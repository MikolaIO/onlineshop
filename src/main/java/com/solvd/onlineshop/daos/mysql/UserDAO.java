package com.solvd.onlineshop.daos.mysql;

import com.solvd.onlineshop.daos.IUserDAO;
import com.solvd.onlineshop.models.User;

import java.util.List;

public class UserDAO implements IUserDAO {

    @Override
    public User getUserByFullName(String firstName, String lastName) {
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
}
