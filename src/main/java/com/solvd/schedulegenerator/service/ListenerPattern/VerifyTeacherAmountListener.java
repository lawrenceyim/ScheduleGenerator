package com.solvd.schedulegenerator.service.ListenerPattern;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import com.solvd.schedulegenerator.service.impl.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerifyTeacherAmountListener implements SubjectListener{

    private final Logger OUTPUT_LOGGER = LogManager.getLogger(VerifyTeacherAmountListener.class);
    private final SubjectService subjectService = new SubjectServiceImpl();
    private final TeacherService teacherService = new TeacherServiceImpl();

    @Override
    public void onNewSubject(Subject subject) {
        long subjectAmount = subjectService.findAll().stream().count();
        long teacherAmount = teacherService.findAll().stream().count();

        if (teacherAmount < subjectAmount){
            OUTPUT_LOGGER.warn("Add more teachers. Amount of teachers is less than the amount of subjects.");
        }
    }

    @Override
    public void onSubjectRemoval(Subject subject) {
    }
}
