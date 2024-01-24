package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherDao extends BaseDao<Teacher> {
    void create(Teacher teacher);

    void updateById(@Param("teacher") Teacher teacher, @Param("id") long id);
}
