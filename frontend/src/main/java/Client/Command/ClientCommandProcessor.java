package Client.Command;

import Client.Command.ClientCommand;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public class ClientCommandProcessor {

    private Map<String, ClientCommand> map = new HashMap<>();

    public boolean checkCommandClientAndExecute(String command) {
        String[] split = command.split(" ", 2);

        if (split.length == 0) {
            return false;
        }

        if (!map.containsKey(split[0])) {
            return false;
        }


        String args = "";

        if (split.length == 2) {
            args = split[1];
        }

        executeCommand(split[0], args);
        return true;
    }

    private void executeCommand(String commandName, String args) {
        map.get(commandName).execute(args);
    }
}
