package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Schedule;
import com.solvd.schedulegenerator.persistence.ScheduleDao;
import com.solvd.schedulegenerator.service.ScheduleService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class ScheduleServiceImpl implements ScheduleService {
    @Override
    public Optional<Schedule> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
            return scheduleDao.findById(id);
        }
    }

    @Override
    public List<Schedule> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()){
            ScheduleDao scheduleDao = sqlSession.getMapper(ScheduleDao.class);
            return scheduleDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {

    }
}
