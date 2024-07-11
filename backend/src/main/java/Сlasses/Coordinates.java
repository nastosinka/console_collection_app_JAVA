package Сlasses;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс classes.Coordinates с конструктором
 * @author nastosinka
 * Нужен для заполнения коллекции классов Server.HumanBeing
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordinates {
    @CsvBindByName(column = "coordinates.x", required = true)
    private int x; //Максимальное значение поля: 449
    @CsvBindByName(column = "coordinates.y", required = true)
    private int y; //Максимальное значение поля: 93

}