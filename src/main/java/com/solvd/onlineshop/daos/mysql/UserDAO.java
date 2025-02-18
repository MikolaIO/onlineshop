package com.solvd.onlineshop.daos.mysql;

import com.solvd.onlineshop.daos.IUserDAO;
import com.solvd.onlineshop.models.User;
import com.solvd.onlineshop.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final static Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private final static String GET_BY_FULL_NAME = "SELECT * FROM Users WHERE first_name = ? AND last_name = ?";

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
        return null;
    }

    @Override
    public List<User> getByAddressId(int addressId) {
        return List.of();
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User removeById(long id) {
        return null;
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
