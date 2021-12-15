package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.util.Pair;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArduinoLocatieController {


    public static ArduinoLocatie getAllArduinoLocatie(String ArduinoLocatieName){

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry = String.format("SELECT * FROM `ArduinoLocatie` where `Locatie` = '%s';", ArduinoLocatieName);

            ResultSet result = stat.executeQuery(Querry);

            while (result.next()) {
                return new ArduinoLocatie(result.getInt("ArduinoID"), result.getString("Locatie"), result.getString("Status"));
            }
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
        return null;
    }

    public static ArrayList<ArduinoLocatie> getAllArduinoLocaties(Gebruiker user, Boolean override) {
        ArrayList<ArduinoLocatie> alLocaties = new ArrayList();

        Connection con = null;
        String Query = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            Query = "SELECT al.* FROM `arduinolocatie` as al \n";
            if (user != null) { // if the user is not null get all the device connected to the user
                if (user.getAdmin() && override) {
                    Query += ";";
                } else {
                    Query += "inner join beheerdarduino as ba on ba.ArduinoID = al.ArduinoID\n" +
                            "where ba.LoginNaam = '" + user.getLoginNaam() + "';";
                }
            }

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

    public static boolean CheckDevicename(String locatie) {
        Connection con = null;
        String Querry =
                String.format("SELECT * FROM `arduinolocatie` where `Locatie` = '%s';", locatie);

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            ResultSet result = stat.executeQuery(Querry);
            int i = 0;
            while (result.next()) {
                i++;
            }
            if (i != 0){
                return false;
            }
            else{
                return true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception se) { // No 'SQLException' the 'Exception' catches this too.
                se.printStackTrace();
            }
        }
    }

    public static Pair<Boolean, String> CreateDevice(ArduinoLocatie newDevice) {
        Connection con = null;
        String err = "";
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            String Querry = String.format("insert into `ArduinoLocatie` (`Locatie`, `Status`) values ('%s',  '%s');", newDevice.getLocatie(), newDevice.getStatus());

            int result = stat.executeUpdate(Querry);
            if (result != 1){
                System.out.println(Querry);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
            }
            else{
                return new Pair<>(true, "Device met success gemaakt.");
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
    public static Pair<Boolean, String> ChangeDevice(ArduinoLocatie al, ArduinoLocatie newDevice) {
        Connection con = null;
        String err = "";
        int result = 0;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Query = "update `ArduinoLocatie` " +
                    String.format("set `ArduinoID` = %s, `Locatie` = '%s', `Status` = '%s' ", al.getArduinoID(), newDevice.getLocatie(), newDevice.getStatus()) +
                    String.format("where `ArduinoID` = %s and `Locatie` = '%s' and `Status` = '%s';", al.getArduinoID(), al.getLocatie(), al.getStatus());

            result = stat.executeUpdate(Query);
            if (result == 1){
                return new Pair<>(true, "Device met success bewerkt.");
            }
            else {
                System.out.println(Query);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
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
