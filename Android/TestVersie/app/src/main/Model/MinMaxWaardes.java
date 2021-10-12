/*
 * Author:  Jordy van Venrooij
 * Date:    12-10-2021
 * Edited:  N.A.
 * Project: WaterkersPreject
 * Based on:UML Klassen DIAGRAM v3.0 Rev.1
 */
package app.src.main.Model;

public class MinMaxWaardes {
    String Locatie;
    double MinPH, MaxPH, MinGRTemp, MaxGRTemp, MinGRVocht, MaxGRVocht, MinLuTemp, MaxLuTemp, MinLuVocht, MaxLuVocht;

    // Constructor
    public MinMaxWaardes(String locatie, double minPH, double maxPH, double minGRTemp, double maxGRTemp, double minGRVocht, double maxGRVocht, double minLuTemp, double maxLuTemp, double minLuVocht, double maxLuVocht) {
        Locatie = locatie;
        MinPH = minPH;
        MaxPH = maxPH;
        MinGRTemp = minGRTemp;
        MaxGRTemp = maxGRTemp;
        MinGRVocht = minGRVocht;
        MaxGRVocht = maxGRVocht;
        MinLuTemp = minLuTemp;
        MaxLuTemp = maxLuTemp;
        MinLuVocht = minLuVocht;
        MaxLuVocht = maxLuVocht;
    }

    // Getters and Setters

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

    public double getMinGRTemp() {
        return MinGRTemp;
    }

    public void setMinGRTemp(double minGRTemp) {
        MinGRTemp = minGRTemp;
    }

    public double getMaxGRTemp() {
        return MaxGRTemp;
    }

    public void setMaxGRTemp(double maxGRTemp) {
        MaxGRTemp = maxGRTemp;
    }

    public double getMinGRVocht() {
        return MinGRVocht;
    }

    public void setMinGRVocht(double minGRVocht) {
        MinGRVocht = minGRVocht;
    }

    public double getMaxGRVocht() {
        return MaxGRVocht;
    }

    public void setMaxGRVocht(double maxGRVocht) {
        MaxGRVocht = maxGRVocht;
    }

    public double getMinLuTemp() {
        return MinLuTemp;
    }

    public void setMinLuTemp(double minLuTemp) {
        MinLuTemp = minLuTemp;
    }

    public double getMaxLuTemp() {
        return MaxLuTemp;
    }

    public void setMaxLuTemp(double maxLuTemp) {
        MaxLuTemp = maxLuTemp;
    }

    public double getMinLuVocht() {
        return MinLuVocht;
    }

    public void setMinLuVocht(double minLuVocht) {
        MinLuVocht = minLuVocht;
    }

    public double getMaxLuVocht() {
        return MaxLuVocht;
    }

    public void setMaxLuVocht(double maxLuVocht) {
        MaxLuVocht = maxLuVocht;
    }

    // Functions


    // Overrides
    /**
     * If the toString() is called we want the ??? to be returned.
     * @return ???
     */
    @Override
    public String toString() {
        return "MinMaxWaardes{" +
                "Locatie='" + Locatie + '\'' +
                ", MinPH=" + MinPH +
                ", MaxPH=" + MaxPH +
                ", MinGRTemp=" + MinGRTemp +
                ", MaxGRTemp=" + MaxGRTemp +
                ", MinGRVocht=" + MinGRVocht +
                ", MaxGRVocht=" + MaxGRVocht +
                ", MinLuTemp=" + MinLuTemp +
                ", MaxLuTemp=" + MaxLuTemp +
                ", MinLuVocht=" + MinLuVocht +
                ", MaxLuVocht=" + MaxLuVocht +
                '}';
    }
}

