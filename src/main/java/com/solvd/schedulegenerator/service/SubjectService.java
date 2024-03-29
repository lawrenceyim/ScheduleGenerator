package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.domain.Subject;

public interface SubjectService extends BaseService<Subject> {
    void displayAllSubjects();
    void createSubject(Subject subject);
}
