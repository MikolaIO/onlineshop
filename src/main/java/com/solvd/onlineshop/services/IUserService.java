package com.solvd.onlineshop.services;

import com.solvd.onlineshop.models.Address;
import com.solvd.onlineshop.models.Credential;
import com.solvd.onlineshop.models.User;

import java.util.List;

public interface IUserService {
    User getById(Long id);
    User getByFullName(String firstName, String lastName);
    User getByEmail(String email);
    User create(User user, Credential credential, Address address);
    User update(User user, Credential credential);
    void deleteById(Long id);
}
