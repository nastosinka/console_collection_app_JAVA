package Server;

import Exceptions.CommandException;
import message.Message;

import java.sql.SQLException;


// 1. Регистрация и авторизация с моим подключением? Или пользователи каждый подключается в свою датабазу?
// 2. Как можно реализовать разные уровни доступа? Это в SQL менять?
// jwt ключи json web token
// user id привязывать к новой создаваемой таблице - автор таблицы
// добавить условие на select к user id (where ...)
// можно через jwt token реализовать эту штукук
//  @Deprecated("больше не нужна для этой лабы") - аннотация для обозначение неиспользуюемых классов

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
