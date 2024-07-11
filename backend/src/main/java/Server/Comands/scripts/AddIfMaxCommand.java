package Server.Comands.scripts;

import Server.Context;
import Server.HumanBeing;
import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;

import java.util.Comparator;
import java.util.Optional;

public class AddIfMaxCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        Optional<HumanBeing> maxMinutesOfWaiting = context.getDatabaseHumanBeings().getCollection()
                .stream()
                .max(Comparator.comparingDouble(HumanBeing::getMinutesOfWaiting));
        if (maxMinutesOfWaiting.isPresent()) {
            context.universalOutput(userlogin, "Максимальное время ожидания: " + maxMinutesOfWaiting.get().getMinutesOfWaiting());
        }
        else {
            context.universalOutput(userlogin, "Коллекция пустая, элемент добавиться в любом случае");
        }


        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("add_if_max");
        }

        HumanBeing newElement = HumanBeing.createFromInput(context, userlogin);

        if (maxMinutesOfWaiting.isEmpty() || maxMinutesOfWaiting.get().getMinutesOfWaiting() < newElement.getMinutesOfWaiting()) {
            if (context.getDatabaseHumanBeings().addElementToDatabase(newElement, userlogin)) {
                return "Объект доабавлен в коллекцию.";
            } else {
                return "Что-то пошло не так...";
            }
        }

        return "Элемент не максимальный";
    }
    @Override
    public String getDescription() {
        return "добавляет элемент в коллекцию, если он больше всех элементов";
    }

    @Override
    public String getName() {
        return "add_if_max";
    }
}
