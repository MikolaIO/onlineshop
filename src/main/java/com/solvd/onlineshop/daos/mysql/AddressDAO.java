package com.solvd.onlineshop.daos.mysql;

import com.solvd.onlineshop.daos.IAddressDAO;
import com.solvd.onlineshop.daos.ICredentialDAO;
import com.solvd.onlineshop.models.Address;
import com.solvd.onlineshop.services.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class AddressDAO implements IAddressDAO {
    private final static Logger logger = LogManager.getLogger(AddressDAO.class.getName());
    private final static String GET_BY_DATA = "SELECT * FROM Addresses WHERE name = ?";
    private final static String GET_BY_ID = "SELECT * FROM Addresses WHERE id = ?";
    private final static String SAVE = "INSERT INTO Addresses (street, city, postal_code) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE Addresses SET street = ?, city = ?, postal_code = ? WHERE id = ?";
    private final static String REMOVE_BY_ID = "DELETE FROM Addresses WHERE id = ?";

    @Override
    public Address getAddress(String street, String city, String postalCode) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_DATA)) {
            statement.setString(1, street);
            statement.setString(2, city);
            statement.setString(3, postalCode);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedAddress(resultSet);
                }
            }

        } catch (SQLException ex) {
            logger.error("Error during search by data {} {} {} : {}", street, city, postalCode, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return null;
    }

    @Override
    public Address getById(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getMappedAddress(resultSet);
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
    public void save(Address entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getStreet());
            statement.setString(2, entity.getCity());
            statement.setString(3, entity.getPostalCode());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Saving address failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error saving address {} : {}", entity, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        };
    }

    @Override
    public void update(Address entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getStreet());
            statement.setString(2, entity.getCity());
            statement.setString(3, entity.getPostalCode());

            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("Update not successful, non-existent address with id: ");
            }

        } catch (SQLException ex) {
            logger.error("Error updating address with id {} : {}", entity.getId(), ex);
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
            logger.error("Error deleting address with id {} : {}", id, ex);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    private Address getMappedAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address();

        address.setStreet(resultSet.getString("street"));
        address.setCity(resultSet.getString("city"));
        address.setPostalCode(resultSet.getString("postal_code"));

        return address;
    }
}
