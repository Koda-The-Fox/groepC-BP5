package com.waterkersapp.waterkersapp.model;

public class ArduinoLocatie {

    int ArduinoID;
    String Locatie;
    String Status;

    public ArduinoLocatie(int arduinoID, String locatie, String status) {
        ArduinoID = arduinoID;
        Locatie = locatie;
        Status = status;
    }

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
}
