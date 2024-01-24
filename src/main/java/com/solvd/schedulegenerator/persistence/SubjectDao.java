package com.solvd.schedulegenerator.persistence;

import com.solvd.schedulegenerator.domain.Subject;
import org.apache.ibatis.annotations.Param;

public interface SubjectDao extends BaseDao<Subject> {
    void create(Subject subject);

    void updateById(@Param("subject") Subject subject, @Param("id") long id);
}
