package com.solvd.onlineshop.services;

import com.solvd.onlineshop.daos.IAddressDAO;
import com.solvd.onlineshop.daos.ICredentialDAO;
import com.solvd.onlineshop.daos.IUserDAO;
import com.solvd.onlineshop.daos.mysql.AddressDAO;
import com.solvd.onlineshop.daos.mysql.CredentialDAO;
import com.solvd.onlineshop.daos.mysql.UserDAO;
import com.solvd.onlineshop.models.Address;
import com.solvd.onlineshop.models.Credential;
import com.solvd.onlineshop.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserService.class.getName());
    private final IUserDAO userDAO = new UserDAO();
    private final ICredentialDAO credentialDAO = new CredentialDAO();
    private final IAddressDAO addressDAO = new AddressDAO();

    public UserService() {
    }

    @Override
    public User getById(Long id) {
        logger.info("Getting user by ID: {}", id);
        User user = userDAO.getById(id);
        if (user == null) {
            logger.warn("User not found with ID: {}", id);
        }
        return user;
    }

    @Override
    public User getByFullName(String firstName, String lastName) {
        logger.info("Getting user by full name: {} {}", firstName, lastName);
        User user = userDAO.getUserByFullName(firstName, lastName);
        if (user == null) {
            logger.warn("User not found with name: {} {}", firstName, lastName);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        logger.info("Getting user by email: {}", email);
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email);
        }
        return user;
    }

    @Override
    public User create(User user, Credential credential, Address address) {
        logger.info("Creating new user with email: {}", credential.getMail());

        if (userDAO.getUserByEmail(credential.getMail()) != null) {
            logger.error("User with email {} already exists", credential.getMail());
            throw new IllegalArgumentException("User with this email already exists");
        }

        addressDAO.save(address);
        user.setAddressId(address.getId());
        userDAO.save(user);
        credential.setUserId(user.getId());
        credentialDAO.save(credential);

        User savedUser = userDAO.getUserByEmail(credential.getMail());
        if (savedUser != null) {
            logger.info("Successfully created user with ID: {}", savedUser.getId());
        } else {
            logger.error("Failed to create user");
        }
        return savedUser;
    }

    @Override
    public User update(User user, Credential credential) {
        logger.info("Updating user with ID: {}", user.getId());

        if (userDAO.getById(user.getId()) == null) {
            logger.error("User with ID {} not found for update", user.getId());
            throw new IllegalArgumentException("User not found");
        }
        userDAO.update(user);
        User updatedUser = userDAO.getUserByEmail(credential.getMail());
        if (updatedUser != null) {
            logger.info("Successfully updated user with ID: {}", user.getId());
        } else {
            logger.error("Failed to update user with ID: {}", user.getId());
        }
        return updatedUser;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with ID: {}", id);

        if (userDAO.getById(id) == null) {
            logger.error("User with ID {} not found for deletion", id);
            throw new IllegalArgumentException("User not found");
        }

        userDAO.removeById(id);
        logger.info("Successfully deleted user with ID: {}", id);
    }
}
