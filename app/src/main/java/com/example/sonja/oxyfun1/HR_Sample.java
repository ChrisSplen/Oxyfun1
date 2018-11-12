package com.example.sonja.oxyfun1;

import android.util.Log;

class HR_Sample {

    private int distance;
    private int hr;
    private int altitude;


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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }



    @Override
    public String toString() {
        return "HR_Sample{" +
                "distance=" + distance +
                ", hr=" + hr +
                '}';
    }
}
