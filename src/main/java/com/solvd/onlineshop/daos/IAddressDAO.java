package com.solvd.onlineshop.daos;

import com.solvd.onlineshop.models.Address;
import com.solvd.onlineshop.models.Credential;

public interface IAddressDAO extends IDAO<Address> {
    Address getAddress(String street, String city, String postalCode);
}
