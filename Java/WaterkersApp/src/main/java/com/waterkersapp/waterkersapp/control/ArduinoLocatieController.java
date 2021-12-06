package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArduinoLocatieController {


    public static ArrayList<ArduinoLocatie> getAllArduinoLocaties(Gebruiker user){
        ArrayList<ArduinoLocatie> alLocaties = new ArrayList();

        Connection con = null; // @TODO SQL Command, possibly changes once the API works
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            // ResultSet result = stat.executeQuery("select * from ArduinoLocatie;"); // Old code, its outdated and doesnt do user specific requests

            String Querry =
                    "SELECT al.* FROM `arduinolocatie` as al \n";
            if (user != null){ // if the user is not null get all the device connected to the user
                Querry += "inner join beheerdarduino as ba on ba.ArduinoID = al.ArduinoID\n"+
                            "where ba.LoginNaam = '" + user.getLoginNaam() + "';";
            }

            ResultSet result = stat.executeQuery(Querry);

            while (result.next()) {
                ArduinoLocatie Locatie = new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("Locatie"), result.getString("Status"));
                alLocaties.add(Locatie);
            }
            return alLocaties;
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
