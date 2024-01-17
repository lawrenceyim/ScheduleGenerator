package com.solvd.schedulegenerator.persistence.jdbc;

import com.solvd.schedulegenerator.persistence.CourseDao;

import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    @Override
    public Optional findById(long id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }
}
