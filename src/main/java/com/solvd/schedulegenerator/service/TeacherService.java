package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.domain.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService extends BaseService<Teacher> {

    Optional<Teacher>  findBySubjectId(long subjectId);
}
