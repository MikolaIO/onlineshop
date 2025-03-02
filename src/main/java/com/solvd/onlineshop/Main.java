package com.solvd.onlineshop;

import com.solvd.onlineshop.models.Address;
import com.solvd.onlineshop.models.Credential;
import com.solvd.onlineshop.models.User;
import com.solvd.onlineshop.services.ConnectionPool;
import com.solvd.onlineshop.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        UserService userService = new UserService();
        Address address = new Address("Zgonna", "Wroclaw", "50-251");
        User user = new User("Piotr", "Nijaki", 50);
        Credential credential = new Credential("spider", "passwd", "spider@gmail.com");

        user = userService.create(user, credential, address);
        logger.info(user);
    }
}