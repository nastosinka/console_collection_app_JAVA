package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsAcceptedException;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;
import Server.HumanBeing;

import java.util.stream.Collectors;

public class RemoveLowerCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (args.isBlank()) {
            throw new CommandArgsAcceptedException("remove_lower");
        }
        try {
            context.getDatabaseHumanBeings().removeLower(Long.parseLong(args), userlogin);

            return context.getDatabaseHumanBeings().getCollection()
                    .stream()
                    .map(HumanBeing::toString)
                    .collect(Collectors.joining("\n"));
        } catch (NumberFormatException e) {
            throw new CommandException("Неправильный аргумент. Введите целое число.");        }
    }


    @Override
    public String getDescription() {
        return "удаляет из коллекции все элементы, меньшие чем заданный";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }
}
