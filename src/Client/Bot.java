package Client;

public class Bot extends Client{
    private final BotInput botInput;
    private final String name;
    private boolean isFirstSend;
    private final int pause;

    public Bot(String host, int port, String name, int pause, BotInput botInput) {
        super(host, port, false);
        this.botInput = botInput;
        this.pause = pause;
        this.name = name;
    }

    @Override
    public String inputLine() {
        My.sleep(pause);
        String message = "";
        if(!isFirstSend) {
            isFirstSend = true;
            message = name;
        }
        else {
            message = botInput.inputLine();
        }

        System.out.printf(">[%s] %s \n", name, message);

        return message;
    }

}
