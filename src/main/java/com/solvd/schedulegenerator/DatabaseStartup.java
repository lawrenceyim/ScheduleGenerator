package com.solvd.schedulegenerator;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseStartup {
    private static final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    public static void main(String[] args) {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        try (InputStream inputStream = DatabaseStartup.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Class.forName(prop.getProperty("driver"));

            try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
                 Reader reader = new FileReader("src/main/resources/sql/GenerateDatabase.sql")) {
                ScriptRunner scriptRunner = new ScriptRunner(conn);
                scriptRunner.runScript(reader);
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            OUTPUT_LOGGER.error(e);
        }
    }
}
