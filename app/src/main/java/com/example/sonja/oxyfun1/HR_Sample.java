package com.example.sonja.oxyfun1;

import android.util.Log;

class HR_Sample {

    private int distance;
    private int hr;
    private int altitude;
    private double speed;
    private int time;


    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public int getDistance() {return distance;}

    public void setDistance(int distance) {this.distance = distance;}

    public int getTime() { return time; }

    public void setTime(int time) {this.time = time;}

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) {this.speed = speed;}

    @Override
    public String toString() {
        return "HR_Sample{" +
                "distance=" + distance +
                ", hr=" + hr +
                '}';
    }


}

