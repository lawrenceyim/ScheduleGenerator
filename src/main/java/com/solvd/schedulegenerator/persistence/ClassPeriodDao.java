package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ClassPeriodDao extends BaseDao<ClassPeriod> {
    void create(@Param("teacherId") long teacherId, @Param("roomId") long roomId, @Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("timeslot") int timeslot);
    void updateById(@Param("teacherId") long teacherId, @Param("roomId") long roomId, @Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("timeslot") int timeslot, @Param("classPeriodId") long classPeriodId);
    Optional<ClassPeriod> findById(long id);
    List<ClassPeriod> findAll();
    void deleteById(long id);
}
