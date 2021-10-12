/*
 * Author:  Jordy van Venrooij
 * Date:    12-10-2021
 * Edited:  N.A.
 * Project: WaterkersPreject
 * Based on:UML Klassen DIAGRAM v3.0 Rev.1
 */


/* De Gebruiker
 * * Dit is de Class voor de gebruiker, hier zullende Gebruikernaam en mogelijk de password tijdelijk opgeslagen worden.
 * * Het wachtwoord kunnen we beter vergelijken met de bekende wachtwoorden in de database en nooit opvragen/opslaan.
 * * Toch heb ik nu de Password hier in staan maar die ga ik wss we galen wanneer we de login klaar heben.
 */
package src.sample.Model;



public class Gebruiker {
    private String LoginNaam, LoginPass;

    // Constructor
    public Gebruiker(String loginNaam, String loginPass) {
        LoginNaam = loginNaam;
        LoginPass = loginPass;
    }

    // Getters and setters
    public String getLoginNaam() {
        return LoginNaam;
    }

    public void setLoginNaam(String loginNaam) {
        LoginNaam = loginNaam;
    }

    private String getLoginPass() {
        return LoginPass;
    }

    public void setLoginPass(String loginPass) {
        LoginPass = loginPass;
    }

    // Functions

    /**
     * Validates the combination of the username and password exists;
     * @param Username The Username given by the user;
     * @param Pass The password given by the user;
     * @return Returns true if combination exists, false in any other situation;
     */
    boolean ValidateLogin(String Username, String Pass){
        /*
        * Maak een code die een select doet naar de database en kijk of de combinatie van de waardes username en password bestaat
        * LET OP de password is versleuteld met een SHA, dus versleutel de gegeven password met 'SHA2('Password', 256)'
        * Voorbeeld:
        * SELECT * FROM `Gebruiker` where `LoginNaam` like 'Username' and `LoginPass` like SHA2('Pass', 256);
        *
        * Als de combinatie bestaat, dan krijgen we een result. Als de combinatie niet bestaat dan krijgen we niets terug.
        * Hier kunnen wij een boolean op baseren, als het goed is zit dit al in de request van SQL.
        */
        return true;
    }

    // Overrides
    /**
     * If the toString() is called we want the username to be returned, the password should always be protected and never shown.
     * @return username
     */
    @Override
    public String toString() {
        return LoginNaam;
    }
}
