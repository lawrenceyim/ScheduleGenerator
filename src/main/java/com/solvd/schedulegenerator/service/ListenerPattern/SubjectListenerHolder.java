package com.solvd.schedulegenerator.service.ListenerPattern;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ListenerPattern.SubjectListener;

import java.util.ArrayList;
import java.util.List;

public class SubjectListenerHolder{

    private static final List<SubjectListener> listeners = new ArrayList<>();

    public static void subscribe(SubjectListener subjectListener){
        listeners.add(subjectListener);
    }

    public static void unsubscribe(SubjectListener subjectListener){
        listeners.remove(subjectListener);
    }

    public static void onNewSubject(Subject subject){
        for (SubjectListener listener : listeners){
            listener.onNewSubject(subject);
        }
    }

    public static void onSubjectRemoval(Subject subject){
        for (SubjectListener listener : listeners){
            listener.onSubjectRemoval(subject);
        }
    }

}
