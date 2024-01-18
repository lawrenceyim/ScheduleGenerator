package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.utils.ConnectionPool;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class DatabaseStartup {
    public static void main(String[] args){

        try(Reader reader = new FileReader("src/main/resources/sql/GenerateDatabase.sql")) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection conn = connectionPool.getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
