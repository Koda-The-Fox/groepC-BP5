package com.waterkersapp.waterkersapp.model;

public class ArduinoLocatie {

    Integer ArduinoID;
    String Locatie;
    String Status = null;

    /**
     * Creates an empty ArduinoLocatie Object
     */
    public ArduinoLocatie() {}


    public ArduinoLocatie(Integer arduinoID, String locatie) {
        ArduinoID = arduinoID;
        Locatie = locatie;
    }
    public ArduinoLocatie(Integer arduinoID, String locatie, String status) {
        ArduinoID = arduinoID;
        Locatie = locatie;
        Status = status;
    }

    public int getArduinoID() {
        return ArduinoID;
    }

    public void setArduinoID(Integer arduinoID) {
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

    @Override
    public String toString() {
        if (this.Status == null){
            return Locatie;
        }
        else {
            return Locatie + " (" + Status + ")";
        }
    }
}
