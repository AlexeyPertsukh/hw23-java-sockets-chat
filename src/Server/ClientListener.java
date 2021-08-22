
package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class ClientListener implements Runnable{
    private final long MIN_TIME_BETWEEN_MSG;
    private final Socket clientSocket;
    private PrintWriter printWriter;
    private String name;
    private final List<String> messagesBuffer;
    private boolean isSendFirstMessage;
    private boolean isConnected;
    private String lastMessage;
    private long timeLastMessage;


    public ClientListener(Socket clientSocket, List<String> messagesBuffer) {
        this.clientSocket = clientSocket;
        this.messagesBuffer = messagesBuffer;
        this.lastMessage = "";
        this.isConnected = true;
        MIN_TIME_BETWEEN_MSG = 1000 / Const.MAX_MSG_PER_SECOND;
        clearName();
        try {
            printWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(clientSocket.getInputStream());
            String message;
            while (scanner.hasNextLine()) {
                message = scanner.nextLine();
                if(isRegistered() && !isSpam()) {
                    addMessage(message);
                }
                lastMessage = message;
                timeLastMessage = System.currentTimeMillis();
            }

            System.out.println("client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isRegistered()) {
            addMessage(Const.MSG_DISCONNECT);
        }
        isConnected = false;
    }

    public boolean isSpam() {
        return (System.currentTimeMillis() - timeLastMessage) < MIN_TIME_BETWEEN_MSG  ;
    }


    public void addMessage(String message) {
        messagesBuffer.add(getFormattedMessage(message));
    }

    private void sendList(List<String> list) {
        for (String line : list) {
            printWriter.println(line);
        }
        printWriter.flush();

    }

    public void sendMessagesBuffer() {
        sendList(messagesBuffer);
    }


    public void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
        isSendFirstMessage = true;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public static String getFormattedMessage(String message, String name) {
        String timeStamp = new SimpleDateFormat(Const.TIME_FORMAT).format(Calendar.getInstance().getTime());
        String formattedName = "";
        if(!name.isEmpty()) {
            formattedName = String.format("[%s]", name);
        }
        return String.format(Const.FORMAT_MSG, timeStamp, formattedName, message);
    }

    public String getFormattedMessage(String message) {
        return getFormattedMessage(message, name);
    }

    //
    public void invalidRegistration(Const.EnumValidCode enumValidCode) {
        clearLastMessage();
        sendMessage(enumValidCode.getMessage());
    }

    public boolean isSendFirstMessage() {
        return isSendFirstMessage;
    }

    private void clearName() {
        name = "";
    }
    private void clearLastMessage() {
        lastMessage = "";
    }

    //состояние регистрации на сервере
    public boolean isRegistered() {
        return !name.isEmpty();
    }

    public void registration(String name, List<String> log) {
        this.name = name;
        messagesBuffer.add(getFormattedMessage(Const.MSG_ADD_NAME));
        sendList(log);
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getName() {
        return name;
    }

    public boolean isMessage() {
        return !lastMessage.isEmpty();
    }

}
