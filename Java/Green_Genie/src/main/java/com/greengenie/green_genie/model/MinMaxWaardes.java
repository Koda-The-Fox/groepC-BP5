package com.greengenie.green_genie.model;

public class MinMaxWaardes {
    private ArduinoLocatie AL = new ArduinoLocatie();
    private double MinPH = 6; // value source : https://www.nrcs.usda.gov/Internet/FSE_DOCUMENTS/nrcs142p2_052208.pdf
    private double MaxPH = 7; // value source : https://www.nrcs.usda.gov/Internet/FSE_DOCUMENTS/nrcs142p2_052208.pdf
    private double MinGT = 15; // value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
    private double MaxGT = 24; // value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
    private double MinLT = 20; // value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
    private double MaxLT = 60; // value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
    private double MinGV = 15; // value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
    private double MaxGV = 24; // value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
    private double MinLV = 50; // value source: https://greenupside.com/what-is-the-best-humidity-level-for-plants/
    private double MaxLV = 60; // value source: https://greenupside.com/what-is-the-best-humidity-level-for-plants/

    public Boolean fromDB = false;

    public MinMaxWaardes() {}

    public MinMaxWaardes(ArduinoLocatie AL) {
        this.AL = AL;
    }

    public MinMaxWaardes(ArduinoLocatie al, double minPH, double maxPH, double minGT, double maxGT, double minLT, double maxLT, double minGV, double maxGV, double minLV, double maxLV) {
        AL = al;
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

    public ArduinoLocatie getAL() {
        return AL;
    }

    public void setAL(ArduinoLocatie AL) {
        this.AL = AL;
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

    public Boolean getFromDB() {
        return fromDB;
    }

    public void setFromDB(Boolean fromDB) {
        this.fromDB = fromDB;
    }

    public MinMaxWaardes copy(){
        return new MinMaxWaardes(
                this.AL,
                this.MinPH,
                this.MaxPH,
                this.MinGT,
                this.MaxGT,
                this.MinLT,
                this.MaxLT,
                this.MinGV,
                this.MaxGV,
                this.MinLV,
                this.MaxLV
        );
    }


    public boolean equals(MinMaxWaardes otherMMW){
        return
                this.MinPH == otherMMW.MinPH &&
                this.MaxPH == otherMMW.MaxPH &&
                this.MinGT == otherMMW.MinGT &&
                this.MaxGT == otherMMW.MaxGT &&
                this.MinLT == otherMMW.MinLT &&
                this.MaxLT == otherMMW.MaxLT &&
                this.MinGV == otherMMW.MinGV &&
                this.MaxGV == otherMMW.MaxGV &&
                this.MinLV == otherMMW.MinLV &&
                this.MaxLV == otherMMW.MaxLV;
    }

    @Override
    public String toString() {
        return "Lucht temperatuur= " + MinLT + " <-> " + MaxLT +
                "\nLucht vochtigheid= \t" + MinLV + " <-> " + MaxLV +
                "\nGrond temperatuur= " + MinGT + " <-> " + MaxGT +
                "\nGrond vochtigheid= " + MinGV + " <-> " + MaxGV;
    }
}
