package com.waterkersapp.waterkersapp.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class sensorRegistratie {
    final private ObjectProperty<ArduinoLocatie> ArduinoID = new SimpleObjectProperty<>();
    final private ObjectProperty<String> DatumTijd = new SimpleObjectProperty<>();
    final private ObjectProperty<Double> PHwaarde = new SimpleObjectProperty<>();
    final private ObjectProperty<Double> GrondTemp = new SimpleObjectProperty<>();
    final private ObjectProperty<Double> LuchtTemp = new SimpleObjectProperty<>();
    final private ObjectProperty<Double> GrondVocht = new SimpleObjectProperty<>();
    final private ObjectProperty<Double> LuchtVocht = new SimpleObjectProperty<>();

    public sensorRegistratie() {
    }

    public sensorRegistratie(sensorRegistratie reg) {
        this.ArduinoID.set(reg.getArduino());
        this.DatumTijd.set(reg.getDatumTijd());
        this.PHwaarde.set(reg.getPHwaarde());
        this.GrondTemp.set(reg.getGrondTemp());
        this.LuchtTemp.set(reg.getLuchtTemp());
        this.GrondVocht.set(reg.getGrondVocht());
        this.LuchtVocht.set(reg.getLuchtVocht());
    }

    public sensorRegistratie(ArduinoLocatie arduinoID, String datumTijd, double PHwaarde, double grondTemp, double luchtTemp, double grondVocht, double luchtVocht) {
        this.ArduinoID.set(arduinoID);
        this.DatumTijd.set(datumTijd);
        this.PHwaarde.set(PHwaarde);
        this.GrondTemp.set(grondTemp);
        this.LuchtTemp.set(luchtTemp);
        this.GrondVocht.set(grondVocht);
        this.LuchtVocht.set(luchtVocht);
    }

    public ArduinoLocatie getArduino() {
        return ArduinoID.get();
    }

    public ObjectProperty<ArduinoLocatie> arduinoProperty() {
        return ArduinoID;
    }

    public void setArduino(ArduinoLocatie arduino) {
        this.ArduinoID.set(arduino);
    }

    public String getDatumTijd() {
        return DatumTijd.get();
    }

    public ObjectProperty<String> datumTijdProperty() {
        return DatumTijd;
    }

    public void setDatumTijd(String datumTijd) {
        this.DatumTijd.set(datumTijd);
    }

    public Double getPHwaarde() {
        return PHwaarde.get();
    }

    public ObjectProperty<Double> PHwaardeProperty() {
        return PHwaarde;
    }

    public void setPHwaarde(Double PHwaarde) {
        this.PHwaarde.set(PHwaarde);
    }

    public Double getGrondTemp() {
        return GrondTemp.get();
    }

    public ObjectProperty<Double> grondTempProperty() {
        return GrondTemp;
    }

    public void setGrondTemp(Double grondTemp) {
        this.GrondTemp.set(grondTemp);
    }

    public Double getLuchtTemp() {
        return LuchtTemp.get();
    }

    public ObjectProperty<Double> luchtTempProperty() {
        return LuchtTemp;
    }

    public void setLuchtTemp(Double luchtTemp) {
        this.LuchtTemp.set(luchtTemp);
    }

    public Double getGrondVocht() {
        return GrondVocht.get();
    }

    public ObjectProperty<Double> grondVochtProperty() {
        return GrondVocht;
    }

    public void setGrondVocht(Double grondVocht) {
        this.GrondVocht.set(grondVocht);
    }

    public Double getLuchtVocht() {
        return LuchtVocht.get();
    }

    public ObjectProperty<Double> luchtVochtProperty() {
        return LuchtVocht;
    }

    public void setLuchtVocht(Double luchtVocht) {
        this.LuchtVocht.set(luchtVocht);
    }

    @Override
    public String toString() {
        return "Registratie{" +
                "ArduinoID=" + ArduinoID.get() +
                ", DatumTijd='" + DatumTijd.get() + '\'' +
                ", PHwaarde=" + PHwaarde.get() +
                ", GrondTemp=" + GrondTemp.get() +
                ", LuchtTemp=" + LuchtTemp.get() +
                ", GrondVocht=" + GrondVocht.get() +
                ", LuchtVocht=" + LuchtVocht.get() +
                '}';
    }
}

