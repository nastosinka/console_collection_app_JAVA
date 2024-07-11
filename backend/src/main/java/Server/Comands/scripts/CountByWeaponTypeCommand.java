package Server.Comands.scripts;

import Server.Context;
import Server.HumanBeing;
import Сlasses.WeaponType;
import Server.Comands.Core.Command;
import Exceptions.CommandException;

public class CountByWeaponTypeCommand implements Command {
    @Override
    public String execute(String args, Context context, String userlogin) throws CommandException {
        try {
            WeaponType type = WeaponType.valueOf(args);

            long answer = context.getDatabaseHumanBeings().getCollection()
                    .stream()
                    .filter(human -> filterByElement(human, type))
                    .count();
            return "Найдено " + answer + " элементов.";
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }


    private boolean filterByElement(HumanBeing humanBeing, WeaponType weaponType) {
        return humanBeing.getWeaponType() == weaponType;
    }

    @Override
    public String getDescription() {
        return "считает количество записей с указанным типом оружия";
    }

    @Override
    public String getName() {
        return "count_by_weapon_type";
    }
}
