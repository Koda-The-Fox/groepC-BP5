package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    /**
     * Validates the login by comparing the given information with the information on the database.
     * @param givenUserName The username is it is written in the database.
     * @param givenPassword The password is it is created, the one on the database should be encrypted.
     * @return True if the validation succeeded, False if it failed
     */
    public static boolean validateLogin(String givenUserName, String givenPassword) {
        List<Gebruiker> gebruikerList = getUsers(givenUserName, givenPassword);

        if (gebruikerList.size() == 1) {
            // Return true because the username is correct
            return gebruikerList.get(0).getLoginNaam().equals(givenUserName); // possibly redundant second validation, but it returns a boolean so why not ¯\_(ツ)_/¯
        } else {
            return false;
        }
    }

    /**
     * Gets the requested user
     * @param givenUserName The username is it is written in the database.
     * @param givenPassword The password is it is created, the one on the database should be encrypted.
     * @return returns the Gebruiker object with the username, null if nothing is found or more than one user was returned;
     */
    public static Gebruiker getUser(String givenUserName, String givenPassword){
        List<Gebruiker> gebruikerList = getUsers(givenUserName, givenPassword);

        if (gebruikerList == null){
            return null;
        }
        else if (gebruikerList.size() == 1) {
            return gebruikerList.get(0); // retrun a singular user
        } else {
            return null;
        }
    }

    /**
     * The function that actually gets the user from the database
     * @param givenUserName The username is it is written in the database.
     * @param givenPassword The password is it is created, the one on the database should be encrypted.
     * @return returns the list that was received, empty list if nothing was found, null if failed
     */
    private static List<Gebruiker> getUsers(String givenUserName, String givenPassword){
        String query = String.format("SELECT `LoginNaam`, `Admin` FROM `Gebruiker` where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", givenUserName, givenPassword);

        List<Gebruiker> gebruikerList = new ArrayList<>();

        try{
            Connection con = DBCPDataSource.getConnection();
            // start the connection
            PreparedStatement ps = con.prepareStatement(query);
            // Execute Query
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Because there should only be one User with the given username and password we don't really need to loop through the resultSet, we still do in case multiple users are returned.
                //Integer userID = getInteger(resultSet, "userID"); // Irrelevant, We don't need this inside the application;
                gebruikerList.add(new Gebruiker(resultSet.getString("LoginNaam"), resultSet.getBoolean("Admin")));
            }
        } catch (SQLSyntaxErrorException | SQLDataException datEX) {
            // Make something that tells the use there is no user with that username
            // the query either didn't find the Database/Table/Value or the query is wrong
            datEX.printStackTrace();
            return null;
        } catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
            return null;
        }
        return gebruikerList;
    }
}
