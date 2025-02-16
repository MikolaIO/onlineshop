package com.solvd.onlineshop.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
    private final List<Connection> pool;
    private final List<Connection> connectedPool = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 10;
    private static ConnectionPool INSTANCE;
    private static final Properties properties = new Properties();
    private static final String PATH_TO_PROPERTIES = "src/main/resources/db.properties";

    private ConnectionPool() {
        this.pool = new ArrayList<>();
        loadProperties();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
    }

    public static ConnectionPool getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ConnectionPool();
        }
        return INSTANCE;
    }

    public int getSize() {
        return INITIAL_POOL_SIZE;
    }

    public int getConnectedSize() {
        return connectedPool.size();
    }

    public void getConnection() {
        Connection connection = pool.getLast();
        connectedPool.add(connection);
        logger.info("Got connection");
    }

    public void releaseConnection() {
        pool.add(connectedPool.getLast());
        connectedPool.removeLast();
        logger.info("Released connection");
    }

    private static Connection createConnection() {
        logger.info("Created connection");
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.usr"),
                    properties.getProperty("db.passwd"));
        } catch (SQLException ex) {
            logger.error("Failed to establish connection: ", ex);
        }
        return conn;
    }

    private void loadProperties() {
        try (FileInputStream input = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Failed to load database properties file", e);
        }
    }
}
