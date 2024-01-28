package com.solvd.schedulegenerator.menu.impl;

import com.solvd.schedulegenerator.menu.IMenu;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.service.impl.TeacherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu implements IMenu {
    private final TeacherService teacherService = new TeacherServiceImpl();
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() {
        OUTPUT_LOGGER.info("Schedule Generator");
        OUTPUT_LOGGER.info("1. Generate schedule");
        OUTPUT_LOGGER.info("2. View schedule");
        OUTPUT_LOGGER.info("3. View student groups");
        OUTPUT_LOGGER.info("4. View students");
        OUTPUT_LOGGER.info("5. View subjects");
        OUTPUT_LOGGER.info("6. View teachers");
        OUTPUT_LOGGER.info("7. Exit");
    }

    @Override
    public int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            if (choice >= 1 && choice <= 7) {
                return choice;
            }
        } catch (InputMismatchException e) {
            OUTPUT_LOGGER.info("Invalid input. Returning to menu");
        }
        return 0;
    }

    @Override
    public void performUserChoice(int choice) {
        switch (choice) {
            case 1:
                // TODO: Generate schedule
                return;
            case 2:
                // TODO: View schedule
                return;
            case 3:
                // TODO: View student groups
                return;
            case 4:
                // TODO: View students
                return;
            case 5:
                // TODO: View subjects
                return;
            case 6:
                teacherService.displayAllTeachers();
                return;
            case 7:
                System.exit(0);
                return;
            default:
                return;
        }
    }
}
