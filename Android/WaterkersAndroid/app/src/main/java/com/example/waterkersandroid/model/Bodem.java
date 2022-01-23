package com.example.waterkersandroid.model;

public class Bodem {

    private String bodemvochtigheid, bodemtemperatuur, pHwaarde;

    public Bodem(String bodemtemperatuur, String bodemvochtigheid, String pHwaarde){
        this.bodemtemperatuur = bodemtemperatuur;
        this.bodemvochtigheid = bodemvochtigheid;
        this.pHwaarde = pHwaarde;
    }

    public String getBodemvochtigheid() {
        return bodemvochtigheid;
    }

    public void setBodemvochtigheid(String bodemvochtigheid) {
        this.bodemvochtigheid = bodemvochtigheid;
    }

    public String getBodemtemperatuur() {
        return bodemtemperatuur;
    }

    public void setBodemtemperatuur(String bodemtemperatuur) {
        this.bodemtemperatuur = bodemtemperatuur;
    }

    public String getpHwaarde() {
        return pHwaarde;
    }

    public void setpHwaarde(String pHwaarde) {
        this.pHwaarde = pHwaarde;
    }
}
