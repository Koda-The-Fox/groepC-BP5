package com.greengenie.green_genie.control;

import com.greengenie.green_genie.model.ArduinoLocatie;
import com.greengenie.green_genie.model.sensorRegistratie;
import com.greengenie.green_genie.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistratieController {

    public static sensorRegistratie GetRegFromDevice(ArduinoLocatie device){
        sensorRegistratie alRegistraties = new sensorRegistratie();

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry = String.format("SELECT * FROM `Registratie` where `ArduinoID` = %s ORDER BY `DatumTijd` DESC LIMIT 1;", device.getArduinoID());

            ResultSet result = stat.executeQuery(Querry);
            while (result.next()) {
                try {
                    alRegistraties = new sensorRegistratie(
                            device,
                            result.getString("DatumTijd"),
                            result.getDouble("PHwaarde"),
                            result.getDouble("GrondTemp"),
                            result.getDouble("LuchtTemp"),
                            result.getDouble("GrondVocht"),
                            result.getDouble("LuchtVocht")
                    );
                } catch (SQLException se) {
                    se.printStackTrace();
                    return null;
                }
            }
            return alRegistraties;
        } catch (SQLException se) {
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
