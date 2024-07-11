package Server;


import Server.Comands.Core.CommandInvoker;
//import Server.Console.CsvFileParser;
//import Server.Console.FileParser;
import lombok.Data;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Data
public class Context {

    private CommandInvoker commandInvoker = new CommandInvoker();
    private CommandDatabase commandDatabase = new CommandDatabase(commandInvoker);
    private DatabaseHumanBeings databaseHumanBeings = new DatabaseHumanBeings();

//    private FileParser<HumanBeing> fileParser;
    private Scanner scanner = new Scanner(System.in);

    private boolean isInFile;

    BiConsumer<String, String> output;
    Function<String, String> input;

    public void universalOutput(String userlogin, String string) {
        output.accept(string, userlogin);
    }
    public String universalInput(String userlogin) {
        return input.apply(userlogin);
    }

//    public Context(String filename) {
//        fileParser = new CsvFileParser<>(filename, HumanBeing.class);
//    }
}
