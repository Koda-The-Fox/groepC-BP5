package com.waterkersapp.waterkersapp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class ArduinoLocatie {

    final private ObjectProperty<Integer> ArduinoID = new SimpleObjectProperty<>();
    final private ObjectProperty<String> Locatie = new SimpleObjectProperty<>();
    final private ObjectProperty<String> Status = new SimpleObjectProperty<>("Uit");// Default: Uit, This is for possible future use.

    /**
     * Creates an empty ArduinoLocatie Object
     */
    public ArduinoLocatie() {}

    public ArduinoLocatie(String locatie) {
        // ID is autoincrement
        this.Locatie.set(locatie);
    }
    public ArduinoLocatie(Integer arduinoID, String locatie) {
        this.ArduinoID.set(arduinoID);
        this.Locatie.set(locatie);
    }
    public ArduinoLocatie(Integer arduinoID, String locatie, String status) {
        this.ArduinoID.set(arduinoID);
        this.Locatie.set(locatie);
        this.Status.set(status);
    }

    public Integer getArduinoID() {
        return ArduinoID.get();
    }

    public ObjectProperty<Integer> arduinoIDProperty() {
        return ArduinoID;
    }

    public void setArduinoID(Integer arduinoID) {
        this.ArduinoID.set(arduinoID);
    }

    public String getLocatie() {
        return Locatie.get();
    }

    public ObjectProperty<String> locatieProperty() {
        return Locatie;
    }

    public void setLocatie(String locatie) {
        this.Locatie.set(locatie);
    }

    public String getStatus() {
        return Status.get();
    }

    public ObjectProperty<String> statusProperty() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status.set(status);
    }

    @Override
    public String toString() {
        if (this.Status == null){
            return Locatie.get();
        }
        else {
            return ArduinoID.get() + " " + Locatie.get();
//            return ArduinoID.get() + " " + Locatie.get() + " (" + Status.get() + ")"; // 10-01-2022 - Removed status - Reason: We don't use status.
        }
    }


    public boolean equals(ArduinoLocatie otherAL){
        return Objects.equals(this.getArduinoID(), otherAL.getArduinoID()) &&
                Objects.equals(this.getLocatie(), otherAL.getLocatie());

    }
}
