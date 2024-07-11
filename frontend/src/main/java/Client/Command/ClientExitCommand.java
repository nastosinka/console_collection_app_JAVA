package Client.Command;

import Client.Command.ClientCommand;

public class ClientExitCommand implements ClientCommand {


    @Override
    public void execute(String args) {
        if (!args.isBlank()) {
            System.err.println("No arguments!");
            return;
        }

        System.exit(0);
    }
}
