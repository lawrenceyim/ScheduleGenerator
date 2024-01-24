package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Schedule;
import org.apache.ibatis.annotations.Param;

public interface ScheduleDao extends BaseDao<Schedule> {
    void create(@Param("schedule") Schedule schedule, @Param("courseId") long courseId);
    void updateById(@Param("schedule") Schedule schedule, @Param("courseId") long courseId, @Param("scheduleId") long scheduleId);
}
