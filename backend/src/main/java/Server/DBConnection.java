package Server;

import lombok.Getter;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


@Getter
public class DBConnection {
    static String userName = "";;
    static String password = "";
    static String jdbcURL = "";
    static Connection connection;
    public static void getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(jdbcURL, userName, password);

        } catch (NoSuchElementException e) {
            System.out.println("Не найдены данные для входа в бд");
            throw new NoSuchElementException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Connection getConnection1() {
        return connection;
    }
}