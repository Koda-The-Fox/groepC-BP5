package com.waterkersapp.waterkersapp.util;

import com.waterkersapp.waterkersapp.MainWindow;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private final static BasicDataSource ds = new BasicDataSource();

    static {
        //ds.setUrl("jdbc:mysql://localhost:3306/groepc?useSSL=false&serverTimezone=UTC");
//        ds.setUsername("JFXAPP");
//        ds.setPassword("35if1euOezkHGQAPtAxy");
        ds.setUrl(MainWindow.SQLconnection.getFullURL());
        ds.setUsername(MainWindow.SQLconnection.getUSERNAME());
        ds.setPassword(MainWindow.SQLconnection.getPASSWORD());
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    private DBCPDataSource() {

    }

    /**
     This method creates an SQL connection
     @return returns static SQL connection
     @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
