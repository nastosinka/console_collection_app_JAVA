package Server;

import lombok.Getter;
import Сlasses.Car;
import Сlasses.Coordinates;
import Сlasses.Database;
import Сlasses.WeaponType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.WeakHashMap;


/**
 * Класс DatabaseHumanBeings с конструктором
 * @author nastosinka
 * Нужен для управления коллекцией Server.HumanBeing.
 */
@Getter
public class DatabaseHumanBeings implements Database<HumanBeing> {
    LinkedList<HumanBeing> collection = new LinkedList<>();
    public boolean addElementToDatabase(HumanBeing h, String userlogin) {

        String query = "select nextval('humanbeings_id_seq'::regclass);";

        long id = 0L;
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(query)) {
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                id = set.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String stroka = "INSERT INTO humanbeings values(" + id + ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?);";
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka)) {
            preparedStatement.setString(1, h.getName());
            preparedStatement.setInt(2, h.getCoordinates().getX());
            preparedStatement.setInt(3, h.getCoordinates().getY());
            preparedStatement.setTime(4, Time.valueOf(h.getCreationDate().toLocalTime()));
            preparedStatement.setBoolean(5, h.getRealHero());
            preparedStatement.setBoolean(6, h.getHasToothpick());
            preparedStatement.setDouble(7, h.getImpactSpeed());
            preparedStatement.setString(8, h.getSoundtrackName());
            preparedStatement.setLong(9, h.getMinutesOfWaiting());
            preparedStatement.setString(10, h.getWeaponType().toString());
            preparedStatement.setString(11, h.getCar().getName());
            preparedStatement.setBoolean(12, h.getCar().getCool());
            preparedStatement.setString(13, userlogin);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        h.setUserlogin(userlogin);
        h.setId(id);

        collection.add(h);

        return true;
    }

    @Override
    public boolean removeByID(long id, String userlogin) {
        String stroka = "DELETE FROM humanbeings WHERE userlogin=? AND id=?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka);) {
            preparedStatement.setString(1, userlogin);
            preparedStatement.setLong(2, id);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection.removeIf(human -> human.getId() == id && human.getUserlogin().equals(userlogin));
    }

    public boolean removeElement(HumanBeing humanBeing) {
        return collection.remove(humanBeing);
    }

    public void clearDatabase(String userlogin) throws SQLException {
        String stroka = "DELETE FROM humanbeings WHERE userlogin=?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka);) {
            preparedStatement.setString(1, userlogin);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        collection.removeIf(human -> human.getUserlogin().equals(userlogin));
    }

    public void removeLower(long l, String userlogin) {
        String stroka = "DELETE FROM humanbeings WHERE id < ? and userlogin = ?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka)) {
            preparedStatement.setLong(1, l);
            preparedStatement.setString(2, userlogin);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        collection.removeIf(humanBeing -> humanBeing.getId() < l && humanBeing.getUserlogin().equals(userlogin));
    }

    public void fillCollection() {
        String stroka = "Select * FROM humanbeings ORDER BY id DESC";
        try (PreparedStatement preparedStatement = DBConnection.getConnection1().prepareStatement(stroka)) {
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()) {
                HumanBeing humanBeing = new HumanBeing();

                humanBeing.setId(set.getLong("id"));
                humanBeing.setName(set.getString("name"));
                humanBeing.setCoordinates(new Coordinates(set.getInt("coordinates_x"), set.getInt("coordinates_y")));
                humanBeing.setUserlogin(set.getString("userlogin"));
                humanBeing.setCreationDate(ZonedDateTime.of(set.getDate("creationdate").toLocalDate().atStartOfDay(), ZoneId.systemDefault()));
                humanBeing.setCar(new Car(set.getString("car_name"), set.getBoolean("car_cool")));
                humanBeing.setRealHero(set.getBoolean("realhero"));
                humanBeing.setHasToothpick(set.getBoolean("hastoothpick"));
                humanBeing.setImpactSpeed(set.getDouble(("impactseed")));
                humanBeing.setSoundtrackName(set.getString("soundtrackname"));
                humanBeing.setMinutesOfWaiting(set.getInt("minutesofwaiting"));
                humanBeing.setWeaponType(WeaponType.valueOf(set.getString("weapontype")));
                collection.add(humanBeing);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
