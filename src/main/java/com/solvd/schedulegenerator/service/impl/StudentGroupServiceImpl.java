package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.persistence.StudentGroupDao;
import com.solvd.schedulegenerator.service.StudentGroupService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentGroupServiceImpl implements StudentGroupService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public Optional<StudentGroup> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            return studentGroupDao.findById(id);
        }
    }

    @Override
    public List<StudentGroup> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            return studentGroupDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void displayAllStudentGroups() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            List<StudentGroup> studentGroups = studentGroupDao.findAll();

            StringBuilder sb = new StringBuilder();
            studentGroups.stream().forEach(group -> {
                List<Student> students = group.getStudents();
                sb.append("Group ID: ").append(group.getId()).append(System.lineSeparator());
                sb.append(String.format("%-15s%-30s%-30s", "Student ID", "First Name", "Last Name"));
                sb.append(System.lineSeparator());
                students.stream().forEach(student -> {
                    sb.append(String.format("%-15s%-30s%-30s",
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName()));
                    sb.append(System.lineSeparator());
                });
                sb.append(System.lineSeparator());
            });
            OUTPUT_LOGGER.info(sb.toString());
        }
    }
}
