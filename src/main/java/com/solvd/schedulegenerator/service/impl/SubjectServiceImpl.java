package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.List;
import java.util.Optional;

public class SubjectServiceImpl implements SubjectService {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    @Override
    public Optional<Subject> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            return subjectDao.findById(id);
        }
    }

    @Override
    public List<Subject> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            return subjectDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void displayAllSubjects() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            List<Subject> subjects = subjectDao.findAll();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s%-30s", "Subject ID", "Subject"));
            sb.append(System.lineSeparator());
            subjects.stream().forEach(subject -> {
                sb.append(String.format("%-15s%-30s",
                        subject.getId(),
                        subject.getName()));
                sb.append(System.lineSeparator());
            });

            OUTPUT_LOGGER.info(sb.toString());
        }
    }
}
