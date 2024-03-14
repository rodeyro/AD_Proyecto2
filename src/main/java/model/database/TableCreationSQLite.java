package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreationSQLite {
    public static void Table(Connection connection) {

        try {
            PreparedStatement librosStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS libros ("
                            + "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                            + "titulo VARCHAR(100), "
                            + "autor VARCHAR(100),"
                            + "prestado BOOLEAN DEFAULT FALSE"
                            + ")"
            );
            librosStatement.executeUpdate();
            librosStatement.close();
            System.out.println("Tabla 'libros' creada correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        try {
            PreparedStatement sociosStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS socios ("
                            + "dni INTEGER PRIMARY KEY, "
                            + "nombre VARCHAR(100), "
                            + "correo VARCHAR(100) "
                            + ")"
            );

            sociosStatement.executeUpdate();
            sociosStatement.close();
            System.out.println("Tabla 'socios' creada correctamente.");

        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
        }

        try {
            PreparedStatement prestamosStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS préstamos ("
                            + "UUID INT PRIMARY KEY, "
                            + "fecha_inicio DATE, "
                            + "fecha_fin DATE "
                            + ")"
            );

            prestamosStatement.executeUpdate();
            prestamosStatement.close();
            System.out.println("Tabla 'préstamos' creada correctamente.");

        } catch (SQLException sqle) {
            System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
        }
    }
}
