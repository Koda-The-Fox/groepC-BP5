/*
 * Author:  Jordy van Venrooij
 * Date:    12-10-2021
 * Edited:  N.A.
 * Project: WaterkersPreject
 * Based on:UML Klassen DIAGRAM v3.0 Rev.1
 */
package app.src.main.Model;

public class BeheerdArduino {
    String LoginNaam;
    int ArduinoID;

    // Constructor
    public BeheerdArduino(String loginNaam, int arduinoID) {
        LoginNaam = loginNaam;
        ArduinoID = arduinoID;
    }

    // Getters and setters
    public String getLoginNaam() {
        return LoginNaam;
    }

    public void setLoginNaam(String loginNaam) {
        LoginNaam = loginNaam;
    }

    public int getArduinoID() {
        return ArduinoID;
    }

    public void setArduinoID(int arduinoID) {
        ArduinoID = arduinoID;
    }

    // Functions


    // Overrides
    /* Geen override nodig, deze class zal nooit gebruikt worden voor de user zelf, dit is een combinatie tabel/class.
    @Override
    public String toString() {
        return "BeheerdArduino{" +
                "LoginNaam='" + LoginNaam + '\'' +
                ", ArduinoID=" + ArduinoID +
                '}';
    }

 */
}

