package com.solvd.onlineshop.daos;

import com.solvd.onlineshop.models.Credential;

import java.util.List;

public interface ICredentialDAO extends IDAO<Credential> {
    Credential getCredentialByName(String name);
    Credential getCredentialByEmail(String email);
}
