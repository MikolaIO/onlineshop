package com.solvd.onlineshop.daos;

public interface IDAO<T> {
    T getById(long id);
    void save(T entity);
    void update(T entity);
    void removeById(long id);
}
