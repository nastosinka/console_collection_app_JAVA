package Server; /**
 * Класс Server.HumanBeing с конструктором
 * @author nastosinka
 * Нужен для заполнения коллекции  Server.HumanBeing.
 */

import Сlasses.Car;
import Сlasses.Coordinates;
import Сlasses.WeaponType;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeing implements Comparable<HumanBeing> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick; //Поле не может быть null
    private Double impactSpeed; //Поле не может быть null
    private String soundtrackName; //Поле не может быть null
    private long minutesOfWaiting;
    private WeaponType weaponType; //Поле не может быть null
    private Car car; //Поле может быть null
    private String userlogin;

    @Override // сравнение по времени ожидания
    public int compareTo(HumanBeing h) {
        return (int) (this.name.compareTo(h.name));
    }


    public static HumanBeing createFromInput(Context context, String userlogin) {
        return new HumanBeing(0, HumanBeing.enterName(context, userlogin),
                HumanBeing.enterCoordinates(context, userlogin),
                ZonedDateTime.now(),
                HumanBeing.enterRealHero(context, userlogin),
                HumanBeing.enterHasToothpick(context, userlogin),
                HumanBeing.enterImpactSpeed(context, userlogin),
                HumanBeing.enterSoundtrackName(context, userlogin),
                HumanBeing.enterMinutesOfWaiting(context, userlogin),
                HumanBeing.enterWeaponType(context, userlogin),
                new Car(HumanBeing.enterCarName(context, userlogin), enterCarCool(context, userlogin)), "");
    }

    public static String enterName(Context context, String userlogin){
        context.universalOutput(userlogin, "Введите имя: ");

        String current = context.universalInput(userlogin);

        if (current.isEmpty()) {
            context.universalOutput(userlogin, "Неправильный ввод команды. Поле не должно быть пусто.");
            return enterName(context, userlogin);
        } else {
            context.universalOutput(userlogin, "Имя введено");
            return current;
        }
    }
    public static Coordinates enterCoordinates(Context context, String userlogin){
        context.universalOutput(userlogin, "Введите координаты:");
        try {
            context.universalOutput(userlogin, "x: ");
            int x = Integer.parseInt(context.universalInput(userlogin));
            context.universalOutput(userlogin, "y: ");
            int y = Integer.parseInt(context.universalInput(userlogin));
            return new Coordinates(x, y);

        } catch (NumberFormatException e) {
            context.universalOutput(userlogin, "Введите координаты в формате int.");

            return enterCoordinates(context, userlogin);
        }
    }
    public static Double enterImpactSpeed(Context context, String userlogin) {
        context.universalOutput(userlogin, "Введите уровень влияния: ");
        try {
            return Double.parseDouble(context.universalInput(userlogin));

        } catch (NumberFormatException e) {
            context.universalOutput(userlogin, "Введите число в формате double.");

            return enterImpactSpeed(context, userlogin);
        }
    }
    public static Boolean enterRealHero(Context context, String userlogin) {
        String userAns;
        context.universalOutput(userlogin, "Введите геройский статус: ");
        userAns = context.universalInput(userlogin);
        if (userAns.equals("true") || userAns.equals("false")) {
            return Boolean.parseBoolean(userAns);
        } else {
            context.universalOutput(userlogin, "Введите значение в формате true/false.");
            return enterRealHero(context, userlogin);
        }
    }
    public static Boolean enterHasToothpick(Context context, String userlogin) {
        String userAns;
        context.universalOutput(userlogin, "Введите наличие зубочистки: ");
        userAns = context.universalInput(userlogin);
        if (userAns.equals("true") || userAns.equals("false")) {
            return Boolean.parseBoolean(userAns);
        } else {
            context.universalOutput(userlogin, "Введите значение в формате true/false.");
            return enterHasToothpick(context, userlogin);
        }
    }
    public static String enterSoundtrackName(Context context, String userlogin) {
        context.universalOutput(userlogin, "Введите название саундтрэка: ");
        String current = context.universalInput(userlogin);

        if (current.isEmpty()) {
            context.universalOutput(userlogin, "Неправильный ввод команды. Поле не должно быть пусто.");
            return enterSoundtrackName(context, userlogin);
        } else {
            return current;
        }
    }
    public static Long enterMinutesOfWaiting(Context context, String userlogin) {
        context.universalOutput(userlogin, "Введите время ожидания: ");
        try {
            return Long.parseLong(context.universalInput(userlogin));

        } catch (NumberFormatException e) {
            context.universalOutput(userlogin, "Введите число в формате long.");

            return enterMinutesOfWaiting(context, userlogin);
        }
    }
    public static WeaponType enterWeaponType(Context context, String userlogin) {
        context.universalOutput(userlogin, "Введите оружие персонажа: ");
        try {
            return WeaponType.valueOf((context.universalInput(userlogin)));

        } catch (IllegalArgumentException e) {
            context.universalOutput(userlogin, "Введите один из параметров: AXE, HAMMER, KNIFE, PISTOL.");

            return enterWeaponType(context, userlogin);
        }
    }
    public static String enterCarName(Context context, String userlogin) {
        context.universalOutput(userlogin, "Введите марку автомобиля: ");
        String name = context.universalInput(userlogin);

        if (name.isEmpty()) {
            context.universalOutput(userlogin, "Неправильный ввод команды. Поле не должно быть пусто.");
            return enterCarName(context, userlogin);
        } else {
            return name;
        }
    }
    public static Boolean enterCarCool(Context context, String userlogin) {
        String userAns;
        context.universalOutput(userlogin, "Введите крутизну автомобиля: ");
        userAns = context.universalInput(userlogin);
        if (userAns.equals("true") || userAns.equals("false")) {
            return Boolean.parseBoolean(userAns);
        } else {
            context.universalOutput(userlogin, "Введите крутизну в формате true/false.");

            return enterCarCool(context, userlogin);
        }

    }
}