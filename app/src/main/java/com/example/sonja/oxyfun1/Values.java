package com.example.sonja.oxyfun1;

public class Values {

    private double dist;
    private double pulse;
    private double time;
    private double speed;

    //default contructor

    public Values(double dist, double pulse, double time, double speed) {
        this.dist = dist;
        this.pulse = pulse;
        this.time = time;
        this.speed = speed;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
