package com.waterkersapp.waterkersapp.model;

import javafx.collections.ObservableList;
import javafx.util.Pair;

public class BeheerdArduino {
    String LoginNaam;
    Integer ArduinoID;

    public BeheerdArduino(String loginNaam, Integer arduinoID) {
        LoginNaam = loginNaam;
        ArduinoID = arduinoID;
    }

    public String getLoginNaam() {
        return LoginNaam;
    }

    public void setLoginNaam(String loginNaam) {
        LoginNaam = loginNaam;
    }

    public Integer getArduinoID() {
        return ArduinoID;
    }

    public void setArduinoID(int arduinoID) {
        ArduinoID = arduinoID;
    }
}
