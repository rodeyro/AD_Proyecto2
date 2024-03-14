package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionMYSQL {
    public static Connection MySQLConnector(String Host, String port, String Username, String Password) {
        try {
            String url = "jdbc:mysql://" + Host + ":" + port;
            Connection connection = DriverManager.getConnection(url,Username,Password);
            System.out.println("Conexion MySQL");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
