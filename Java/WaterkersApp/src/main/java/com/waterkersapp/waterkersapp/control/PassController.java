package com.waterkersapp.waterkersapp.control;

import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.DBCPDataSource;
import javafx.util.Pair;

import java.sql.*;

public class PassController {

    public static Pair<Boolean, String> ChangePassword(Gebruiker user, String Password, String NewPassword, Gebruiker editor){

        String query1 = String.format("SELECT LoginNaam FROM `Gebruiker` where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", user.getLoginNaam(), Password);

        // validate the editor if it's an admin and isn't editing itself
        if (editor.getAdmin() && !editor.equals(user)) {
            query1 = String.format("SELECT LoginNaam FROM `Gebruiker` where `LoginNaam` = '%s';", user.getLoginNaam());

            if (!LoginController.validateLogin(editor.getLoginNaam(), Password)){
                return new Pair<>(false, "Het admin wachtwoord is verkeerd.\n");
            }
        }



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
        } catch (SQLException datEX) {
            // the query either didn't find the Database/Table/Value or the query is wrong
            datEX.printStackTrace();
            System.out.println("query1: "+query1);
            return new Pair<>(false, "Er ging iets fout met de database,\nprobeer het later opnieuw.");
        }

        if (i != 1){ // if 0 or more than 1 users would have been edited cancel the operation.
            return new Pair<>(false, i +" gebruikers zouden bewerkt worden met deze handeling,\ndit moet er maar een zijn dus de handeling is afgebroken.");
        }


        // Attempt to change the password.
        String query = "update `Gebruiker` " +
                String.format("set `LoginPass` = sha2('%s', 256)", NewPassword) +
                String.format("where `LoginNaam` = '%s' and `LoginPass` = sha2('%s', 256);", user.getLoginNaam(), Password);

        if (editor != null)
            query = "update `Gebruiker` " +
                    String.format("set `LoginPass` = sha2('%s', 256)", NewPassword) +
                    String.format("where `LoginNaam` = '%s';", user.getLoginNaam());


//        System.out.println(query);

        try (Connection con = DBCPDataSource.getConnection()) {
            // start the connection
            PreparedStatement ps = con.prepareStatement(query);
            // Execute Query
            int resultSet = ps.executeUpdate();

            // If the operation succeeded resultSet should be 1
            if (resultSet == 1){
                return new Pair<>(true, "Gebruiker is bewerkt.\n");
            }
            else {
                return new Pair<>(false, "Er ging iets fout. "+resultSet+" gebruikers zijn bewerkt.\n");
            }

        } catch (SQLException datEX) {
            // the query either didn't find the Database/Table/Value or the query is wrong
            datEX.printStackTrace();
            System.out.println("query: "+query);
            return new Pair<>(false, "Er ging iets fout met de database,\nprobeer het later opnieuw.");
        }
    }

}
