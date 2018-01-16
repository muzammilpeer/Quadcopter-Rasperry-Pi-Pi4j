package com.muzammilpeer.quadcopter.model;

import com.pi4j.io.gpio.Pin;

public class BaseMotor {
    protected int currentSpeed = 0;
    protected Pin pin;


    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Pin getPin() {
        return pin;
    }
}
