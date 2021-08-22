package Server;

public class Const {

    public static final String PROG_NAME = "TCP jCHAT";
    public static final String VERSION = "2.20";
    public static final String COPYRIGHT = "JAVA A01 \"ШАГ\", Запорожье 2021";
    public static final String AUTHOR = "Перцух Алексей";

    public final static int PORT = 6789;
    public final static int MIN_LENGTH_NAME = 2;
    public final static int MAX_LENGTH_NAME = 12;
    public final static int MAX_MSG_PER_SECOND = 2;     //максимальное количество сообщений в секунду для защиты от дурака (спамера)

    public final static String MSG_NAME_LENGTH = String.format("* длина имени %d-%d символов",MIN_LENGTH_NAME, MAX_LENGTH_NAME);
    public final static String MSG_NAME_RULES = "* Имя может состоять из русских или английских букв \n" +
                                                "* разрешены цифры, но первый символ должен быть буквой \n" +
                                                "* без пробелов\n" +
                                                MSG_NAME_LENGTH + "\n" +
                                                "Введите свое имя: ";

    public final static String MSG_NAME_DUPLICATED = "Это имя уже используется";
    public final static String MSG_NAME_INCORRECT = "Недопустимое имя";
    public final static String FIRST_MSG_TO_CLIENT = "***********************************\n"
                                         + String.format("Welcome to %s %s \n", PROG_NAME, VERSION)
                                                     +   "***********************************";

    public final static String TEXT_ON_START = "******************************\n" +
                                String.format("%s %s  \n", PROG_NAME, VERSION) +
                                             COPYRIGHT + "\n" +
                                             "******************************";

    public static final String FORMAT_MSG =    "%s %s: %s";
    public static final String MSG_ADD_NAME =      "вошел в чатик";
    public static final String MSG_DISCONNECT =    "покинул чатик";
    public static final String TIME_FORMAT =        "HH:mm";

    private Const() {}

    public enum EnumValidCode {
        OK("OK"),
        NAME_DUPLICATED(MSG_NAME_DUPLICATED),
        NAME_INCORRECT(MSG_NAME_INCORRECT + "\n" + MSG_NAME_RULES);
        private final String message;

        EnumValidCode(String message) {
            this.message = message;
        }

        String getMessage() {
            return message;
        }

    }
}
