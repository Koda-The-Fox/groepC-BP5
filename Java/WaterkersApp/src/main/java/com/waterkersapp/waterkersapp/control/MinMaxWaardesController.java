package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.util.Pair;

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

    public static boolean updateMinMaxWaardes(MinMaxWaardes originalMM, MinMaxWaardes newMM){
        String valueStencil = "`ArduinoID` = %s, `MinPH` = %f, `MaxPH` = %f, `MinGT` = %f, `MaxGT` = %f, `MinLT` = %f, `MaxLT` = %f, `MinGV` = %f, `MaxGV` = %f, `MinLV` = %f, `MaxLV` = %f";
        String query = "update `MinMaxWaardes` set " + String.format(valueStencil,
                newMM.getLocatie().getArduinoID(),
                newMM.getMinPH(), newMM.getMaxPH(),
                newMM.getMinGT(), newMM.getMaxGT(), newMM.getMinLT(), newMM.getMaxLT(),
                newMM.getMinGV(), newMM.getMaxGV(), newMM.getMinLV(), newMM.getMaxLV()
                ) + " where " + String.format(valueStencil.replaceAll(",", " and"),
                originalMM.getLocatie().getArduinoID(),
                originalMM.getMinPH(), originalMM.getMaxPH(),
                originalMM.getMinGT(), originalMM.getMaxGT(), originalMM.getMinLT(), originalMM.getMaxLT(),
                originalMM.getMinGV(), originalMM.getMaxGV(), originalMM.getMinLV(), originalMM.getMaxLV()
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

    public static Pair<Boolean, String> CreateMMW(MinMaxWaardes MMW){
        String query = "insert into `MinMaxWaardes` " +
                "(`ArduinoID` , `MinPH`, `MaxPH`, `MinGT`,  `MaxGT`, `MinLT`, `MaxLT`,  `MinGV`, `MaxGV`, `MinLV`, `MaxLV`) values " +
                String.format("(%d, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f)", MMW.getLocatie().getArduinoID(), MMW.getMinPH(), MMW.getMaxPH(), MMW.getMinGT(), MMW.getMaxGT(), MMW.getMinLT(), MMW.getMaxLT(), MMW.getMinGV(), MMW.getMaxGV(), MMW.getMinLV(), MMW.getMaxLV());

        Connection con = null;
        String err = "";
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            int result = stat.executeUpdate(query);
            if (result != 1){
                System.out.println(query);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
            }
            else{
                return new Pair<>(true, "Min/Max Waardes met success gemaakt.");
            }

        } catch (SQLException se) {
            se.printStackTrace();
            err += se.getMessage() + "\n";
        } finally {
            try {
                con.close();
            } catch (Exception se) { // No 'SQLException' the 'Exception' catches this too.
                se.printStackTrace();
                err += se.getMessage() + "\n";
            }
        }
        return new Pair<>(false, err);

    }

}
