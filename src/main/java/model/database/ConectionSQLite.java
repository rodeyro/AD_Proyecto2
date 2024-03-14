package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionSQLite {
    public static Connection SQLite(String path){
        Connection connection=null;
        try {
            String url = "jdbc:sqlite:" + path;
            connection = DriverManager.getConnection(url);
            System.out.println("Conexion SQLite");
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
