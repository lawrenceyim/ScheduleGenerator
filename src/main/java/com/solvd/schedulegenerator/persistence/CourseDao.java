package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Course;

public interface CourseDao extends BaseDao {
    void create(Course course);
}
