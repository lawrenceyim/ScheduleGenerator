package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Schedule;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.persistence.ScheduleDao;
import com.solvd.schedulegenerator.persistence.StudentGroupDao;
import com.solvd.schedulegenerator.service.ScheduleService;
import com.solvd.schedulegenerator.service.StudentGroupService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ScheduleServiceImpl implements ScheduleService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public Optional<Schedule> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
            return scheduleDao.findById(id);
        }
    }

    @Override
    public List<Schedule> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
            return scheduleDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void displaySchedule() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);

            List<Schedule> schedules = scheduleDao.findAll();
            List<StudentGroup> groups = studentGroupDao.findAll();

            StringBuilder sb = new StringBuilder();
            groups.stream().forEach(group -> {
                sb.append("Group ID: ").append(group.getId()).append(System.lineSeparator());
                sb.append(String.format("%-10s%-20s%-20s%-10s", "ID", "Subject", "Day of the week", "Time slot"));
                sb.append(System.lineSeparator());
                schedules.stream()
                        .filter(schedule -> schedule.getCourse().getGroup().equals(group.getId()))
                        .sorted(Comparator.comparing(Schedule::getDayOfWeek).thenComparing(Schedule::getTimeSlot))
                        .forEach(schedule -> {
                            sb.append(String.format("%-10s%-20s%-20s%-10s",
                                    schedule.getId(),
                                    schedule.getCourse().getSubject(),
                                    schedule.getDayOfWeek(),
                                    schedule.getTimeSlot()));
                            sb.append(System.lineSeparator());
                        });
            });
            OUTPUT_LOGGER.info(sb.toString());
        }
    }
}
