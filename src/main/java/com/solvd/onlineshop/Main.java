package com.solvd.onlineshop;

import com.solvd.onlineshop.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();
    }
}