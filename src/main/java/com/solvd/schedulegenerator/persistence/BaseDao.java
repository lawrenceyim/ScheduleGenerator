package com.solvd.schedulegenerator.persistence;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> findById(long id);
    List<T> findAll();
}
