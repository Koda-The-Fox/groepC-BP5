package com.greengenie.green_genie.control;

import com.greengenie.green_genie.model.ArduinoLocatie;
import com.greengenie.green_genie.model.MinMaxWaardes;
import com.greengenie.green_genie.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MinMaxWaardesController {

    public static MinMaxWaardes getSpecificMinMaxWaardes(ArduinoLocatie al){
        MinMaxWaardes alWaardes = new MinMaxWaardes(al);

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            ResultSet result = stat.executeQuery("select al.ArduinoID, al.Locatie as 'alLocatie', al.Status, mmw.* from `MinMaxWaardes` as mmw join `ArduinoLocatie` as al on mmw.ArduinoID = al.ArduinoID where mmw.ArduinoID = " + al.getArduinoID() + ";");
            while (result.next()) {
                MinMaxWaardes Waardes = new MinMaxWaardes(
                        new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("alLocatie"), result.getString("Status")),
                        result.getDouble("MinPH"), result.getDouble("MaxPH"),
                        result.getDouble("MinGT"), result.getDouble("MaxGT"), result.getDouble("MinLT"), result.getDouble("MaxLT"),
                        result.getDouble("MinGV"), result.getDouble("MaxGV"), result.getDouble("MinLV"), result.getDouble("MaxLV"));
                Waardes.setFromDB(true);
                alWaardes = Waardes;
            }
            return alWaardes;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
