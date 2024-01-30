package com.solvd.schedulegenerator.menu.impl;

import com.solvd.schedulegenerator.menu.IMenu;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SubjectDeleteMenu implements IMenu<Long> {

    private final SubjectService subjectService = new SubjectServiceImpl();
    private final Logger OUTPUT_LOGGER = LogManager.getLogger(SubjectDeleteMenu.class);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() {
        OUTPUT_LOGGER.info("Enter subject id to delete");
        subjectService.displayAllSubjects();
    }

    @Override
    public Long getUserChoice() {
        try {
            Long choice = scanner.nextLong();
            if (choice >= 0) {
                return choice;
            }
        } catch (InputMismatchException e) {
            OUTPUT_LOGGER.info("Invalid input. Returning to menu");
        }
        return 0L;
    }

    @Override
    public void performUserChoice(Long subjectId) {
        if (subjectId instanceof Long){
            subjectService.deleteById(subjectId);
        }
    }
}
