package com.solvd.onlineshop.daos;

public interface IDAO<T> {
    T getById(long id);
    T save(T entity);
    T update(T entity);
    T removeById(long id);
}
