package Server.Comands.Core;

import Exceptions.CommandException;
import Server.Context;

import java.sql.SQLException;

/**
 * Интерфейс Command
 * @author nastosinka
 * Часть паттерна Command для создания команд по управлению коллекцией объектов.
 */

public interface Command {

    public String execute(String args, Context context, String userlogin) throws CommandException, SQLException;
    public String getDescription();
    public String getName();
}
