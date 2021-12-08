package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.model.sensorRegistratie;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegistratieController {

    public static ArrayList<sensorRegistratie> GetRegFromDevice(ArduinoLocatie device){
        ArrayList<sensorRegistratie> alRegistraties = new ArrayList();

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry = String.format("SELECT * FROM `Registratie` where `ArduinoID` = %s;", device.getArduinoID());


            String Querry2 = String.format("SELECT * FROM `ArduinoLocatie` where `ArduinoID` = %s;", device.getArduinoID());

            ResultSet result2 = stat.executeQuery(Querry2);
            ArduinoLocatie alnew = null;
            while (result2.next()) {
                alnew = new ArduinoLocatie(result2.getInt("ArduinoID"), result2.getString("Locatie"), result2.getString("Status"));
            }

            ResultSet result = stat.executeQuery(Querry);
            while (result.next()) {
                try {
                    sensorRegistratie registratie = new sensorRegistratie(
                            alnew,
                            result.getString("DatumTijd"),
                            result.getDouble("PHwaarde"),
                            result.getDouble("GrondTemp"),
                            result.getDouble("LuchtTemp"),
                            result.getDouble("GrondVocht"),
                            result.getDouble("LuchtVocht")
                    );

                    alRegistraties.add(registratie);
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
