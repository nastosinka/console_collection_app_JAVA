package Server;


import Server.Comands.Core.CommandInvoker;
import Server.Comands.scripts.*;

/**
 * Класс CommandDatabase
 * @author nastosinka
 * Часть паттерна Command для работы со словарём команд - хранение команд.
 */

public class CommandDatabase {
    public CommandDatabase(CommandInvoker commandInvoker) {
        commandInvoker.commandDictionaryPut("exit", new ExitCommand());
        commandInvoker.commandDictionaryPut("help", new HelpCommand());
        commandInvoker.commandDictionaryPut("show", new ShowCommand());
        commandInvoker.commandDictionaryPut("remove_by_id", new RemoveByIdCommand());
        commandInvoker.commandDictionaryPut("add", new AddCommand());
        commandInvoker.commandDictionaryPut("clear", new ClearCommand());
        commandInvoker.commandDictionaryPut("info", new InfoCommand());
        commandInvoker.commandDictionaryPut("execute_script", new ExecuteScript());
        commandInvoker.commandDictionaryPut("count_by_weapon_type", new CountByWeaponTypeCommand());
        commandInvoker.commandDictionaryPut("head", new HeadCommand());
        commandInvoker.commandDictionaryPut("filter_less_than_minutes_of_waiting", new FilterLessThanMinutesOfWaitingCommand());
        commandInvoker.commandDictionaryPut("count_greater_than_soundtrack_name", new CountGreaterThanSoundtrackNameCommand());
        commandInvoker.commandDictionaryPut("update_id", new UpdateIdCommand());
        commandInvoker.commandDictionaryPut("add_if_max", new AddIfMaxCommand());
        commandInvoker.commandDictionaryPut("remove_lower", new RemoveLowerCommand());

    }
}
