package com.waterkersapp.waterkersapp.model;

import java.util.Objects;

public class TTN_Info {
    private ArduinoLocatie Arduino = new ArduinoLocatie("");
    private String TTN_DeviceID = "";
    private String TTN_ApplicationID = "";
    private String TTN_ConnectionURL = "";
    private String TTN_Username = "";
    private String TTN_APIPassword = "";

    public TTN_Info() {
    }

    public TTN_Info(ArduinoLocatie arduinoID) {
        Arduino = arduinoID;
    }

    public TTN_Info(ArduinoLocatie arduino, String TTN_DeviceID, String TTN_ApplicationID, String TTN_ConnectionURL, String TTN_Username, String TTN_APIPassword) {
        Arduino = arduino;
        this.TTN_DeviceID = TTN_DeviceID;
        this.TTN_ApplicationID = TTN_ApplicationID;
        this.TTN_ConnectionURL = TTN_ConnectionURL;
        this.TTN_Username = TTN_Username;
        this.TTN_APIPassword = TTN_APIPassword;
    }

    public ArduinoLocatie getArduino() {
        return Arduino;
    }

    public void setArduino(ArduinoLocatie arduino) {
        Arduino = arduino;
    }

    public String getTTN_DeviceID() {
        return TTN_DeviceID;
    }

    public void setTTN_DeviceID(String TTN_DeviceID) {
        this.TTN_DeviceID = TTN_DeviceID;
    }

    public String getTTN_ApplicationID() {
        return TTN_ApplicationID;
    }

    public void setTTN_ApplicationID(String TTN_ApplicationID) {
        this.TTN_ApplicationID = TTN_ApplicationID;
    }

    public String getTTN_ConnectionURL() {
        return TTN_ConnectionURL;
    }

    public void setTTN_ConnectionURL(String TTN_ConnectionURL) {
        this.TTN_ConnectionURL = TTN_ConnectionURL;
    }

    public String getTTN_Username() {
        return TTN_Username;
    }

    public void setTTN_Username(String TTN_Username) {
        this.TTN_Username = TTN_Username;
    }

    public String getTTN_APIPassword() {
        return TTN_APIPassword;
    }

    public void setTTN_APIPassword(String TTN_APIPassword) {
        this.TTN_APIPassword = TTN_APIPassword;
    }


    public boolean equals(TTN_Info otherTTNI){
        return Objects.equals(this.Arduino.getLocatie(), otherTTNI.Arduino.getLocatie()) &&
                Objects.equals(this.TTN_DeviceID, otherTTNI.TTN_DeviceID) &&
                Objects.equals(this.TTN_ApplicationID, otherTTNI.TTN_ApplicationID) &&
                Objects.equals(this.TTN_ConnectionURL, otherTTNI.TTN_ConnectionURL) &&
                Objects.equals(this.TTN_Username, otherTTNI.TTN_Username) &&
                Objects.equals(this.TTN_APIPassword, otherTTNI.TTN_APIPassword);
    }
}
