package com.greengenie.green_genie.model;

public class Prediction {

    Double TempAir;
    Double Humidity;

    Double TempGround;

    Integer pH;

    public Prediction(Double tempAir, Double humidity, Double tempGround, Integer pH) {
        TempAir = tempAir;
        Humidity = humidity;
        TempGround = tempGround;
        this.pH = pH;
    }

    public Double getTempAir() {
        return TempAir;
    }

    public void setTempAir(Double tempAir) {
        TempAir = tempAir;
    }

    public Double getHumidity() {
        return Humidity;
    }

    public void setHumidity(Double humidity) {
        Humidity = humidity;
    }

    public Double getTempGround() {
        return TempGround;
    }

    public void setTempGround(Double tempGround) {
        TempGround = tempGround;
    }

    public Integer getpH() {
        return pH;
    }

    public void setpH(Integer pH) {
        this.pH = pH;
    }
}
