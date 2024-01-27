package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.*;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.service.impl.ScheduleGenerationServiceGeneticAlgo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlgoTest {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Test
    public void generateAndPrintResult() {
        // Instantiate input
        final List<StudentGroup> groups = generateGroups();
        final List<Subject> subjects = generateSubjects();
        final List<Teacher> teachers = generateTeachers();
        final List<Room> rooms = generateRooms();
        final int coursesPerDay = 5;

        ScheduleGenerationService service = new ScheduleGenerationServiceGeneticAlgo.Builder()
                .setGroups(groups)
                .setSubjects(subjects)
                .setTeachers(teachers)
                .setRooms(rooms)
                .setCoursesPerDay(coursesPerDay)
                .build();

        // Run Algo
        List<ClassPeriod> schedule = service.generateSchedule();

        // Print result
        printSchedule(schedule);
    }

    // Test data

    private List<StudentGroup> generateGroups() {
        List<StudentGroup> groups = new ArrayList<>();
        for (int i = 1; i <= 4; i++) { // Assuming 4 student groups
            StudentGroup group = new StudentGroup();
            group.setId(i);
            groups.add(group);
        }
        return groups;
    }

    private List<Subject> generateSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String[] subjectNames = {"Mathematics", "Science", "History", "Geography", "English",
                "Physical Education", "Art", "Music", "Biology", "Chemistry",
                "Physics", "Computer Science", "Literature", "Social Studies", "Economics"};

        for (int i = 0; i < subjectNames.length; i++) {
            Subject subject = new Subject();
            subject.setId(i + 1);
            subject.setName(subjectNames[i]);
            subject.setShouldBeLast(subjectNames[i].equals("Physical Education")); // PE should be last
            subjects.add(subject);
        }
        return subjects;
    }

    private List<Teacher> generateTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) { // Assuming 15 teachers
            Teacher teacher = new Teacher();
            teacher.setId(i);
            teacher.setFirstName("Teacher");
            teacher.setLastName(String.valueOf(i));
            teachers.add(teacher);
        }
        return teachers;
    }

    private List<Room> generateRooms() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            Room room = new Room();
            room.setId(i);
            room.setBuilding("Building " + ((i - 1) / 4 + 1));
            room.setFloor((i - 1) % 4 + 1);
            room.setRoomNumber(100 + i);
            rooms.add(room);
        }
        return rooms;
    }

    private List<Subject> generateSubjectsWithConstraints() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(11, "Physical Education"));
        return subjects;
    }





    private void printSchedule(List<ClassPeriod> schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-45s%-45s%-20s%-20s%-45s", "Group", "Subject", "Room", "Teacher", "Timeslot"));
        sb.append(System.lineSeparator());
        schedule.forEach(period -> {
            sb.append(String.format("%-45s%-45s%-20s%-20s%-45s",
                    period.getGroupId(),
                    period.getSubjectId(),
                    period.getRoomId(),
                    period.getTeacherId(),
                    period.getTimeslot()));
            sb.append(System.lineSeparator());
        });
        OUTPUT_LOGGER.info(sb.toString());
    }


}
