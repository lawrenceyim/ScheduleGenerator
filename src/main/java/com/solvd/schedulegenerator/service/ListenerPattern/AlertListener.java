package com.solvd.schedulegenerator.service.ListenerPattern;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlertListener implements SubjectListener{
    private final Logger OUTPUT_LOGGER = LogManager.getLogger(AlertListener.class);

    @Override
    public void onNewSubject(Subject subject) {
        OUTPUT_LOGGER.info("Schedule outdated due to new subject");
    }

    @Override
    public void onSubjectRemoval(Subject subject) {
        OUTPUT_LOGGER.info("Schedule outdated due to removed subject");
    }
}
