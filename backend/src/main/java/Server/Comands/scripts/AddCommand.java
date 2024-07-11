package Server.Comands.scripts;

import Server.Context;
import Server.HumanBeing;
import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;

public class AddCommand implements Command {

    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        if (!args.isBlank()) {
            throw new CommandArgsNotAcceptedException("add");
        }
        HumanBeing humanBeing = HumanBeing.createFromInput(context, userlogin);
        humanBeing.setUserlogin(userlogin);
        if (context.getDatabaseHumanBeings().addElementToDatabase(humanBeing, userlogin)) {
            return "Объект доабавлен в коллекцию.";
        } else {
            return "Что-то пошло не так...";
        }
     }


    @Override
    public String getDescription() {
        return "добавляет нвовый элемент в коллекцию";
    }

    @Override
    public String getName() {
        return "add";
    }
}
