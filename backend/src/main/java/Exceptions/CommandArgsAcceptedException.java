package Exceptions;

public class CommandArgsAcceptedException extends CommandException {
    public CommandArgsAcceptedException(String command) {
        super("Команда " + command + " использует аргументы.");
    }
}
