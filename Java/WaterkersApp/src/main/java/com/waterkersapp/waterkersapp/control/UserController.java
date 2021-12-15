package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserController {

    public static boolean CheckPassword(Gebruiker user, String givenPass)
    {
        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry = String.format("SELECT * FROM `Gebruiker` where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", user.getLoginNaam(), givenPass);
            ResultSet result = stat.executeQuery(Querry);
            int i = 0;
            while (result.next()) {
                i++;
            }
            return i == 1;
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


    public static ArrayList<Gebruiker> getAllUsers(){
        ArrayList<Gebruiker> alUsers = new ArrayList();

        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();

            String Querry ="SELECT * FROM `Gebruiker`;\n";
            ResultSet result = stat.executeQuery(Querry);

            while (result.next()) {
                Gebruiker user = new Gebruiker(result.getString("LoginNaam"), result.getBoolean("Admin"));
                alUsers.add(user);
            }
            return alUsers;
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


    public static Boolean CheckUsername(String Username){
        Connection con = null;
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            String Querry =
                    String.format("SELECT * FROM `Gebruiker` where `LoginNaam` = '%s'", Username);


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


    public static Pair<Boolean, String> CreateUser(Gebruiker user) {
        Connection con = null;
        String err = "";
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            String Querry = String.format("insert into `Gebruiker` (`LoginNaam`, `LoginPass`, `Admin`) values ('%s', sha2('%s', 256), %s);", user.getLoginNaam(), user.getLoginPass(), user.getAdmin());

            int result = stat.executeUpdate(Querry);
            if (result != 1){
                System.out.println(Querry);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
            }
            else{
                return new Pair<>(true, "Gebruiker met success gemaakt.");
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

    public static Pair<Boolean, String> ChangeUser(Gebruiker previousUser, Gebruiker newUser) {
        Connection con = null;
        String err = "";


        String Querry = "update `Gebruiker` " +
                String.format("set `LoginNaam` = '%s', `admin` = %s ", newUser.getLoginNaam(), newUser.getAdmin()) +
                String.format("where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", previousUser.getLoginNaam(), previousUser.getLoginPass());
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();


            int result = stat.executeUpdate(Querry);
            if (result == 1){
                return new Pair<>(true, "Gebruiker met success bewerkt.");
            }
            else {
                System.out.println(Querry);
                return new Pair<>(false, "Er ging its fout, probeer het later nog eens.");
            }

        } catch (SQLException se) {
            System.out.println(Querry);
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
