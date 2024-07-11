package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;

import java.sql.SQLException;

public class ClearCommand implements Command {
//    classes.Database<?> database;

    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException, SQLException {
        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("clear");
        }

        context.getDatabaseHumanBeings().clearDatabase(userlogin);
        return "Массив очищен.";
    }

    @Override
    public String getDescription() {
        return "команда очищения массива с данными.";
    }

    @Override
    public String getName() {
        return "clear";
    }
}
