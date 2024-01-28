package com.solvd.schedulegenerator.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(long id);

    List<T> findAll();

    void deleteById(long id);
}
