package model.database;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class ConfigManager {
    public static void guardardatos(String Host, String port, String database, String Username, String Password) {
        Config config = new Config(Host, port, database, Username, Password);

        String fileName = "config.bin";

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Connection CargarBinario() {
        Connection connection = null;
        Path path = Paths.get("config.bin");
        if (path.toFile().exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                Config config = (Config) inputStream.readObject();
                connection = ConectionMYSQL.MySQLConnector(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
                DataBase.BaseDatosMYSQL(connection,config.getDatabase());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Config CargarConfig() {
        Connection connection = null;
        Path path = Paths.get("config.bin");
        Config config = null;
        if (path.toFile().exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                config = (Config) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return config;
    }
}
