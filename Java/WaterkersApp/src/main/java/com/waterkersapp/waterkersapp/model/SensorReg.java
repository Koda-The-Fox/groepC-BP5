package com.waterkersapp.waterkersapp.model;

import java.sql.Date;
import java.sql.Time;

public class SensorReg {

    // Registry variables
    private String Kas;
    private String Datum;
    private String Tijd;
    private double Waarde;


    private enum ValueType {
        PHwaarde,
        GrondTemp,
        LuchtTemp,
        GrondVocht,
        LuchtVocht
    }

    public SensorReg(ValueType VT) {

    }

    public SensorReg(ValueType VT, Date startDate, Date endDate) {

    }

    public SensorReg(String Kas, String Datum, String Tijd, double Waarde) {
        this.Kas = Kas;
        this.Datum = Datum;
        this.Tijd = Tijd;
        this.Waarde = Waarde;
    }

    // Functions
    public void getVariable(){

    }


    // Getters and setters
    public String getKas() {
        return Kas;
    }

    public void setKas(String kas) {
        Kas = kas;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getTijd() {
        return Tijd;
    }

    public void setTijd(String tijd) {
        Tijd = tijd;
    }

    public double getWaarde() {
        return Waarde;
    }

    public void setWaarde(double waarde) {
        Waarde = waarde;
    }

    @Override
    public String toString() {
        return "SensorReg{" +
                "Kas='" + Kas + '\'' +
                ", Datum=" + Datum +
                ", Tijd=" + Tijd +
                ", Waarde=" + Waarde +
                '}';
    }
}

