package Client;

import lombok.Setter;
import lombok.SneakyThrows;
import message.Message;

import java.io.*;
import java.net.*;

public class Connection {
    Thread thread;
    final Socket sock;
    ObjectInputStream is;
    ObjectOutputStream os;
    InetAddress host;
    int port;
    @Setter
    private String userName;
    public Connection(Socket sock) {
        this.sock = sock;
        try {
            os = new ObjectOutputStream(sock.getOutputStream());
            is = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e) { e.getMessage(); }
    }

    @SneakyThrows
    public void startThread() {
//        super.run();
        port = 8888;
        host = InetAddress.getLocalHost();
//        os = new ObjectOutputStream(sock.getOutputStream());
//        is = new ObjectInputStream(sock.getInputStream());

        thread = new Thread(() -> {
            while (true) {

                Message message = receiveMessage();

                if (message.isError) {
                    System.err.println(message.getMessage());
                }else {
                    System.out.println(message.getMessage());

                }
            }
        });
        thread.setDaemon(true);

        thread.start();
    }

    @SneakyThrows
    public void sendMessage(Message object) {
      //  object.setMessage(userName);
        os.writeObject(object);
    }

    @SneakyThrows
    public Message receiveMessage() {
        Object obj = is.readObject();

        if (obj instanceof Message message) {
            return message;
        }

        throw new RuntimeException();
    }
}
