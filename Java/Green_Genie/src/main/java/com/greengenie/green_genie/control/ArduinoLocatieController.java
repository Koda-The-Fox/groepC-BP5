package com.greengenie.green_genie.control;

import com.greengenie.green_genie.model.ArduinoLocatie;
import com.greengenie.green_genie.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArduinoLocatieController {


    public static ArrayList<ArduinoLocatie> getAllArduinoLocaties() {
        ArrayList<ArduinoLocatie> alLocaties = new ArrayList();

        Connection con = null;
        String Query = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            Query = "SELECT * FROM `arduinolocatie`;";

            ResultSet result = stat.executeQuery(Query);

            while (result.next()) {
                ArduinoLocatie Locatie = new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("Locatie"), result.getString("Status"));
                alLocaties.add(Locatie);
            }
            return alLocaties;
        } catch (SQLException se) {
            System.out.println(Query);
            se.printStackTrace();
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception se) { // No 'SQLException' the 'Exception' catches this too.
                se.printStackTrace();
            }
        }
    }
}
