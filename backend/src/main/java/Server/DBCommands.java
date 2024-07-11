package Server;

import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class DBCommands {

    public static final String GET_COLLECTION_REQUEST = "SELECT * FROM humanbeings";
    public static final String GET_OBJECT_REQUEST = "SELECT ? FROM humanbeings";

    public LinkedList<HumanBeing> loadCollectionFromBD() {
        LinkedList<HumanBeing> collection = new LinkedList<>();
        try {
            PreparedStatement joinStatement = DBConnection.getConnection1().prepareStatement(GET_COLLECTION_REQUEST);
            ResultSet result = joinStatement.executeQuery();

            while (result.next()) {
//                collection.add(exctractLabworkFromResult(result));
            }
            joinStatement.close();
            System.out.println("succeess");

        } catch (SQLException e) {
            System.out.println("error to connect: "+ e.getMessage()+ e.getSQLState());
            System.exit(0);
        }
        return collection;
    }
}
