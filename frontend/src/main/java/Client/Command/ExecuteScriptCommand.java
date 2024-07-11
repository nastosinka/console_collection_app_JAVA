package Client.Command;

import Client.Connection;
import message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScriptCommand implements ClientCommand {
     private Set<Path> pathStack = new HashSet<>();

     private final Connection connection;
     private final ClientCommandProcessor processor;

    public ExecuteScriptCommand(Connection connection, ClientCommandProcessor processor) {
        this.connection = connection;
        this.processor = processor;
    }

    @Override
    public void execute(String args) {
        if(args.isBlank()) {
            System.err.println("Введите имя файла!");

            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(args))) {

            if (!pathStack.add(Path.of(args))) {
                System.out.println("Recursion!!");
                return;
            }

            reader.lines()
                    .filter(line -> !line.isBlank())
                    .filter(line -> !line.startsWith("#"))
                    .forEach(line ->  {
                        if (!processor.checkCommandClientAndExecute(line))
                            connection.sendMessage(new Message(line, false));
                    });
            pathStack.remove(Path.of(args));

        } catch (IOException e) {
            System.err.println("Файл не найден!");
        }
    }
}
