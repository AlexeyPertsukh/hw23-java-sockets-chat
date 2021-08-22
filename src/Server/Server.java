package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private final List<String> messagesBuffer;
    private final List<ClientListener> clientListeners;
    private final List<String> log;
    private final Pattern patternName;

    public Server() {
        messagesBuffer = Collections.synchronizedList(new ArrayList<>());       //синхроизируем List's
        clientListeners  = Collections.synchronizedList(new ArrayList<>());
        log  = Collections.synchronizedList(new ArrayList<>());

        //регулярные выражения: https://proglib.io/p/25-java-regex/
        //никнейм юзера- только буквы(англ и рус) и цифры без других знаков, первая - буква, слово длиной от мин до макс
        String strPattern = String.format("[A-Za-z\\u0400-\\u04FF]([A-Za-z0-9\\u0400-\\u04FF]{%d,%d})", Const.MIN_LENGTH_NAME - 1, Const.MAX_LENGTH_NAME - 1);
        patternName = Pattern.compile(strPattern);
    }

    public void start() {

        printOnStart();
        Connect connect = new Connect(Const.PORT, clientListeners, messagesBuffer);
        Thread threadConnect = new Thread(connect);
        threadConnect.start();

        while(true) {
//            sleep(1);         //без sleep(1) не работает, если не синхронизировать List
//            sleep(100);
            sendFirstMessage(); //отправка первого сообщения подключившемся клиентам
            clientsRegistration();
            deleteDisconnClients();

            //в буфере есть сообщения от клиентов- распечатываем и пересылаем всем
            if(!messagesBuffer.isEmpty()) {
                printMessages();
                sendMessagesToRegisteredClients();
                updateLog();
                messagesBuffer.clear();
            }
        }

    }

    private void sendMessagesToRegisteredClients() {
        ClientListener c;
        //не менять на foreach или stream, пока полностью не разобрался с синхронизацие arraylist
        for (int i = 0; i < clientListeners.size(); i++) {
            c = clientListeners.get(i);
            if(c.isRegistered() && c.isConnected()) {
                c.sendMessagesBuffer();
            }
        }
//        clientListeners.stream().filter((c)-> c.isRegistered() && c.isConnected())
//                .forEach((c)->c.sendMessagesBuffer());
    }

    private void printMessages() {
        messagesBuffer.forEach(System.out::println);
    }

    private void sendFirstMessage() {
        ClientListener c;
        //не менять на foreach или stream, пока полностью не разобрался с синхронизацие arraylist
        for (int i = 0; i < clientListeners.size(); i++) {
            c = clientListeners.get(i);
            if(!c.isSendFirstMessage() && c.isConnected()) {
                c.sendMessage(Const.FIRST_MSG_TO_CLIENT + "\n" + Const.MSG_NAME_RULES);
            }
        }
//        clientListeners.stream().filter((c)->!c.isSendFirstMessage() && c.isConnected())
//                                .forEach((c)->c.sendMessage(Const.FIRST_MSG_TO_CLIENT + "\n" + Const.MSG_NAME_RULES));
    }

    //регистрация новых клиентов в чате- регистрация это привязка имени к клиенту
    //без регистрации клиент не получит переписку чата
    //первое сообщение от клиента- это его имя
    //имя проверяется на корректность и клиент либо регистрируется, либо нет
    private void clientsRegistration() {
        Const.EnumValidCode enumValidCode;
        ClientListener c;
        //не менять на foreach или stream, пока полностью не разобрался с синхронизацие arraylist
        for (int i = 0; i < clientListeners.size(); i++) {
            c = clientListeners.get(i);
            if(!c.isRegistered() && c.isConnected() && c.isMessage()) {
                String newName = c.getLastMessage();
                enumValidCode = validName(newName);
                if(enumValidCode ==  Const.EnumValidCode.OK) {
                    c.registration(newName, log);
                }
                else {
                    c.invalidRegistration(enumValidCode);
                }
            }
        }

        //что-то глючит иногда
//        clientListeners.stream().filter((c) -> !c.isRegistered() && c.isConnected() && !c.getLastMessage().isEmpty())
//                .forEach((c)-> {
//                                    String newName = c.getLastMessage();
//                                    Const.EnumValidCode enumValidCode = validName(newName);
//                                    if(enumValidCode ==  Const.EnumValidCode.OK) {
//                                        c.registration(newName, log);
//                                    }
//                                    else {
//                                        c.invalidRegistration(enumValidCode);
//                                    }
//                                });
    }

    private Const.EnumValidCode validName(String newName) {
        Matcher matcher = patternName.matcher(newName);

        if(!matcher.matches()) {
            return Const.EnumValidCode.NAME_INCORRECT;
        }

        ClientListener c;
        //не менять на foreach или stream, пока полностью не разобрался с синхронизацие arraylist
        for (int i = 0; i < clientListeners.size(); i++) {
            c = clientListeners.get(i);
            if(c.isRegistered() && c.isConnected() && c.getName().equalsIgnoreCase(newName)) {
                return Const.EnumValidCode.NAME_DUPLICATED;
            }
        }

//        if(clientListeners.stream().anyMatch((c) -> c.isRegistered() && c.isConnected() && c.getName().equalsIgnoreCase(newName)))
//        {
//            return Const.EnumValidCode.NAME_DUPLICATED;
//        }
        return Const.EnumValidCode.OK;
    }


    //как упростить удаление через стрим?
    private void deleteDisconnClients() {
        ClientListener c;
        //не менять на foreach или stream, пока полностью не разобрался с синхронизацие arraylist
        for (int i = clientListeners.size() - 1; i >= 0 ; i--) {
            c = clientListeners.get(i);
            if(!c.isConnected()) {
                clientListeners.remove(i);
            }
        }
    }

    private void printOnStart() {
        System.out.println(Const.TEXT_ON_START);
    }

    private void updateLog() {
        log.addAll(messagesBuffer);
    }

    public static void sleep(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }




}
