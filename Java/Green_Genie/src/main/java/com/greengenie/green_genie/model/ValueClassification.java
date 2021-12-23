package com.greengenie.green_genie.model;

public enum ValueClassification {
    erglaag,
    laag,
    normaal,
    hoog,
    erghoog;


    public static ValueClassification getClassification(double min, double max, double init){
        if (init < min - (min/2))
            return erglaag;
        else if (init < min)
            return laag;
        else if (init > max)
            return hoog;
        else if (init > max + (max/2))
            return erghoog;
        else
            return normaal;
    }
}
