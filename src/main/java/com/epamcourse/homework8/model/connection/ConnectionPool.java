package com.epamcourse.homework8.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final String CONNECTION_PROPERTY_USER = "user";
    private static final String CONNECTION_PROPERTY_PASSWORD = "password";
    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final int DEFAULT_POOL_SIZE = 5;
    private final Logger log = LogManager.getLogger(ConnectionPool.class);
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenAwayConnections;

    private static class PoolHolder {
        private static final ConnectionPool POOL_INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return PoolHolder.POOL_INSTANCE;
    }

    ConnectionPool() {
        Properties properties = new Properties();
        properties.setProperty(CONNECTION_PROPERTY_USER, USER);
        properties.setProperty(CONNECTION_PROPERTY_PASSWORD, PASSWORD);
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.add(new ProxyConnection(DriverManager.getConnection(URL, properties)));
            } catch (SQLException throwables) {
                log.fatal(throwables);
            }
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            givenAwayConnections.remove(connection);
            freeConnection.add((ProxyConnection) connection);
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (InterruptedException e) {
                log.error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
