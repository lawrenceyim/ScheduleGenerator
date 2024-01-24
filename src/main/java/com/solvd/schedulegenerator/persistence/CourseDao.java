package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Course;
import org.apache.ibatis.annotations.Param;

public interface CourseDao extends BaseDao<Course> {
    void create(@Param("teacherId") long teacherId, @Param("roomId") long roomId, @Param("groupId") long groupId, @Param("subjectId") long subjectId);

    void updateById(@Param("teacherId") long teacherId, @Param("roomId") long roomId, @Param("groupId") long groupId, @Param("subjectId") long subjectId, @Param("courseId") long courseId);
}
