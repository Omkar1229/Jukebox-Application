package org.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfigTest {

    private static Connection conn;

    public static Connection getTestConnection() throws SQLException
    {
        if(conn==null || conn.isClosed()) {
            conn = DriverManager.getConnection("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;NON_KEYWORDS=user;MODE=MYSQL;init=runscript from 'classpath:script.sql'", "sa", "");
        }
        return conn;
    }
}
