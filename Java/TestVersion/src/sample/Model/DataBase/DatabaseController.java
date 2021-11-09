/*
* Author: Jordy van Venrooij
* Date: 09-11-2021
*
*
* sources:
* https://gist.github.com/julianjupiter/407352f2c8623c9a1854a73488afd50e
*/

package sample.Model.DataBase;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseController {

    private static final String SQL_URL         = "localhost"; // Default: localhost, GCP: 35.204.91.71
    private static final String SQL_PORT        = "3306"; // Default: 3306
    private static final String SQL_SCHEMA      = "groepC"; // Default: N.A

    /*  MySQL Login's
     * --------[Root]--------
     * username: root3
     * password: root12345678
     * priv.   : All
     *
     * --------[application]--------
     * username: application
     * password: aXZObiqOUKW3FFmJvNsL
     * priv.   : Read
    */
    private static final String SQL_USERNAME    = "application";
    private static final String SQL_PASSWORD    = "aXZObiqOUKW3FFmJvNsL";

    private static final String SQL_TIMEZONE    = "UTC"; // Default: UTC
    private static final String SQL_USESSL      = "false"; // Default: false

    private static final String SQL_CONNECTION  = "jdbc:mysql://"+SQL_URL+":"+SQL_PORT+"/"+SQL_SCHEMA+"?useSSL="+SQL_USESSL+"&serverTimezone="+SQL_TIMEZONE;

    public DatabaseController() {

    }

    /**
     * Tries to get the BasicDataSource for the Data Base.
     * @return An BasicDataSource with the default connection data.
     */
    public static BasicDataSource createBasicDataSource() {

        // Create an empty Connection object
        BasicDataSource ds = new BasicDataSource();

        // Fill the rest of the connection data
        System.out.println(SQL_CONNECTION);
        ds.setUrl(SQL_CONNECTION);
        ds.setUsername(SQL_USERNAME);
        ds.setPassword(SQL_PASSWORD);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

        return ds;

    }

    /**
     * Creates and returns an Connection object using the given BasicDataSource.
     * @param bds BasicDataSource with the connection data.
     * @return an Java.sql.Connection object using the given BasicDataSource.
     * @throws SQLException
     */
    public static Connection getConnection(BasicDataSource bds) throws SQLException {
        // get the connection
        return bds.getConnection();
    }



}
