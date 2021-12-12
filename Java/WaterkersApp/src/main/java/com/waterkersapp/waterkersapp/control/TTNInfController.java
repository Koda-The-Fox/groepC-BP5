package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.TTN_Info;
import com.waterkersapp.waterkersapp.model.sensorRegistratie;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TTNInfController {



    public static Object GetTTNInfo(ArduinoLocatie device){

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry = String.format("SELECT * FROM `TTN_Info` where `ArduinoID` = %s;", device.getArduinoID());

            ResultSet result = stat.executeQuery(Querry);
            TTN_Info alnew = null;
            while (result.next()) {
                alnew = new TTN_Info(
                        device,
                        result.getString("TTN_DeviceID"),
                        result.getString("TTN_ApplicationID"),
                        result.getString("TTN_ConnectionURL"),
                        result.getString("TTN_Username"),
                        result.getString("TTN_APIPassword")
                        );
            }
            return alnew;
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

    public static Pair<Boolean, String> ChangeTTNI(TTN_Info currentTTNI, TTN_Info newTTNI) {Connection con = null;
        String err = "";
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            String Querry = "update `TTN_Info` " +
                    String.format("set `ArduinoID` = %s, `TTN_DeviceID` = '%s', `TTN_ApplicationID` = '%s', `TTN_ConnectionURL` = '%s', `TTN_Username` = '%s', `TTN_APIPassword` = sha2('%s', 256) ", newTTNI.getArduino().getArduinoID(), newTTNI.getTTN_DeviceID(), newTTNI.getTTN_ApplicationID(), newTTNI.getTTN_ConnectionURL(), newTTNI.getTTN_Username(), newTTNI.getTTN_APIPassword()) +
                    String.format("where `ArduinoID` = %s and `TTN_DeviceID` = '%s' and `TTN_ApplicationID` = '%s' and `TTN_ConnectionURL` = '%s' and `TTN_Username` = '%s' and `TTN_APIPassword` = sha2('%s', 256);", currentTTNI.getArduino().getArduinoID(), currentTTNI.getTTN_DeviceID(), currentTTNI.getTTN_ApplicationID(), currentTTNI.getTTN_ConnectionURL(), currentTTNI.getTTN_Username(), currentTTNI.getTTN_APIPassword());


            int result = stat.executeUpdate(Querry);
            if (result == 1){
                return new Pair<>(true, "Aparaat met success bewerkt.");
            }
            else {
                System.out.println(Querry);
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

    public static Pair<Boolean, String> CreateTTNI(TTN_Info newTTNI) {
        Connection con = null;
        String err = "";
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            String Query = String.format("insert into `TTN_Info` (`ArduinoID`, `TTN_DeviceID`, `TTN_ApplicationID`, `TTN_ConnectionURL`, `TTN_Username`, `TTN_APIPassword`) values (%s, '%s', '%s', '%s', '%s', sha2('%s', 256));", newTTNI.getArduino().getArduinoID(), newTTNI.getTTN_DeviceID(), newTTNI.getTTN_ApplicationID(), newTTNI.getTTN_ConnectionURL(), newTTNI.getTTN_Username(), newTTNI.getTTN_APIPassword());

            int result = stat.executeUpdate(Query);
            if (result != 1){
                System.out.println(Query);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
            }
            else{
                return new Pair<>(true, "Aparaat met success gemaakt.");
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
