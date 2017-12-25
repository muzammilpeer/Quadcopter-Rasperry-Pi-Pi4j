package com.muzammilpeer.quadcopter.model;

public class Motor {
    public int bcmPin;
    public int currentSpeed = 0;

    public int getCurrentSpeed() {
        if (currentSpeed > 240)
            currentSpeed = 240;
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
}
