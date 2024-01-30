package com.solvd.schedulegenerator.menu.impl;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.menu.IMenu;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class NewSubjectMenu implements IMenu<String> {

    private final SubjectService subjectService = new SubjectServiceImpl();
    private final Logger OUTPUT_LOGGER = LogManager.getLogger(NewSubjectMenu.class);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() {
        OUTPUT_LOGGER.info("Enter new subject name");
    }

    @Override
    public String getUserChoice() {
        return scanner.nextLine();
    }

    @Override
    public void performUserChoice(String subjectName) {
        if (subjectName instanceof String){
            Subject subject = new Subject();
            subject.setName(subjectName);
            subjectService.createSubject(subject);
        }
    }
}
