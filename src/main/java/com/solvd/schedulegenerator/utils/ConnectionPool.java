package com.solvd.schedulegenerator.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private volatile static ConnectionPool connectionPool;
    private List<Connection> connections = new ArrayList<>();

    private ConnectionPool() {

        Properties prop = new Properties();
        String propFileName = "config.properties";

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)){
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            Class.forName(prop.getProperty("driver"));
            Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            connections.add(conn);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println(e);
        }
    }

    public synchronized static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {

        while (connections.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                return null;
            }
        }

        Connection connection = connections.get(0);
        connections.remove(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        connections.add(connection);
    }
}
