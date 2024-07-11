package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Server.Context;

public class ExitCommand implements Command {

    @Override
    public String execute(String args, Context context, String userlogin) throws CommandArgsNotAcceptedException {
        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("exit");
        }
        System.exit(0);
        return null;
    }

    @Override
    public String getDescription() {
        return "выходит из консоли";
    }

    @Override
    public String getName() {
        return "exit";
    }
}
