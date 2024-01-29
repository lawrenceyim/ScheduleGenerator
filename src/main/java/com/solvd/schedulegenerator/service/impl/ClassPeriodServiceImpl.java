package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.domain.Schedule;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.persistence.ClassPeriodDao;
import com.solvd.schedulegenerator.persistence.ScheduleDao;
import com.solvd.schedulegenerator.persistence.StudentGroupDao;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.service.ClassPeriodService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ClassPeriodServiceImpl implements ClassPeriodService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public Optional<ClassPeriod> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            return classPeriodDao.findById(id);
        }
    }

    @Override
    public List<ClassPeriod> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            return classPeriodDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            classPeriodDao.deleteById(id);
            sqlSession.commit();
        }
    }

    @Override
    public void create(ClassPeriod classPeriod) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            classPeriodDao.create(classPeriod.getTeacherId(), classPeriod.getRoomId(), classPeriod.getGroupId(), classPeriod.getSubjectId(), classPeriod.getTimeslot());
            sqlSession.commit();
        }
    }

    @Override
    public void displaySchedule() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);

            List<ClassPeriod> classPeriods = classPeriodDao.findAll();
            List<StudentGroup> groups = studentGroupDao.findAll();

            StringBuilder sb = new StringBuilder();
            groups.stream().forEach(group -> {
                sb.append("Group ID: ").append(group.getId()).append(System.lineSeparator());
                sb.append(String.format("%-15s%-30s%-15s%-15s",
                        "Time slot",
                        "Subject",
                        "Teacher ID",
                        "Room ID"));
                sb.append(System.lineSeparator());
                classPeriods.stream()
                        .filter(classPeriod -> classPeriod.getGroupId() == group.getId())
                        .sorted(Comparator.comparing(ClassPeriod::getTimeslot))
                        .forEach(classPeriod -> {
                            sb.append(String.format("%-15s%-30s%-15s%-15s",
                                    classPeriod.getTimeslot(),
                                    subjectDao.findById(classPeriod.getSubjectId()).get().getName(),
                                    classPeriod.getTeacherId(),
                                    classPeriod.getRoomId()));
                            sb.append(System.lineSeparator());
                        });
            });
            OUTPUT_LOGGER.info(sb.toString());
        }
    }

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            classPeriodDao.deleteAll();
            sqlSession.commit();
        }
    }
}
