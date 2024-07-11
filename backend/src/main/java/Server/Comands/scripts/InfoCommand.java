package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class InfoCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("info");
        }
        StringBuilder builder = new StringBuilder();

        builder.append("Тип коллекции: ").append(context.getDatabaseHumanBeings()
                .getCollection()
                .getClass().getSimpleName());

        builder.append("\nДата инициализации: ");

        builder.append("\nКоличество элементов: ").append(context.getDatabaseHumanBeings().getCollection().size());




        return builder.toString();



    }

    @Override
    public String getDescription() {
        return "выводит информацию о коллекции";
    }

    @Override
    public String getName() {
        return "info";
    }

}
