package Client;

import Client.Command.ClientCommandProcessor;
import Client.Command.ClientExitCommand;
import Client.Command.ExecuteScriptCommand;
import message.Message;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static Connection connection;
    static String user = "123";
    static String passwd = "123";

    public static void main(String[] args)  {


        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8888)) {

            connection = new Connection(socket);



            ClientCommandProcessor processor = new ClientCommandProcessor();
            processor.getMap().put("exit", new ClientExitCommand());
            processor.getMap().put("execute_script", new ExecuteScriptCommand(connection, processor));


            Scanner scanner = new Scanner(System.in);
            String userName = getUser(scanner);
            connection.setUserName(userName);

            connection.startThread();

            while (true) {
                String line = scanner.nextLine().strip();

                if (processor.checkCommandClientAndExecute(line)) {
                    continue;
                }

                connection.sendMessage(new Message(line, false, userName));
            }
        } catch(ConnectException e) {
            System.err.println("Server is down!");
            System.exit(-1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUser(Scanner scanner) {
        System.out.println("Выберите опцию: login или register");
        String option = scanner.nextLine();
        if (!option.equals("login") && !option.equals("register")) {
            return getUser(scanner);
        }

        System.out.println("Введите имя пользователя:");
        user = scanner.nextLine();
        System.out.println("Введите пароль:");
        passwd = scanner.nextLine();
        String messageObject = user + "|" + passwd + "|" + option;

        connection.sendMessage(new Message(messageObject, false));

        Message currentMessage = connection.receiveMessage();

        System.out.println(currentMessage.getMessage());
        if (currentMessage.isError()) {
            return getUser(scanner);
        }

        return user;
    }

}

