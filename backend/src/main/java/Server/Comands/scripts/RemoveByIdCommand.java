package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsAcceptedException;
import Exceptions.CommandException;
import Exceptions.IncorrectCommand;
import Server.Context;

public class RemoveByIdCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        try {
            long id = Long.parseLong(args);

            if (context.getDatabaseHumanBeings().removeByID(id, userlogin)) {
                return "Элемент удалён.";
            } else {
                return "Ошибка. Элемент не найден.";
            }
        } catch (NumberFormatException e) {
            throw new CommandArgsAcceptedException("remove_by_id");
        }
    }

    @Override
    public String getDescription() {
        return "удаляет значение из коллекции по if.";
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
}
