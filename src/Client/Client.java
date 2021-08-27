package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private final Scanner scanner;
    private final boolean isPrintInputMessages;
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this(host, port, true);
    }

    public Client(String host, int port, boolean isPrintInputMessages) {
        this.scanner = new Scanner(System.in);
        this.isPrintInputMessages = isPrintInputMessages;
        this.port = port;
        this.host= host;
    }

    public String inputLine() {
        return  scanner.nextLine();
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            new Thread(new ServerListener(socket, isPrintInputMessages)).start();
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            String message;
            while (true) {
                message = inputLine();

                printWriter.println(message);
                printWriter.flush();

            }
        } catch (IOException e) {
//            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}
