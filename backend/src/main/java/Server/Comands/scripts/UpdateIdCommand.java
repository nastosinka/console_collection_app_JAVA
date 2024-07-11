package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandException;
import Server.Context;

import java.sql.SQLException;

public class UpdateIdCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException, SQLException {
        new RemoveByIdCommand().execute(args, context, userlogin);
        new AddCommand().execute(args, context, userlogin);
        return "значение обновлено.";
    }

    @Override
    public String getDescription() {
        return "обновляет значения в заданном элементе по id";
    }

    @Override
    public String getName() {
        return "update_id";
    }
}
