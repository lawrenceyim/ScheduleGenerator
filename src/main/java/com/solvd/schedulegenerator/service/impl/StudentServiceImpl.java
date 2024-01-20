package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.persistence.StudentDao;
import com.solvd.schedulegenerator.service.BaseService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements BaseService<Student> {
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
}
