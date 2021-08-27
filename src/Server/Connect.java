package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Connect implements Runnable{
    private final List<String> messagesBuffer;
    private List<ClientListener> clientListeners;
    private final int port;

    public Connect(int port, List<ClientListener> clientListeners, List<String> messagesBuffer) {
        this.messagesBuffer = messagesBuffer;
        this.clientListeners = clientListeners;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            String messageStart = String.format("server started: port %d", port);
            System.out.println(messageStart);
            while (true) {
                Socket socket = serverSocket.accept();      //ждать, пока не появится новое подключение
                System.out.println("new client connected");
                ClientListener clientListener = new ClientListener(socket, messagesBuffer);
                clientListeners.add(clientListener);
                new Thread(clientListener).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
