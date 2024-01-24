package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Teacher;
import com.solvd.schedulegenerator.persistence.TeacherDao;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl implements TeacherService {
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

    }
}
