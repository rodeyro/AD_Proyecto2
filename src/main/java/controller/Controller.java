package controller;

import model.database.*;
import utils.ANSI;
import view.DatabaseConfigView;
import view.MainView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private static final Controller MAIN_CONTROLLER = new Controller();

    private Controller() {
    }

    public static Controller getInstance() {
        return MAIN_CONTROLLER;
    }

    private static Connection connection;

    private static Config config;

    public void start() {
        ANSI.printBlue("Controller.start()");
        connection = ConfigManager.CargarBinario();
        if (connection == null) {
            new DatabaseConfigView(this, connection, false).setVisible(true);
            config = ConfigManager.CargarConfig();
        } else {
            new MainView(this, connection).setVisible(true);
            config = ConfigManager.CargarConfig();
        }
    }

    public void saveMySQLConfiguration(String Host, String port, String database, String Username, String Password) {
        Connection connectionsave = ConectionMYSQL.MySQLConnector(Host, port, Username, Password);
        if (connectionsave == null) {
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            connection = connectionsave;
            DataBase.BaseDatosMYSQL(connection, database);
            ConfigManager.guardardatos(Host, port, database, Username, Password);
        }
        TableCreationMYSQL.Table(connection, database);
    }

    public static void FillBooks(String title, String autor) {
        PreparedStatement statementLibros;
        try {
            PreparedStatement use = connection.prepareStatement("USE " + config.getDatabase());
            use.executeUpdate();
            statementLibros = connection.prepareStatement("INSERT INTO libros(titulo, autor) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            statementLibros.setString(1, title);
            statementLibros.setString(2, autor);
            statementLibros.executeUpdate();
            use.close();
            System.out.println("Nuevos datos de libros añadidos con éxito");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void FillMembers(int dni, String title, String correo) {
        PreparedStatement statementMiembros;
        try {
            PreparedStatement use = connection.prepareStatement("USE " + config.getDatabase());
            use.executeUpdate();
            statementMiembros = connection.prepareStatement("INSERT INTO socios(dni, nombre, correo) VALUES(?,?,?);");
            statementMiembros.setInt(1, dni);
            statementMiembros.setString(2, title);
            statementMiembros.setString(3, correo);
            statementMiembros.executeUpdate();
            use.close();
            System.out.println("Nuevos datos de socios añadidos con éxito");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[][] cargarDatosEnTablaSocios(String[][] data) {
        try {
            PreparedStatement use = connection.prepareStatement("USE " + config.getDatabase());
            use.executeUpdate();
            String sql = "SELECT * FROM socios";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> datas = new ArrayList<>();

            while (resultSet.next()) {
                String dni = resultSet.getString("dni");
                String nombre = resultSet.getString("nombre");
                String correo = resultSet.getString("correo");
                String[] row = {dni, nombre, correo};
                datas.add(row);
            }

            resultSet.close();
            statement.close();

            String[][] dataArray = new String[datas.size()][3];
            for (int i = 0; i < datas.size(); i++) {
                dataArray[i] = datas.get(i);
            }
            data = dataArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String[][] cargarDatosEnTablaLibrosPrestados(String[][] data) {
        try {
            PreparedStatement use = connection.prepareStatement("USE " + config.getDatabase());
            use.executeUpdate();
            String sql = "SELECT id,titulo,autor FROM libros WHERE prestado = TRUE";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> datas = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String titulo = resultSet.getString("titulo");
                String autor = resultSet.getString("autor");
                String[] row = {id, titulo, autor};
                datas.add(row);
            }

            resultSet.close();
            statement.close();

            String[][] dataArray = new String[datas.size()][3];
            for (int i = 0; i < datas.size(); i++) {
                dataArray[i] = datas.get(i);
            }
            data = dataArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String[][] cargarDatosEnTablaLibrosDisponibles(String[][] data) {
        try {
            PreparedStatement use = connection.prepareStatement("USE " + config.getDatabase());
            use.executeUpdate();
            String sql = "SELECT id,titulo,autor FROM libros WHERE prestado = FALSE";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<String[]> datas = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String titulo = resultSet.getString("titulo");
                String autor = resultSet.getString("autor");
                String[] row = {id, titulo, autor};
                datas.add(row);
            }

            resultSet.close();
            statement.close();

            String[][] dataArray = new String[datas.size()][3];
            for (int i = 0; i < datas.size(); i++) {
                dataArray[i] = datas.get(i);
            }
            data = dataArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
