package com.greengenie.green_genie.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class ArduinoLocatie {

    private Integer ArduinoID;
    private String Locatie;
    private String Status;

    /**
     * Creates an empty ArduinoLocatie Object
     */
    public ArduinoLocatie() {}

    public ArduinoLocatie(String locatie) {
        // ID is autoincrement
        this.Locatie = locatie;
        this.Status = "Uit"; // Default  = "Uit"
    }
    public ArduinoLocatie(Integer arduinoID, String locatie) {
        this.ArduinoID = arduinoID;
        this.Locatie = locatie;
        this.Status = "Uit"; // Default  = "Uit"
    }
    public ArduinoLocatie(Integer arduinoID, String locatie, String status) {
        this.ArduinoID = arduinoID;
        this.Locatie = locatie;
        this.Status = status;
    }

    public Integer getArduinoID() {
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
        return Locatie;
    }


    public boolean equals(ArduinoLocatie otherAL){
        return Objects.equals(this.getArduinoID(), otherAL.getArduinoID()) &&
                Objects.equals(this.getLocatie(), otherAL.getLocatie());

    }
}
