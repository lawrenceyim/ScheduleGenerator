package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.persistence.SubjectDao;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class SubjectServiceImpl implements SubjectService {
    @Override
    public Optional<Subject> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            return subjectDao.findById(id);
        }
    }

    @Override
    public List<Subject> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            SubjectDao subjectDao = sqlSession.getMapper(SubjectDao.class);
            return subjectDao.findAll();
        }
    }
}
