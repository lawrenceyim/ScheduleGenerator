package com.solvd.schedulegenerator.persistence;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> findById(long id);

    List<T> findAll();

    void deleteById(long id);
}
