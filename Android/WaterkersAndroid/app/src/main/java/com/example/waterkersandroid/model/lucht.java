package com.example.waterkersandroid.model;

public class lucht {
    private String luchtvochtigheid, luchttemperatuur;

    public lucht (String luchttemperatuur, String luchtvochtigheid){
        this.luchttemperatuur = luchttemperatuur;
        this.luchtvochtigheid = luchtvochtigheid;
    }

    public String getLuchtvochtigheid() {
        return luchtvochtigheid;
    }

    public void setLuchtvochtigheid(String luchtvochtigheid) {
        this.luchtvochtigheid = luchtvochtigheid;
    }

    public String getLuchttemperatuur() {
        return luchttemperatuur;
    }

    public void setLuchttemperatuur(String luchttemperatuur) {
        this.luchttemperatuur = luchttemperatuur;
    }
}
