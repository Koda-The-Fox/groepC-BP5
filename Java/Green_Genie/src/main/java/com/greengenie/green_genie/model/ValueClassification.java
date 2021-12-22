package com.greengenie.green_genie.model;

public enum ValueClassification {
    laag,
    normaal,
    hoog;


    public static ValueClassification getClassification(double min, double max, double init){
        if (init < min)
            return laag;
        else if (init > max)
            return hoog;
        else
            return normaal;
    }
}
