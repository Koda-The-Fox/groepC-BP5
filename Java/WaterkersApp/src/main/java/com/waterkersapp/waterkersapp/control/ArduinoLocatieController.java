package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArduinoLocatieController {


    public ArrayList<ArduinoLocatie> getAllArduinoLocaties(){
        ArrayList<ArduinoLocatie> alLocaties = new ArrayList();

        Connection con = null; // @TODO SQL Command, possibly changes once the API works
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            ResultSet result = stat.executeQuery("select * from ArduinoLocatie;");
            while (result.next()) {
                ArduinoLocatie Locatie = new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("Locatie"), result.getString("Status"));
                alLocaties.add(Locatie);
            }
            return alLocaties;
        } catch (SQLException se) {
            System.out.println("PersoonController[getAllMinMaxWaardes()]: " + se.getMessage());
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.getMessage();
            }
        }
    }
}
