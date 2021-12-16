package com.greengenie.green_genie.model;

public class Weather {
    /*
    Dummy information from http://weerlive.nl/weer.php?plaatsnaam=Oss
    */


    Double Temperature; //°C
    Double Humidity; // %

    Double Rain; //
    Integer Acidity; // The acidity of the rain

    Double Speed; // Wind Speed km/h
    Integer Direction; // Wind Direction (N, NE, E, SE, S, SW, W, NW) maybe (N, NNE, NE, NEE, E, etc.)

    /**
     * Method that'll give a prediction using bare minimum information.
     * @param temperature The air temperature for the day.
     * @param humidity The air humidity for the day.
     */
    public Weather(Double temperature, Double humidity) throws Exception {
        Temperature = temperature;
        Humidity = HumidityCheck(humidity);
    }

    /**
     * Method that'll give a prediction using minimal information.
     * @param temperature The air temperature for the day.
     * @param humidity The air humidity for the day.
     * @param rain The amount of rain in ml.
     */
    public Weather(Double temperature, Double humidity, Double rain) throws Exception {
        Temperature = temperature;
        Humidity = HumidityCheck(humidity);
        Rain = rain;
    }


    /**
     * Method that'll give a prediction using bare minim information, and wind information.
     * @param temperature The air temperature for the day.
     * @param humidity The air humidity for the day.
     * @param speed The wind speed in km.
     * @param direction De degrees the wind is coming from (0° -> 360°).
     */
    public Weather(Double temperature, Double humidity, Double speed, Integer direction) throws Exception {
        Temperature = temperature;
        Humidity = HumidityCheck(humidity);
        Speed = speed;
        Direction = DirectionyCheck(direction);
    }


    /**
     * Method that'll give a prediction using minimal information, and wind information.
     * @param temperature The air temperature for the day.
     * @param humidity The air humidity for the day.
     * @param rain The amount of rain in ml.
     * @param speed The wind speed in km.
     * @param direction De degrees the wind is coming from (0° -> 360°).
     */
    public Weather(Double temperature, Double humidity, Double rain, Double speed, Integer direction) throws Exception {
        Temperature = temperature;
        Humidity = HumidityCheck(humidity);
        Rain = rain;
        Speed = speed;
        Direction = DirectionyCheck(direction);
    }

    /**
     * Method that'll give a prediction using the all information.
     * @param temperature The air temperature for the day.
     * @param humidity The air humidity for the day.
     * @param rain The amount of rain in ml.
     * @param acidity The acidity of the rain in pH;
     * @param speed The wind speed in km.
     * @param direction De degrees the wind is coming from (0° -> 360°).
     */
    public Weather(Double temperature, Double humidity, Double rain, Integer acidity, Double speed, Integer direction) throws Exception {
        Temperature = temperature;
        Humidity = HumidityCheck(humidity);
        Rain = rain;
        Acidity = acidity;
        Speed = speed;
        Direction = DirectionyCheck(direction);
    }

    public Double getTemperature() {
        return Temperature;
    }

    public void setTemperature(Double temperature) {
        Temperature = temperature;
    }

    public Double getHumidity() {
        return Humidity;
    }

    public void setHumidity(Double humidity) throws Exception {
        Humidity = HumidityCheck(humidity);
    }

    public Double getRain() {
        return Rain;
    }

    public void setRain(Double rain) {
        Rain = rain;
    }

    public Integer getAcidity() {
        return Acidity;
    }

    public void setAcidity(Integer acidity) {
        Acidity = acidity;
    }

    public Double getSpeed() {
        return Speed;
    }

    public void setSpeed(Double speed) {
        Speed = speed;
    }

    public Integer getDirection() {
        return Direction;
    }

    public void setDirection(Integer direction) throws Exception {
        Direction = DirectionyCheck(direction);
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
            Integer dir = Direction;
            if (degrees[0] < 0)
                if (dir >= degrees[0] || dir <= degrees[1]) {
                    if (ShowDegrees)
                        return String.format("%s° (%s)", Direction, directions[i]);
                    return directions[i];
                }
                else
                if (dir >= degrees[0] && dir <= degrees[1]) {
                    if (ShowDegrees)
                        return String.format("%s° (%s)", Direction, directions[i]);
                    return directions[i];
                }

        }
        return "NaN";
    }

    private double HumidityCheck(double hum) throws Exception {
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
