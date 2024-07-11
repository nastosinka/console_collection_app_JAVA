package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsAcceptedException;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;

import java.util.stream.Collectors;

public class FilterLessThanMinutesOfWaitingCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (args.isBlank()) {
            throw new CommandArgsAcceptedException("filter_less_than_minutes_of_waiting");
        }
        try {
            return context.getDatabaseHumanBeings()
                    .getCollection()
                    .stream()
                    .filter(human -> human.getMinutesOfWaiting() < Long.parseLong(args))
                    .map(humanBeing -> humanBeing + "\n")
                    .collect(Collectors.joining());
        } catch (NumberFormatException e) {
            throw new CommandException("Неправильный аргумент. Введите целое число.");        }
    }
    @Override
    public String getDescription() {
        return "выводит записи с меньшим количеством времени ожидания, чем заданное";
    }

    @Override
    public String getName() {
        return "filter_less_than_minutes_of_waiting";
    }
}
