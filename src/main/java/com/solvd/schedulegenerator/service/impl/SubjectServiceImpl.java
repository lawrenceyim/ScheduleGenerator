package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.service.ListenerPattern.AlertListener;
import com.solvd.schedulegenerator.service.ListenerPattern.SubjectListener;
import com.solvd.schedulegenerator.service.ListenerPattern.SubjectListenerHolder;
import com.solvd.schedulegenerator.service.ListenerPattern.VerifyTeacherAmountListener;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SubjectServiceImpl implements SubjectService {
    private final Logger OUTPUT_LOGGER = LogManager.getLogger(SubjectServiceImpl.class);

    static {
        SubjectListenerHolder.subscribe(new AlertListener());
        SubjectListenerHolder.subscribe(new VerifyTeacherAmountListener());
    }

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

        Optional<Subject> subject = findById(id);

        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            subjectDao.deleteById(id);
            sqlSession.commit();

            if (subject.isPresent()){
                SubjectListenerHolder.onSubjectRemoval(subject.get());
            }
        }
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

    @Override
    public void createSubject(Subject subject) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            subjectDao.create(subject);
            sqlSession.commit();
            SubjectListenerHolder.onNewSubject(subject);
        }
    }
}
