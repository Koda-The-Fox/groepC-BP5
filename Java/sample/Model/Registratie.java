/*
 * Author:  Jordy van Venrooij
 * Date:    12-10-2021
 * Edited:  N.A.
 * Project: WaterkersPreject
 * Based on:UML Klassen DIAGRAM v3.0 Rev.1
 */

package src.sample.Model;

import java.sql.Date;

public class Registratie {
    int ArduinoID;
    Date DatumTijd; // SQL Date, not Java Date;
    double pHwaarde, GrondTemp, GrondVocht, LuchtTemp, LuchtVocht;

    // Constructor
    public Registratie(int arduinoID, Date datumTijd, double pHwaarde, double grondTemp, double grondVocht, double luchtTemp, double luchtVocht) {
        ArduinoID = arduinoID;
        DatumTijd = datumTijd;
        this.pHwaarde = pHwaarde;
        GrondTemp = grondTemp;
        GrondVocht = grondVocht;
        LuchtTemp = luchtTemp;
        LuchtVocht = luchtVocht;
    }

    // Getters and Setters
    public int getArduinoID() {
        return ArduinoID;
    }

    public void setArduinoID(int arduinoID) {
        ArduinoID = arduinoID;
    }

    public Date getDatumTijd() {
        return DatumTijd;
    }

    public void setDatumTijd(Date datumTijd) {
        DatumTijd = datumTijd;
    }

    public double getpHwaarde() {
        return pHwaarde;
    }

    public void setpHwaarde(double pHwaarde) {
        this.pHwaarde = pHwaarde;
    }

    public double getGrondTemp() {
        return GrondTemp;
    }

    public void setGrondTemp(double grondTemp) {
        GrondTemp = grondTemp;
    }

    public double getGrondVocht() {
        return GrondVocht;
    }

    public void setGrondVocht(double grondVocht) {
        GrondVocht = grondVocht;
    }

    public double getLuchtTemp() {
        return LuchtTemp;
    }

    public void setLuchtTemp(double luchtTemp) {
        LuchtTemp = luchtTemp;
    }

    public double getLuchtVocht() {
        return LuchtVocht;
    }

    public void setLuchtVocht(double luchtVocht) {
        LuchtVocht = luchtVocht;
    }

    // Functions


    // Overrides
    /**
     * If the toString() is called we want the ??? to be returned.
     * @return ???
     */
    @Override
    public String toString() {
        return "Registratie{" +
                "ArduinoID=" + ArduinoID +
                ", DatumTijd=" + DatumTijd +
                ", pHwaarde=" + pHwaarde +
                ", GrondTemp=" + GrondTemp +
                ", GrondVocht=" + GrondVocht +
                ", LuchtTemp=" + LuchtTemp +
                ", LuchtVocht=" + LuchtVocht +
                '}';
    }
}
