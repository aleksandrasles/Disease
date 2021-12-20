package com.application.disease.strategy;

import com.application.disease.model.Region;

import java.util.List;

public interface GeneralRepository<T> {

    T create(T t);

    List<T> findAll();

    T findById(String id);

    T findByName(String name);

    T update(T t);

    void removeById(String id);
}
