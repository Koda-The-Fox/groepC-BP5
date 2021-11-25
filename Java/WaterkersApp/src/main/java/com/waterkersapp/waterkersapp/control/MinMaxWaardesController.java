package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MinMaxWaardesController {


    public MinMaxWaardes getSpecificMinMaxWaardes(ArduinoLocatie al){
        MinMaxWaardes alWaardes = new MinMaxWaardes();

        Connection con = null; // @TODO SQL Command, possibly changes once the API works
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            ResultSet result = stat.executeQuery("select * from MinMaxWaardes where locatie = '" + al.getLocatie() + "';");
            while (result.next()) {
                MinMaxWaardes Waardes = new MinMaxWaardes(result.getString("Locatie"), result.getDouble("MinPH"), result.getDouble("MaxPH"), result.getDouble("MinGT"), result.getDouble("MaxGT"), result.getDouble("MinLT"), result.getDouble("MaxLT"), result.getDouble("MinGV"), result.getDouble("MaxGV"), result.getDouble("MinLV"), result.getDouble("MaxLV"));
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
