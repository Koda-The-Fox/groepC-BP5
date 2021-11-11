package com.waterkersapp.waterkersapp.model;

public class Gebruiker {
    String LoginNaam;
    String LoginPass;

    public Gebruiker() {
    }
    public Gebruiker(String loginNaam, String loginPass) {
        LoginNaam = loginNaam;
        LoginPass = loginPass;
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
