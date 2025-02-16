package com.solvd.onlineshop.daos;

import com.solvd.onlineshop.models.User;

import java.util.List;

public interface IUserDAO extends IDAO<User> {
    User getUserByFullName(String firstName, String lastName);
    User getUserByEmail(String email);
    List<User> getByAddressId(int addressId);
}
