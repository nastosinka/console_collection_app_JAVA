package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;

import java.util.*;
import java.util.stream.Collectors;

public class HelpCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (!args.isEmpty()) {
            throw new CommandArgsNotAcceptedException("help");
        }
        Map<String, Command> dictionary = context.getCommandInvoker().getCommandDictionary();

        ArrayList<String> helpDict = new ArrayList<>();
        for (String s : dictionary.keySet()) {
            helpDict.add(s + " " + dictionary.get(s).getDescription());
        }
        return helpDict.stream().map(a -> a + ("\n")).collect(Collectors.joining()).strip(); //лямбда-выражения
    }

    @Override
    public String getDescription() {
        return "выводит список команд  и их описание";
    }

    @Override
    public String getName() {
        return "help";
    }
}
