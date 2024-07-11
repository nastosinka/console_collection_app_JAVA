package Server.Comands.scripts;

import Server.Comands.Core.Command;
import Exceptions.CommandArgsNotAcceptedException;
import Exceptions.CommandException;
import Server.Context;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

public class ExecuteScript implements Command {
    HashSet<Path> hashSet = new HashSet<>();

    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        try (FileReader fileReader = new FileReader(args)) {
            Path path = Path.of(args); // получаем адрес файла
            if (!hashSet.add(path)) {
                throw new CommandException("Рекурсивные команды не поддерживаются.");
            }

            try {
                Scanner scanner = new Scanner(fileReader);
                while (scanner.hasNext()) {
                    String a = scanner.nextLine();
                    if (a.startsWith("#") || a.isBlank()) { //продолжтть выполнение если есть строчки
                        continue;
                    }
                    String line = context.getCommandInvoker().executeCommand(a, context, userlogin);

                    context.universalOutput(userlogin, line);
                }
            } catch (CommandException e) {
                hashSet.remove(path);
                throw e;
            }

        } catch (IOException | SQLException e) {
            throw new CommandArgsNotAcceptedException("ExecuteScript");
        }
        return "Скрипт исполнен.";
    }

    @Override
    public String getDescription() {
        return "исполняет скрипт из файла";
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
