/*
 * Author:  Jordy van Venrooij
 * Date:    12-10-2021
 * Edited:  N.A.
 * Project: WaterkersPreject
 * Based on:UML Klassen DIAGRAM v3.0 Rev.1
 */

package src.sample.Model;

public class ArduinoLocatie {
    int ArduinoID;
    String Locatie, Status;

    // Constructor
    public ArduinoLocatie(int arduinoID, String locatie, String status) {
        ArduinoID = arduinoID;
        Locatie = locatie;
        Status = status;
    }

    // Getters and setters

    public int getArduinoID() {
        return ArduinoID;
    }

    public void setArduinoID(int arduinoID) {
        ArduinoID = arduinoID;
    }

    public String getLocatie() {
        return Locatie;
    }

    public void setLocatie(String locatie) {
        Locatie = locatie;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    // Functions


    // Overrides
    /**
     * If the toString() is called we want the ??? to be returned.
     * @return ???
     */
    @Override
    public String toString() {
        return "Locatie='" + Locatie + '\'' +
                ", Status='" + Status ;
    }
}
