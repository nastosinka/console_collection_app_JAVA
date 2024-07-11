package Exceptions;

public class CommandArgsNotAcceptedException extends CommandException {
    public CommandArgsNotAcceptedException(String command) {
        super("Команда " + command + " не использует аргументы.");
    }
}
