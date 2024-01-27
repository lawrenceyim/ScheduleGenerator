package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherDao extends BaseDao<Teacher> {
    void create(Teacher teacher);
    Optional<Teacher> findById(long id);
    List<Teacher> findAll();
    void updateById(long id, Teacher teacher);

    void updateById(@Param("teacher") Teacher teacher, @Param("id") long id);
    Optional<Teacher> findBySubjectId(long subjectId);
}
