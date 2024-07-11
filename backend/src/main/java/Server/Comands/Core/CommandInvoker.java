package Server.Comands.Core;

import Exceptions.CommandException;
import Exceptions.IncorrectCommand;
import Server.Context;
import lombok.Getter;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Класс CommandInvolker
 * @author nastosinka
 * Часть паттерна Command для работы со словарём команд - запуском.
 */

@Getter
public class CommandInvoker {
    private final HashMap<String, Command> commandDictionary = new HashMap<>();

    public String executeCommand(String commandLine, Context context, String userlogin) throws CommandException, SQLException {
        commandLine = commandLine.strip();
        String command = commandLine.split(" ", 2)[0];
        String args = ""; // области видимости

        if (!commandLine.equals(command)) {
            args = commandLine.split(" ", 2)[1];
        }
        if (commandDictionary.containsKey(command)) {
            return commandDictionary.get(command).execute(args, context, userlogin);
        } else {
            throw new IncorrectCommand("Неверная команда");
        }
    }
    public void commandDictionaryPut(String s, Command c) {
        commandDictionary.put(s, c);
    }
}
