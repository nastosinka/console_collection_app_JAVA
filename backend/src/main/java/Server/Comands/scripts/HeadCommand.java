package Server.Comands.scripts;

import Server.Context;
import Server.HumanBeing;
import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;

import java.util.Comparator;
import java.util.Optional;

public class HeadCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("head");
        }
        Optional<HumanBeing> optional = context.getDatabaseHumanBeings().getCollection()
                .stream()
                .min(Comparator.comparingLong(HumanBeing::getId));

        if (optional.isEmpty()) {
            throw new CommandException("Net");
        } else {
            return optional.get().toString(); //из оптионал в хуманбеинг в стринг
        }
    }

    @Override
    public String getDescription() {
        return "выводит первую запись из коллекции";
    }

    @Override
    public String getName() {
        return "head";
    }
}
