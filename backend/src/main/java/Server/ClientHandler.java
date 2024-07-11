package Server;

import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import message.Message;
import org.apache.commons.lang3.function.Failable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class ClientHandler {

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private final Consumer<Message> onNewMessage;

    private final Connection connection;

    String currentUserName;

    public ClientHandler(Socket sock, Consumer<Message> onNewMessage, Connection connection) {
        this.connection = connection;

        this.onNewMessage = onNewMessage;
        try {
            outputStream = new ObjectOutputStream(sock.getOutputStream());
            inputStream = new ObjectInputStream(sock.getInputStream());

            Thread thread = new Thread(getThreadRunnable());
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Runnable getThreadRunnable() {
        return () -> {
            Boolean passChecker = false;
            try {
                while (!passChecker) {
                    Object obj = inputStream.readObject();
                    System.out.println(obj);

                    if (!(obj instanceof Message message)) {
                        return;
                    }
                    String stroka;
                    String currentPasswd = PasswordHasher.hashPassword(message.getMessage().split("\\|")[1]);
                    System.out.println(currentPasswd.length());

                    currentUserName = message.getMessage().split("\\|")[0];
                    String currentOption = message.getMessage().split("\\|")[2];
                    if (currentOption.equals("login")) {
                        stroka = "SELECT EXISTS(SELECT * FROM progaUsers WHERE userlogin=? and passwd=?)"; // защита от SQL-инъекции
                    } else {
                        stroka = "INSERT INTO progaUsers VALUES(?, ?)";
                    }
                    try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka)) {
                        preparedStatement.setString(1, currentUserName);
                        preparedStatement.setString(2, currentPasswd);
                        preparedStatement.execute();

                        if (currentOption.equals("login")) {
                            ResultSet resultSet = preparedStatement.getResultSet();

                            if (resultSet.next()) {
                                passChecker = resultSet.getBoolean("exists");
                            }
                            if (!passChecker) {
                                outputStream.writeObject(new Message("Пароль или имя пользователя неверны.", true, currentUserName));
                            } else {
                                outputStream.writeObject((new Message("Вход выполнен.", false, currentUserName)));
                            }
                        } else {
                            passChecker = true;
                            outputStream.writeObject(new Message("Пользователь добавлен", false, currentUserName));
                        }

                    } catch (SQLException e) {
                        outputStream.writeObject(new Message("Имя пользователя занято", true, currentUserName));
                    }
                }

                connection.getClients().put(currentUserName, this);


                while (true) {
                    Object obj = inputStream.readObject();

                    System.out.println(obj);

                    if (obj instanceof Message message) {
                        onNewMessage.accept(message);
                    }
                }
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                System.err.println("Client disconnected!");
                connection.getClients().remove(currentUserName);
            }
        };
    }
    
}
