package com.solvd.schedulegenerator.menu.impl;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.menu.IMenu;
import com.solvd.schedulegenerator.persistence.ClassPeriodDao;
import com.solvd.schedulegenerator.persistence.RoomDao;
import com.solvd.schedulegenerator.persistence.StudentGroupDao;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.persistence.TeacherDao;
import com.solvd.schedulegenerator.service.ClassPeriodService;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.service.ScheduleService;
import com.solvd.schedulegenerator.service.StudentGroupService;
import com.solvd.schedulegenerator.service.StudentService;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.service.impl.ClassPeriodServiceImpl;
import com.solvd.schedulegenerator.service.impl.ScheduleGenerationServiceGeneticAlgo;
import com.solvd.schedulegenerator.service.impl.ScheduleServiceImpl;
import com.solvd.schedulegenerator.service.impl.StudentGroupServiceImpl;
import com.solvd.schedulegenerator.service.impl.StudentServiceImpl;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import com.solvd.schedulegenerator.service.impl.TeacherServiceImpl;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu implements IMenu {

    private final ClassPeriodService classPeriodService = new ClassPeriodServiceImpl();
    private final StudentGroupService studentGroupService = new StudentGroupServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final SubjectService subjectService = new SubjectServiceImpl();
    private final TeacherService teacherService = new TeacherServiceImpl();
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");
    private final Scanner scanner = new Scanner(System.in);
    private ScheduleGenerationService scheduleGenerationService;


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
                // Delete old schedule
                classPeriodService.deleteAll();

                try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
                    int coursesPerDay = 5;
                    RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
                    StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
                    SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
                    TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);

                    scheduleGenerationService = new ScheduleGenerationServiceGeneticAlgo.Builder()
                            .setGroups(studentGroupDao.findAll())
                            .setSubjects(subjectDao.findAll())
                            .setTeachers(teacherDao.findAll())
                            .setRooms(roomDao.findAll())
                            .setCoursesPerDay(coursesPerDay)
                            .build();
                    OUTPUT_LOGGER.info("Generating schedule...");
                    scheduleGenerationService.generateAndSaveSchedule();
                    OUTPUT_LOGGER.info("Schedule generated and stored in the database.");
                }
                return;
            case 2:
                classPeriodService.displaySchedule();
                return;
            case 3:
                studentGroupService.displayAllStudentGroups();
                return;
            case 4:
                studentService.displayAllStudents();
                return;
            case 5:
                subjectService.displayAllSubjects();
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
