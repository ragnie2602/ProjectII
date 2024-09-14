package Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection database = null;

    private static String host = "jdbc:sqlserver://localhost:1433";
    private static String databaseName = "INTELLIQUIZ";
    private static String username = "sa";
    private static String password = "admin";

    public static boolean connect() {
        try {
            String connectionUrl = host + ";"
                    + "database=" + databaseName + ";"
                    + "user=" + username + ";"
                    + "password=" + password + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=3;";
            database = DriverManager.getConnection(connectionUrl);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setHost(String host) {
        DBConnection.host = host;
    }

    public void setDatabaseName(String databaseName) {
        DBConnection.databaseName = databaseName;
    }

    public void setUsername(String username) {
        DBConnection.username = username;
    }

    public void setPasswrod(String password) {
        DBConnection.password = password;
    }
}