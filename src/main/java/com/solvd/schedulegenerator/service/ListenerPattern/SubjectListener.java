package com.solvd.schedulegenerator.service.ListenerPattern;

import com.solvd.schedulegenerator.domain.Subject;

public interface SubjectListener {

    void onNewSubject(Subject subject);

    void onSubjectRemoval(Subject subject);
}
