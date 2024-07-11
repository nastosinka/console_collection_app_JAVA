package Сlasses;

import Server.HumanBeing;

import java.util.Collection;

/**
 * Интерфейс classes.Database
 * @author nastosinka
 * Нужен для управления коллекциями - родитель.
 */

public interface Database<T> {

    Collection<T> getCollection();
    boolean addElementToDatabase(HumanBeing h, String userlogin);

    boolean removeByID(long id, String userlogin);

}
