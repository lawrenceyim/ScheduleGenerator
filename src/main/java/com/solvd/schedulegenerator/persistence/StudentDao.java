package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao extends BaseDao<Student>{
    void create(@Param("student") Student student, @Param("groupId") long groupId);
    void updateById(@Param("student") Student student, @Param("studentId") long studentId, @Param("groupId") long groupId);
}
