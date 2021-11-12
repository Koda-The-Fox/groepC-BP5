package com.waterkersapp.waterkersapp.model;


import java.sql.Date;

public class Registratie {
    int ArduinoID;
    Date DatumTijd;
    double PHwaarde;
    double GrondTemp;
    double LuchtTemp;
    double GrondVocht;
    double LuchtVocht;

    public Registratie() {
    }

    public Registratie(int arduinoID, Date datumTijd, double PHwaarde, double grondTemp, double luchtTemp, double grondVocht, double luchtVocht) {
        ArduinoID = arduinoID;
        DatumTijd = datumTijd;
        this.PHwaarde = PHwaarde;
        GrondTemp = grondTemp;
        LuchtTemp = luchtTemp;
        GrondVocht = grondVocht;
        LuchtVocht = luchtVocht;
    }


}

