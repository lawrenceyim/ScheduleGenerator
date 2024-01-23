package com.solvd.schedulegenerator.utils;

import com.solvd.schedulegenerator.domain.DayOfWeek;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DayOfWeekTypeHandler implements TypeHandler<DayOfWeek> {
    @Override
    public void setParameter(PreparedStatement ps, int i, DayOfWeek parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public DayOfWeek getResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.next()){
            int num = rs.getInt(columnName);
            return DayOfWeek.valueOf(num);
        }
        return null;
    }

    @Override
    public DayOfWeek getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public DayOfWeek getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
