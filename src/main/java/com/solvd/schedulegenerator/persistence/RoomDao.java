package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Room;
import org.apache.ibatis.annotations.Param;

public interface RoomDao extends BaseDao<Room>{
    void create(Room room);
    void updateById(@Param("room") Room room, @Param("id") long id);
}
