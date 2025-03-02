package com.solvd.onlineshop.daos.mysql;

import com.solvd.onlineshop.daos.ICredentialDAO;
import com.solvd.onlineshop.daos.IUserDAO;
import com.solvd.onlineshop.models.Credential;
import com.solvd.onlineshop.models.User;
import com.solvd.onlineshop.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialDAO implements ICredentialDAO {
    private final static Logger logger = LogManager.getLogger(CredentialDAO.class.getName());
    private final static String GET_BY_NAME = "SELECT * FROM Credentials WHERE name = ?";
    private final static String GET_BY_EMAIL = "SELECT * FROM Credentials WHERE mail = ?";
    private final static String GET_BY_ID = "SELECT * FROM Credentials WHERE id = ?";
    private final static String SAVE = "INSERT INTO Credentials (name, password, mail, user_id) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Credentials SET name = ?, password = ?, mail = ?, user_id = ? WHERE id = ?";
    private final static String REMOVE_BY_ID = "DELETE FROM Credentials WHERE id = ?";

    @Override
    public Credential getCredentialByName(String name) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedCredential(resultSet);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by name {} : {}", name, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Credential getCredentialByEmail(String email) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedCredential(resultSet);
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
    public Credential getById(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedCredential(resultSet);
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
    public void save(Credential entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getMail());
            statement.setLong(4, entity.getUserId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving credentials failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error saving credentials {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        };
    }

    @Override
    public void update(Credential entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getMail());
            statement.setLong(4, entity.getUserId());
            statement.setLong(5, entity.getId());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Update not successful, non-existent credentials with id: ");
            }

        } catch (SQLException ex) {
            logger.error("Error updating credentials with id {} : {}", entity.getId(), ex);
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
            logger.error("Error deleting credentials with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Credential getMappedCredential(ResultSet resultSet) throws SQLException {
        Credential credential = new Credential();

        credential.setName(resultSet.getString("name"));
        credential.setPassword(resultSet.getString("password"));
        credential.setMail(resultSet.getString("mail"));
        credential.setUserId(resultSet.getLong("user_id"));

        return credential;
    }
}
