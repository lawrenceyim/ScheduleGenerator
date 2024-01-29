package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.persistence.StudentDao;
import com.solvd.schedulegenerator.service.StudentService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public void create(Student student, long groupId) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            studentDao.create(student, groupId);
        }
    }

    @Override
    public Optional<Student> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            return studentDao.findById(id);
        }
    }

    @Override
    public List<Student> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            return studentDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            studentDao.deleteById(id);
        }
    }

    @Override
    public void displayAllStudents() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            List<Student> students = studentDao.findAll();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s%-30s%-30s", "Student ID", "First Name", "Last Name"));
            sb.append(System.lineSeparator());
            students.stream().forEach(student -> {
                sb.append(String.format("%-15s%-30s%-30s",
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName()));
                sb.append(System.lineSeparator());
            });
            OUTPUT_LOGGER.info(sb.toString());
        }
    }
}
