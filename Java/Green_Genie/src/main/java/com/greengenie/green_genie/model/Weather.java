package com.greengenie.green_genie.model;

public class Weather {
    /*
    Dummy data from http://weerlive.nl/weer.php?plaatsnaam=Oss
    API Key: e23912e085
    URL: https://weerlive.nl/api/json-data-10min.php?key=e23912e085&locatie=Oss
    */

    String loc = ""; // The location, (API NAme: plaats)

    Double maxTemp = 0.0; // Max temperature in °C, (API Name: d0tmax)
    Double minTemp = 0.0; // Min temperature in °C, (API Name: d0tmin)

    Integer rHum = 0; // Relative Humidity in %, (API Name: lv)

    Integer windD = 0; // Wind Direction in degrees (°), (API Name: d0windrgr)
    Integer windS = 0; // Wind Speed in beaufort(Bft), (API Name: d0windk) -> https://www.knmi.nl/kennis-en-datacentrum/uitleg/windschaal-van-beaufort

    Double dewPnt = 0.0; // Dew Point in °C, (API Name: dauwp)

    Time sunUp = new Time("00:00"); // sunrise time, (API Name: sup)
    Time sunDwn = new Time("00:00"); // sunset time, (API Name: sunder)

    Integer rain = 0; // Percentage chance of rain during the entire day, (API Name: d0neerslag)
    Integer Sun = 0; // Percentage chance of sun during the entire day, (API Name: d0zon)


    public Weather() throws Exception {
    }

    /**
     * Method that'll fill in all the data and checks if it's the right format.
     * @param loc the location for the weather.
     * @param maxTemp Maximum temperature for that day in celsius(°C).
     * @param minTemp Minimum temperature for that day in celsius(°C).
     * @param rHum Relative humidity percentage;
     * @param windD Wind Direction in degrees(°);
     * @param windS Wind Speed in km/U, will be converted to beaufort level;
     * @param dewPnt The dew point in celsius(°C).
     * @param sup The time the sun comes up.
     * @param sunder The time the sun goes down.
     * @param rain The change of rain during the day in percentage.
     * @param sun The change of sunshine during the day in percentage.
     * @throws Exception If an input is out of range, or impossible.
     */
    public Weather(String loc, Double maxTemp, Double minTemp, Integer rHum, Integer windD, Integer windS, Double dewPnt, Time sup, Time sunder, Integer rain, Integer sun) throws Exception {
        setLoc(loc);
        setMaxTemp(maxTemp);
        setMinTemp(minTemp);
        setrHum(rHum);
        setWindD(windD);
        setWindS(windS);
        setDewPnt(dewPnt);
        setSunUp(sup);
        setSunDwn(sunder);
        setRain(rain);
        setSun(sun);
    }

    /**
     * Method that'll fill in all the data and checks if it's the right format.
     * @param loc the location for the weather.
     * @param maxTemp Maximum temperature for that day in celsius(°C).
     * @param minTemp Minimum temperature for that day in celsius(°C).
     * @param rHum Relative humidity percentage;
     * @param windD Wind Direction in degrees(°);
     * @param windS Wind Speed in km/U, will be converted to beaufort level;
     * @param dewPnt The dew point in celsius(°C).
     * @param sunUp The time the sun comes up.
     * @param sunDwn The time the sun goes down.
     * @param rain The change of rain during the day in percentage.
     * @param sun The change of sunshine during the day in percentage.
     * @throws Exception If an input is out of range, or impossible.
     */
    public Weather(String loc, Double maxTemp, Double minTemp, Integer rHum, Integer windD, Integer windS, Double dewPnt, String sunUp, String sunDwn, Integer rain, Integer sun) throws Exception {
        setLoc(loc);
        setMaxTemp(maxTemp);
        setMinTemp(minTemp);
        setrHum(rHum);
        setWindD(windD);
        setWindS(windS);
        setDewPnt(dewPnt);
        setSunUp(new Time(sunUp));
        setSunDwn(new Time(sunDwn));
        setRain(rain);
        setSun(sun);
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Integer getrHum() {
        return rHum;
    }

    public void setrHum(Integer rHum) throws Exception {
        this.rHum = HumidityCheck(rHum);
    }

    public Integer getWindD() {
        return windD;
    }

    public void setWindD(Integer windD) throws Exception {
        this.windD = DirectionyCheck(windD);
    }

    public Integer getWindS() {
        return windS;
    }

    public void setWindS(Integer windS) throws Exception {
        this.windS = getBeaufort(windS);
    }

    public Double getDewPnt() {
        return dewPnt;
    }

    public void setDewPnt(Double dewPnt) {
        this.dewPnt = dewPnt;
    }

    public Time getSunUp() {
        return sunUp;
    }

    public void setSunUp(Time sunUp) {
        this.sunUp = sunUp;
    }
    public void setSunUp(String sunUp) throws Exception {
        this.sunUp = new Time(sunUp);
    }

    public Time getSunDwn() {
        return sunDwn;
    }

    public void setSunDwn(Time sunDwn) {
        this.sunDwn = sunDwn;
    }
    public void setSunDwn(String sunDwn) throws Exception {
        this.sunDwn = new Time(sunDwn);
    }

    public Integer getRain() {
        return rain;
    }

    public void setRain(Integer rain) {
        this.rain = rain;
    }

    public Integer getSun() {
        return Sun;
    }

    public void setSun(Integer sun) {
        Sun = sun;
    }

    public String getSimpleDirection(int DetailLevel, Boolean ShowDegrees) throws Exception {
        String[] directions;
        switch (DetailLevel){
            case 0:
                directions = new String[]{"N" ,"O", "S", "W"};
                break;
            case 1:
                directions = new String[]{"N" ,"NO", "O", "SO", "S", "SW", "W", "NW"};
                break;
            case 2:
                directions = new String[]{"N" , "NNO", "NO", "ONO", "O", "OZO", "ZO", "ZZO", "Z", "ZZW", "ZW", "WZW", "W", "WNW", "NW", "NNW"};
                break;
            default:
                throw new Exception("DetailLevel out of range ("+DetailLevel+");");
        }
        int divisionDegrees = 360/directions.length;
        for (int i = 0; i < directions.length; i++){
            int[] degrees = new int[]{ (divisionDegrees * i) - divisionDegrees/2, (divisionDegrees * i) + divisionDegrees/2};
            Integer dir = windD;
            if (degrees[0] < 0)
                if (dir >= degrees[0] || dir <= degrees[1]) {
                    if (ShowDegrees)
                        return String.format("%s° (%s)", windD, directions[i]);
                    return directions[i];
                }
                else
                if (dir >= degrees[0] && dir <= degrees[1]) {
                    if (ShowDegrees)
                        return String.format("%s° (%s)", windD, directions[i]);
                    return directions[i];
                }

        }
        return "NaN";
    }


    /**
     * Get the beaufort level
     * @param kmU The wind speed in km/U
     * @return returns the beaufort level, if not measurable returns 0
     */
    private int getBeaufort(Integer kmU) throws Exception {
        if (kmU < 0){
            throw new Exception("WindSpeed can not be less than 0.");
        }
        /*
         * source: https://www.knmi.nl/kennis-en-datacentrum/uitleg/windschaal-van-beaufort
         */
        else if (kmU > 117){
            return 12;
        }
        int[][] beaufort = {
                {0,1},
                {2,5},
                {6,11},
                {12,19},
                {20,19},
                {29,38},
                {39,49},
                {50,61},
                {62,74},
                {75,88},
                {89,102},
                {103,117}
        };

        for (int i = 0; i < beaufort.length; i++) {
            if (kmU >= beaufort[i][0] && kmU < beaufort[i][1]){
                return i;
            }
        }
        return 0;
    }
    private Integer HumidityCheck(Integer hum) throws Exception {
        if (hum >= 0 && hum <= 100 )
            return hum;
        throw new Exception("Humidity out of range.");
    }
    private Integer DirectionyCheck(Integer dir) throws Exception {
        if (dir >= 0 && dir <= 360 )
            return dir;
        throw new Exception("Direction out of range.");
    }
}
