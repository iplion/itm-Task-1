package jm.task.core.jdbc.util;

import jm.task.core.jdbc.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private final static Properties PROPERTIES = AppConfig.getDbConfig();

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
            PROPERTIES.getProperty("url"),
            PROPERTIES.getProperty("username"),
            PROPERTIES.getProperty("password")
        );
    }
}
