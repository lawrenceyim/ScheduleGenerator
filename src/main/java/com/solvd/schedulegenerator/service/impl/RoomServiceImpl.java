package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Room;
import com.solvd.schedulegenerator.persistence.RoomDao;
import com.solvd.schedulegenerator.service.RoomService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    @Override
    public Optional<Room> findById(long id) {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
            return roomDao.findById(id);
        }
    }

    @Override
    public List<Room> findAll() {
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
            return roomDao.findAll();
        }
    }

    @Override
    public void deleteById(long id) {

    }
}
