package com.example.waterkersandroid.model.SampleSQLite;

public class Gebruiker {
    public Gebruiker gebruiker;
    public String LoginNaam, LoginPass;

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getLoginNaam() {
        return LoginNaam;
    }

    public void setLoginNaam(String loginNaam) {
        LoginNaam = loginNaam;
    }

    public String getLoginPass() {
        return LoginPass;
    }

    public void setLoginPass(String loginPass) {
        LoginPass = loginPass;
    }
}
