package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.persistence.ClassPeriodDao;
import com.solvd.schedulegenerator.service.ClassPeriodService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class ClassPeriodServiceImpl implements ClassPeriodService {
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
}
