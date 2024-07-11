package Server.Comands.scripts;

import Server.Context;
import Server.HumanBeing;
import Server.Comands.Core.Command;
import Exceptions.CommandArgsAcceptedException;
import Exceptions.CommandException;

public class CountGreaterThanSoundtrackNameCommand implements Command {

    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (args.isBlank()) {
            throw new CommandArgsAcceptedException("count_greater_than_soundtrack_name");
        }
        try {
            long answer = context.getDatabaseHumanBeings().getCollection()
                    .stream()
                    .filter(human -> filterByElement(human, args))
                    .count();
            return "Найдено " + answer + " элементов.";
        } catch (IllegalArgumentException e) {
            throw new CommandException("Неправильный ввод аргументов команды.");
        }
    }

    private boolean filterByElement(HumanBeing humanBeing, String soundTrack) {
        return humanBeing.getSoundtrackName().compareTo(soundTrack) < 0;
    }
    @Override
    public String getDescription() {
        return "считает количество элементов, значение которых больше заданного ";
    }

    @Override
    public String getName() {
        return "count_greater_than_soundtrack_name";
    }
}
