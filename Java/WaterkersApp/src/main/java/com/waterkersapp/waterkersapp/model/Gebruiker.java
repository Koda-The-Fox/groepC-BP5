package com.waterkersapp.waterkersapp.model;

public class Gebruiker {
    String LoginNaam;
    String LoginPass;
    Boolean Admin;

    public Gebruiker() {
    }
    public Gebruiker(String loginNaam) {
        LoginNaam = loginNaam;
    }
    public Gebruiker(String loginNaam, Boolean admin) {
        LoginNaam = loginNaam;
        Admin = admin;
    }
    public Gebruiker(String loginNaam, String loginPass) {
        LoginNaam = loginNaam;
        LoginPass = loginPass;
    }
    public Gebruiker(String loginNaam, String loginPass, Boolean admin) {
        LoginNaam = loginNaam;
        LoginPass = loginPass;
        Admin = admin;
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

    public Boolean getAdmin() { return Admin; }

    public void setAdmin(Boolean admin) { Admin = admin; }
}
