package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.BeheerdArduino;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BeheerdArduinoController {

    /**
     * Gets all BeheerdArduino Records.
     * Leave user and/or device null to ignore them, if both are null, the result will be all the records;
     * @param user The user whose records you want to receive, make null to ignore;
     * @param device The device which records you want to receive, make null to ignore;
     * @return An ArrayList < BeheerdArduino >
     */
    public static ArrayList<BeheerdArduino> getAllBeheerd( Gebruiker user, ArduinoLocatie device){

        String Querry =
                "SELECT * FROM `BeheerdArduino`";
        if (user != null || device != null) {
            Querry += " where ";
            if (user != null)
                Querry += String.format(" `LoginNaam` = '%s'", user.getLoginNaam());
            if (user != null && device != null)
                Querry += " and ";
            if (device != null)
                Querry += String.format(" `ArduinoID` = %x", device.getArduinoID());

        }
        Querry += ";"; // Close the query properly

        ArrayList<BeheerdArduino> alBeheerdRecords = new ArrayList<>();

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            ResultSet result = stat.executeQuery(Querry);

            while (result.next()) {
                BeheerdArduino BeheerdRecord = new BeheerdArduino(result.getString("LoginNaam"), result.getInt("ArduinoID"));
                alBeheerdRecords.add(BeheerdRecord);
            }
            return alBeheerdRecords;
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

    /**
     * Method that creates the new BeheerdArduino Records.
     * @param newUser The user the Record('s) is/are for.
     * @param devices The device('s) that the record(s) is/are for.
     * @return Pair < Boolean, String > ,<br> Boolean is the result of the query (true = success, False = failed)<br>String is the message, f failed, the reason why. If Succeeded, the success message.
     */
    public static Pair<Boolean, String> CreateBeheerdRecord(Gebruiker newUser, ObservableList<ArduinoLocatie> devices) {
        ArrayList<BeheerdArduino> DevRecords = new ArrayList<>(); // The list with all existing DeviceRecords
        ArrayList<BeheerdArduino> DevRecIgnList = new ArrayList<>(); // The list for the devicesRecords that need to be ignored
        ArrayList<ArduinoLocatie> DevIgnList = new ArrayList<>(); // The list for the devicesRecords that need to be ignored
        ArrayList<ArduinoLocatie> DevList = new ArrayList<>(devices); // The list for the devicesRecords that need to be ignored
        Pair<Boolean, String> result = new Pair<>(false, "No result yet\n");

        // Validate what records need to be removed and which need to stay
        // Request all records
        DevRecords.addAll(getAllBeheerd(newUser, null));


        // Filter the items to be ignored
        for (BeheerdArduino devRecord : DevRecords) {
            // Ignore existing records
            for (ArduinoLocatie device : DevList) {
                if (devRecord.getArduinoID().equals(device.getArduinoID())) {
                    DevRecIgnList.add(devRecord);
                    DevIgnList.add(device);
                }
            }
        }
        // Filter the items to be removed
        DevRecords.removeAll(DevRecIgnList); // Remove the ignored deviceRecords
        // The list for the devicesRecords that need to be removed
        ArrayList<BeheerdArduino> DevRecRemList = new ArrayList<>(DevRecords); // the remaining deviceRecords need to be removed

        // Filter the items to be added
        DevList.removeAll(DevIgnList); // Remove the ignored devices
        // The list for the devicesRecords that need to be added
        ArrayList<ArduinoLocatie> DevAddList = new ArrayList<>(DevList); // the remaining devices need to be added


        // Create the query
        // Insert
        Boolean CreateResultBool = false;
        StringBuilder queryInsrt = new StringBuilder("");
        for (ArduinoLocatie device : DevAddList) {
            if (queryInsrt.toString().equals("")){
                queryInsrt.append("Insert into `BeheerdArduino` (`LoginNaam`, `ArduinoID`) values ");
            }
            queryInsrt.append(String.format("('%s', %x)", newUser.getLoginNaam(), device.getArduinoID()));
            if (DevAddList.indexOf(device) != DevAddList.size() - 1) { // if it's the last element
                queryInsrt.append(",");
            }
            else{
                queryInsrt.append(";");
                CreateResultBool = true;
            }
        }
//        System.out.println(queryInsrt);

        // Remove
        Boolean DeleteResultBool = false;
        StringBuilder queryDelte = new StringBuilder("");
        for (BeheerdArduino record : DevRecRemList) {
            if (queryDelte.toString().equals("")){
                queryDelte.append("delete from `BeheerdArduino` where ");
            }
            queryDelte.append(String.format("(`LoginNaam` = '%s' and `ArduinoID` = %x)", newUser.getLoginNaam(), record.getArduinoID()));
            if (DevRecRemList.indexOf(record) != DevRecRemList.size() - 1) { // if it's the last element
                queryDelte.append("or");
            }else{
                queryDelte.append(";");
                DeleteResultBool = true;
            }
        }
//        System.out.println(queryDelte);

        // Execute the query
        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            int AddQueryResult = 0;
            int RemQueryResult = 0;

            String resultString = "";
            // Add
            if (CreateResultBool) {

                AddQueryResult = stat.executeUpdate(queryInsrt.toString());

                if (AddQueryResult > 0){
                    resultString += "Gebruiker successvol toegevoegd. ";
                    CreateResultBool = true;
                }
                else {
                    resultString += "Gebruiker toevoegen mislukt. ";
                    CreateResultBool = false;
                }
            }
            else
                CreateResultBool = true; //set to true, if it isn't used it can't fail

            // Remove
            if (DeleteResultBool) {

                RemQueryResult = stat.executeUpdate(queryDelte.toString());

                if (RemQueryResult > 0){
                    resultString += "Aparaat(en) registratie successvol. ";
                    DeleteResultBool = true;
                }
                else {
                    resultString += "Aparaat(en) registratie mislukt. ";
                    DeleteResultBool = false;
                }
            }
            else
                DeleteResultBool = true; //set to true, if it isn't used it can't fail

            result = new Pair<>((CreateResultBool && DeleteResultBool), resultString); // Return true if both succeeded, else return false;
            return result;

        } catch (SQLException se) {
            se.printStackTrace();
            result = new Pair<>(false, se.getMessage());
            return result;
        } finally {
            try {
                con.close();
            } catch (Exception se) { // No 'SQLException' the 'Exception' catches this too.
                se.printStackTrace();
                result = new Pair<>(false, se.getMessage());
                return result;
            }
        }
    }
}
