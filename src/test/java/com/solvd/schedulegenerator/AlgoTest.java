package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.service.impl.ScheduleGenerationServiceImpl;
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
        final List<Subject> subjectsWithConstraint = generateSubjectsWithConstraints();
        final int coursesPerDay = 5;

        ScheduleGenerationService service = new ScheduleGenerationServiceImpl.Builder()
                .setGroups(groups)
                .setSubjects(subjects)
                .setSubjectsWithConstraints(subjectsWithConstraint)
                .setCoursesPerDay(coursesPerDay)
                .build();

        // Run algo
        List<Course> schedule = service.generateSchedule();

        // Print result
        printSchedule(schedule);
    }

    private List<StudentGroup> generateGroups() {
        List<StudentGroup> groups = new ArrayList<>();
        IntStream.range(1, 6).forEach(i -> {
            StudentGroup group = new StudentGroup();
            group.setId(i);
            groups.add(group);
        });
        return groups;
    }

    private List<Subject> generateSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Algebra"));
        subjects.add(new Subject(2, "English"));
        subjects.add(new Subject(3, "Geometry"));
        subjects.add(new Subject(4, "Social Studies"));
        subjects.add(new Subject(5, "History"));
        subjects.add(new Subject(6, "Geography"));
        subjects.add(new Subject(7, "Physics"));
        subjects.add(new Subject(8, "Chemistry"));
        subjects.add(new Subject(9, "Biology"));
        subjects.add(new Subject(10, "Computer Science"));
        subjects.add(new Subject(11, "Physical Education"));
        subjects.add(new Subject(12, "Art"));
        subjects.add(new Subject(13, "Music"));
        subjects.add(new Subject(14, "Foreign Language"));
        subjects.add(new Subject(15, "Economics"));
        return subjects;
    }

    private List<Subject> generateSubjectsWithConstraints() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(11, "Physical Education"));
        return subjects;
    }

    private void printSchedule(List<Course> schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-45s%-45s%-20s%-45s", "Group", "Subject", "Room", "Teacher"));
        sb.append(System.lineSeparator());
        schedule.forEach(course -> {
            sb.append(String.format("%-45s%-45s%-20s%-45s",
                    course.getGroup(),
                    course.getSubject(),
                    course.getRoom(),
                    course.getTeacher()));
            sb.append(System.lineSeparator());
        });
        OUTPUT_LOGGER.info(sb.toString());
    }
}
