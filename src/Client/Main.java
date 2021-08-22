package Client;
/*
Модернизация чата.
К сообщению добавлять время.
Должно быть ясно, кто отправляет сообщение.
Имя не должно состоять из одних пробелов.
Имя не менне двух символов.
Отображать всю переписку вновь подклеченому
клиенту.

*Запретить использование имени, которое
уже есть в чате.
**Другая архитекура отправки юзерам сообщений.
**"Защита от дурака"
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static int PORT = 6789;
    private final static String HOST = "127.0.0.1";

    private final List<String> listBotMessages = new ArrayList<>();

    public static void main(String[] args) {
        Main prog = new Main();
        prog.go();
    }

    private void go() {
        Scanner sc = new Scanner(System.in);
        int num = My.nextInt(sc,"Режим работы (1- человек, 2 - боты): ", 1, 2);

        if (num == 1) {
            Client client = new Client(HOST, PORT);
            new Thread(client).start();
        }
        else {
            loadBotMessages();
            //
            Bot bot1 = new Bot(HOST, PORT,"Арнольд666", 3000, this::getMessage);
            new Thread(bot1).start();

            Bot bot2 = new Bot(HOST, PORT,"CheБуратор", 2500, this::getMessage);
            new Thread(bot2).start();

            Bot bot3 = new Bot(HOST, PORT,"Kiska2002", 4000, this::getMessage);
            new Thread(bot3).start();

            //спамер - для проверки защиты от дурака
            Bot botSpammer = new Bot(HOST, PORT,"SPAMMER", 400, ()->"SPAM");
            new Thread(botSpammer).start();
        }
    }

    private String getMessage() {
        return listBotMessages.get(My.random(listBotMessages.size()));
    }


    private void loadBotMessages(){
        listBotMessages.add("на ютубе ролик прикольный про кошечку, она там лапку грызет");
        listBotMessages.add("Всем привет!");
        listBotMessages.add("Кто сегодня на пиво?");
        listBotMessages.add("Го бухать!");
        listBotMessages.add("лично я предпочитаю грибы");
        listBotMessages.add("работа закладчиком, пишите в личку");
        listBotMessages.add("всем цёмки в этом чате!");
        listBotMessages.add("Пацаны, кто выучил лямбды? Очень нада...");
        listBotMessages.add("Майню крипту");
        listBotMessages.add("Чатик что-то тупит админ просыпайся");
        listBotMessages.add("бабы есть?");
        listBotMessages.add("Снова ты?!!!!");
        listBotMessages.add("секс наркотики рокнрол еееееее");
        listBotMessages.add("не 'тся', а 'ться', олень");
        listBotMessages.add("я сегодня иду купатся на речку");
        listBotMessages.add("у кого какие планы????");
        listBotMessages.add("пайтон для лохов");
        listBotMessages.add("согласен, python отстой");
        listBotMessages.add("шота скушно, где все");
        listBotMessages.add("вы что, боты?");
        listBotMessages.add("сам ты бот!");
        listBotMessages.add("на аве ты на Гитлера похож");
        listBotMessages.add("нееее, я за закладки уже отмотал");
        listBotMessages.add("одна школота кругом");
        listBotMessages.add("хватит парится за учебу");
        listBotMessages.add("ну где же вы ******??? а выручайте дяяяядю!!!");
        listBotMessages.add("смотри телек больше");
        listBotMessages.add("а я слил всю крипту");
        listBotMessages.add("говорят, скоро крипта всё");
        listBotMessages.add("мамку свою пугай");
        listBotMessages.add("Чё сразу Гитлер то????");
        listBotMessages.add("Приснилось, что снова сдавать экзамены в универе");
        listBotMessages.add("Конь в пальто!");
        listBotMessages.add("а я видел, как обезьянка на гармошке играла 0_o");
        listBotMessages.add("еду в магадан!");
        listBotMessages.add("Знаю одного такого Бурундукова");
        listBotMessages.add("я тебя по айпи вычеслю и накажу школотрон");
        listBotMessages.add("согласен, регулярные выражения тоже надо почитать ");
        listBotMessages.add("Забыл пароль от аськи, что делать?");
        listBotMessages.add("Сегодня прям аншлаг!");
        listBotMessages.add("Нашел кошелек, а там права. Купил булочку. Спасибо, Бурундуков Сергей Петрович!");
        listBotMessages.add("мала народу у всех шабат чтоле");
        listBotMessages.add("обезьянка что, у меня вот кабанчик в преферанс играл");
    }


}
