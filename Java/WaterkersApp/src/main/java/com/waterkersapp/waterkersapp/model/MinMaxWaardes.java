package com.waterkersapp.waterkersapp.model;

public class MinMaxWaardes {
    private String Locatie;
    private double MinPH;
    private double MaxPH;
    private double MinGT;
    private double MaxGT;
    private double MinLT;
    private double MaxLT;
    private double MinGV;
    private double MaxGV;
    private double MinLV;
    private double MaxLV;

    public MinMaxWaardes() {
    }

    public MinMaxWaardes(String locatie, double minPH, double maxPH, double minGT, double maxGT, double minLT, double maxLT, double minGV, double maxGV, double minLV, double maxLV) {
        Locatie = locatie;
        MinPH = minPH;
        MaxPH = maxPH;
        MinGT = minGT;
        MaxGT = maxGT;
        MinLT = minLT;
        MaxLT = maxLT;
        MinGV = minGV;
        MaxGV = maxGV;
        MinLV = minLV;
        MaxLV = maxLV;
    }

    public String getLocatie() {
        return Locatie;
    }

    public void setLocatie(String locatie) {
        Locatie = locatie;
    }

    public double getMinPH() {
        return MinPH;
    }

    public void setMinPH(double minPH) {
        MinPH = minPH;
    }

    public double getMaxPH() {
        return MaxPH;
    }

    public void setMaxPH(double maxPH) {
        MaxPH = maxPH;
    }

    public double getMinGT() {
        return MinGT;
    }

    public void setMinGT(double minGT) {
        MinGT = minGT;
    }

    public double getMaxGT() {
        return MaxGT;
    }

    public void setMaxGT(double maxGT) {
        MaxGT = maxGT;
    }

    public double getMinLT() {
        return MinLT;
    }

    public void setMinLT(double minLT) {
        MinLT = minLT;
    }

    public double getMaxLT() {
        return MaxLT;
    }

    public void setMaxLT(double maxLT) {
        MaxLT = maxLT;
    }

    public double getMinGV() {
        return MinGV;
    }

    public void setMinGV(double minGV) {
        MinGV = minGV;
    }

    public double getMaxGV() {
        return MaxGV;
    }

    public void setMaxGV(double maxGV) {
        MaxGV = maxGV;
    }

    public double getMinLV() {
        return MinLV;
    }

    public void setMinLV(double minLV) {
        MinLV = minLV;
    }

    public double getMaxLV() {
        return MaxLV;
    }

    public void setMaxLV(double maxLV) {
        MaxLV = maxLV;
    }


}
