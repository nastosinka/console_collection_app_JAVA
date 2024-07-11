package Сlasses;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс classes.Car с конструктором
 * @author nastosinka
 * Нужен для заполнения коллекции классов Server.HumanBeing
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {
    @CsvBindByName (column = "car.name", required = true)
    private String name; //Поле не может быть null
    @CsvBindByName (column = "car.cool", required = true)
    private Boolean cool = Boolean.FALSE; //Поле не может быть null

}