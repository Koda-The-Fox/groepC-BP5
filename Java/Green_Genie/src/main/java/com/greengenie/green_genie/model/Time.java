package com.greengenie.green_genie.model;

public class Time {

    int hours;
    int minutes;
    int seconds;

    /**
     * 24Hours time format
     * @param hours
     * @param minutes
     * @param seconds
     */
    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Time(String time) throws Exception {
        int format = ValidateTimeString(time);
        if (!time.contains(":") || (format == 0 || format > 2)){
            throw new Exception("The given time is in the wrong format.");
        }
        else{
            String[] splittime = time.split(":");
            this.hours = Integer.parseInt(splittime[0]);
            this.minutes = Integer.parseInt(splittime[1]);
            if (splittime.length > 2){
                this.seconds = Integer.parseInt(splittime[3]);
            }
        }
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    private int ValidateTimeString(String string){
        int i = 0;
        for (char ch: string.toCharArray()) {
            if (ch == ':'){
                i++;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return String.format("%d:%d:%d", hours, minutes, seconds);
    }

}
