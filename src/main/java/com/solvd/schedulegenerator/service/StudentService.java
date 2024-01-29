package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentService extends BaseService<Student> {
    void create(@Param("student") Student student, @Param("groupId") long groupId);
    void displayAllStudents();
}
