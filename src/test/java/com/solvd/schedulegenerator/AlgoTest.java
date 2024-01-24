package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.service.impl.ScheduleGenerationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

import java.util.List;

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

    // TODO
    private List<StudentGroup> generateGroups() {
        return null;
    }

    // TODO
    private List<Subject> generateSubjects() {
        return null;
    }

    // TODO
    private List<Subject> generateSubjectsWithConstraints() {
        return null;
    }

    // TODO
    private void printSchedule(List<Course> schedule ) {

    }
}
