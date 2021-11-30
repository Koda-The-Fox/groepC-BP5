package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChangePassController {

    public static boolean ChangePassword(Gebruiker user, String Password, String NewPassword){

        String query1 = String.format("SELECT LoginNaam FROM `Gebruiker` where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", user.getLoginNaam(), Password);


        int i = 0;
        // validate if only one user will be edited
        try (Connection con = DBCPDataSource.getConnection()) {
            // start the connection
            PreparedStatement ps = con.prepareStatement(query1);
            // Execute Query
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                i++;
            }
        } catch (SQLSyntaxErrorException | SQLDataException datEX) {
            // Make something that tells the use there is no user with that username
            // the query either didn't find the Database/Table/Value or the query is wrong
            datEX.printStackTrace();
            return false;
        } catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
            return false;
        }

        if (i != 1){ // if 0 or more than 1 users would have been edited cancel the operation.
            System.out.println(i +" users would have been edited, 1 was expected. Cancelling the operation.");
            return false;
        }


        // Attempt to change the password.
        String query = "update `Gebruiker` " +
                String.format("set `LoginPass` = sha2('%s', 256)", NewPassword) +
                String.format("where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", user.getLoginNaam(), Password);

        System.out.println(query);

        List<Gebruiker> gebruikerList = new ArrayList<>();

        try (Connection con = DBCPDataSource.getConnection()) {
            // start the connection
            PreparedStatement ps = con.prepareStatement(query);
            // Execute Query
            int resultSet = ps.executeUpdate();

            // If the operation succeeded resultSet should be 1
            return (resultSet == 1);

        } catch (SQLSyntaxErrorException | SQLDataException datEX) {
            // Make something that tells the use there is no user with that username
            // the query either didn't find the Database/Table/Value or the query is wrong
            datEX.printStackTrace();
            return false;
        } catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
            return false;
        }
    }

}
