package com.solvd.onlineshop.daos.mysql;

import com.solvd.onlineshop.daos.IUserDAO;
import com.solvd.onlineshop.models.User;
import com.solvd.onlineshop.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final static Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private final static String GET_BY_FULL_NAME = "SELECT * FROM Users WHERE first_name = ? AND last_name = ?";
    private final static String GET_BY_EMAIL = "SELECT * FROM Users WHERE id IN (SELECT user_id FROM Credentials WHERE mail = ?)";
    private final static String GET_BY_ADDRESS_ID = "SELECT * FROM Users WHERE address_id = ?)";
    private final static String GET_BY_ID = "SELECT * FROM Users WHERE id = ?)";
    private final static String SAVE = "INSERT INTO Users (first_name, last_name, age, address_id) VALUES (?, ?, ?, ?))";
    private final static String UPDATE = "UPDATE Users SET first_name = ?, last_name = ?, age = ?, address_id = ? WHERE id = ?";
    private final static String REMOVE_BY_ID = "DELETE FROM Users WHERE id = ?";

    @Override
    public User getUserByFullName(String firstName, String lastName) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_FULL_NAME)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by full name {} {} : {}", firstName, lastName, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by email {} : {}", email, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<User> getByAddressId(int addressId) {
        List<User> users = new ArrayList<>();

        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ADDRESS_ID)) {
            statement.setInt(1, addressId);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    users.add(getMappedUser(resultSet));
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by address ID {} : {}", addressId, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return users;
    }

    @Override
    public User getById(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedUser(resultSet);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by ID {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void save(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getAddressId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error saving user {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        };
    }

    @Override
    public void update(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getAddressId());
            statement.setLong(5, entity.getId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Update not successful, non-existent user with id: ");
            }

        } catch (SQLException ex) {
            logger.error("Error updating user with id {} : {}", entity.getId(), ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        };
    }

    @Override
    public void removeById(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error deleting user with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private User getMappedUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setAge(resultSet.getInt("age"));
        user.setAddressId(resultSet.getInt("address_id"));

        return user;
    }
}
