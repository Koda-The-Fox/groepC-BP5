package com.waterkersapp.waterkersapp.model;

public class BeheerdArduino {
    String LoginNaam;
    int ArduinoID;

    public BeheerdArduino(String loginNaam, int arduinoID) {
        LoginNaam = loginNaam;
        ArduinoID = arduinoID;
    }

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
}
