package Server;

import lombok.Getter;
import lombok.Setter;
import message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Connection {

    ServerSocket serv;

    int port = 8888;
    @Setter
    private Consumer<Message> onNewMessage;

    @Getter
    Map<String, ClientHandler> clients = new HashMap<>();

    Runnable threadMethod;

    public void initialize() {
        try {
            serv = new ServerSocket(port);
            System.out.println("server connected");

            while (true) {
                waitForClient();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForClient() {
        try {
            Socket sock = serv.accept();

            new ClientHandler(sock, onNewMessage, this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Message message, String userName) {
        try {
            getClients().get(userName).outputStream.writeObject(message);
        } catch (IOException e) {
            System.err.println("Client disconnected!");
        }
    }

    public String waitForInput(String userName) {
        try {
            Object obj = getClients().get(userName).inputStream.readObject();


            System.out.println(obj);

            if (obj instanceof Message message) {
                return message.getMessage();
            }
        } catch (IOException | ClassNotFoundException e) {
            return e.getMessage();
        }

        return "";
    }


}
