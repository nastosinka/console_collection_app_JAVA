package Server;

import Exceptions.CommandException;
import message.Message;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args)  {

        Context context = new Context();
        Connection connection = new Connection();

        context.setInput(connection::waitForInput);
        context.setOutput((string, userlogin) -> connection.sendMessage(new Message(string, false), userlogin));

        DBConnection dbConnection = new DBConnection();
        dbConnection.getDBConnection();

        connection.setOnNewMessage((message -> {
            try {
                String line = context.getCommandInvoker().executeCommand(message.getMessage(), context, message.getUser());

                connection.sendMessage(new Message(line, false), message.getUser());

            } catch (CommandException | SQLException  e) {
                connection.sendMessage(new Message(e.getMessage(), true), message.getUser());
            }

        }));

        connection.initialize();
    }
}
