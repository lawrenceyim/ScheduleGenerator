package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.domain.Teacher;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.persistence.TeacherDao;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl implements TeacherService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public Optional<Teacher> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            return teacherDao.findById(id);
        }
    }

    @Override
    public List<Teacher> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            return teacherDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            teacherDao.deleteById(id);
            sqlSession.commit();
        }
    }

    @Override
    public Optional<Teacher> findBySubjectId(long subjectId) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            return teacherDao.findBySubjectId(subjectId);
        }
    }

    @Override
    public void displayAllTeachers() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            List<Teacher> teachers = teacherDao.findAll();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s%-30s%-30s%-15s%-30s",
                    "Teacher ID",
                    "First Name",
                    "Last Name",
                    "Subject ID",
                    "Subject"));
            sb.append(System.lineSeparator());
            teachers.stream().forEach(teacher -> {
                Optional<Subject> subject = subjectDao.findById(teacher.getSubjectId());
                sb.append(String.format("%-15s%-30s%-30s%-15s%-30s",
                        teacher.getId(),
                        teacher.getFirstName(),
                        teacher.getLastName(),
                        teacher.getSubjectId(),
                        subject.get().getName()));
                sb.append(System.lineSeparator());
            });

            OUTPUT_LOGGER.info(sb.toString());
        }
    }
}
