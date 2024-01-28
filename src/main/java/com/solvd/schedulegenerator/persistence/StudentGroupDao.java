package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.StudentGroup;
import org.apache.ibatis.annotations.Param;

public interface StudentGroupDao extends BaseDao<StudentGroup> {
    void create(StudentGroup group);

    void updateById(@Param("group") StudentGroup group, @Param("id") long id);
}
