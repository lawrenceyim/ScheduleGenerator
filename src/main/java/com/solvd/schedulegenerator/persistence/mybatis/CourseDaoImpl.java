package com.solvd.schedulegenerator.persistence.mybatis;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.persistence.CourseDao;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    @Override
    public Optional<Course> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
            return courseDao.findById(id);
        }
    }

    @Override
    public List<Course> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
            return courseDao.findAll();
        }
    }

    @Override
    public void create(Course course) {
    }
}
