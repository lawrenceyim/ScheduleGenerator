package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.SubjectConstraint;
import com.solvd.schedulegenerator.persistence.SubjectConstraintDao;
import com.solvd.schedulegenerator.service.SubjectConstraintService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SubjectConstraintServiceImpl implements SubjectConstraintService {
    @Override
    public Optional<SubjectConstraint> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            SubjectConstraintDao subjectConstraintDao = sqlSession.getMapper(SubjectConstraintDao.class);
            return subjectConstraintDao.findById(id);
        }
    }

    @Override
    public List<SubjectConstraint> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            SubjectConstraintDao subjectConstraintDao = sqlSession.getMapper(SubjectConstraintDao.class);
            return subjectConstraintDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession(true)) {
            SubjectConstraintDao subjectConstraintDao = sqlSession.getMapper(SubjectConstraintDao.class);
            subjectConstraintDao.deleteById(id);
            sqlSession.commit();
        }
    }
}
