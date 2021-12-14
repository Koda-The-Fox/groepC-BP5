package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

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

            ResultSet result = stat.executeQuery("select al.ArduinoID, al.Locatie as 'alLocatie', al.Status, mmw.* from `MinMaxWaardes` as mmw join `ArduinoLocatie` as al on mmw.Locatie = al.ArduinoID where mmw.Locatie = " + al.getArduinoID() + ";");
            while (result.next()) {

                    MinMaxWaardes Waardes = new MinMaxWaardes(new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("alLocatie"), result.getString("Status")), result.getDouble("MinPH"), result.getDouble("MaxPH"), result.getDouble("MinGT"), result.getDouble("MaxGT"), result.getDouble("MinLT"), result.getDouble("MaxLT"), result.getDouble("MinGV"), result.getDouble("MaxGV"), result.getDouble("MinLV"), result.getDouble("MaxLV"));
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
    private String valueStencil = "`Locatie` = %s, `MinPH` = %f, `MaxPH` = %f, `MinGT` = %f, `MaxGT` = %f, `MinLT` = %f, `MaxLT` = %f, `MinGV` = %f, `MaxGV` = %f, `MinLV` = %f, `MaxLV` = %f";
    public boolean updateMinMaxWaardes(MinMaxWaardes original, MinMaxWaardes MinMax){
        String query = "update `MinMaxWaardes` set " + String.format(valueStencil,
                MinMax.getLocatie().getArduinoID(),
                MinMax.getMinPH(), MinMax.getMaxPH(),
                MinMax.getMinGT(), MinMax.getMaxGT(), MinMax.getMinLT(), MinMax.getMaxLT(),
                MinMax.getMinGV(), MinMax.getMaxGV(), MinMax.getMinLV(), MinMax.getMaxLV()
                ) + " where " + String.format(valueStencil.replaceAll(",", " and"),
                original.getLocatie().getArduinoID(),
                original.getMinPH(), original.getMaxPH(),
                original.getMinGT(), original.getMaxGT(), original.getMinLT(), original.getMaxLT(),
                original.getMinGV(), original.getMaxGV(), original.getMinLV(), original.getMaxLV()
        );

        Connection con = null;
        int result = 0;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            result = stat.executeUpdate(query);
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return (result == 1);
    }

}
