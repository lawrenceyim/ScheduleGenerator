package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.persistence.StudentGroupDao;
import com.solvd.schedulegenerator.service.StudentGroupService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class StudentGroupServiceImpl implements StudentGroupService {
    @Override
    public Optional<StudentGroup> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            return studentGroupDao.findById(id);
        }
    }

    @Override
    public List<StudentGroup> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            StudentGroupDao studentGroupDao = sqlSession.getMapper(StudentGroupDao.class);
            return studentGroupDao.findAll();
        }
    }
}
