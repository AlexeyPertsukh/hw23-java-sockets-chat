package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener implements Runnable{
    private final Socket socket;
    private final boolean isPrintInputMessages;

    public ServerListener(Socket socket, boolean isPrintInputMessages) {
        this.socket = socket;
        this.isPrintInputMessages = isPrintInputMessages;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNextLine()) {
                if(isPrintInputMessages) {
                    System.out.println(scanner.nextLine());
                }

            }
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }



}
