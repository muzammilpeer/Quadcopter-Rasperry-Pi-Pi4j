package com.muzammilpeer.quadcopter.model;

public class BrushlessMotor {

    private int currentSpeed = 0;
    private int pwmHardwareBCMPin;
    private boolean isMoving = false;

    private BrushlessMotor() {
    }

    public BrushlessMotor(int pwmHardwareBCMPin) {
        this.pwmHardwareBCMPin = pwmHardwareBCMPin;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        if (currentSpeed < 1) {
            isMoving = false;
        } else {
            isMoving = true;
        }
        this.currentSpeed = currentSpeed;
    }

    public int getPwmHardwareBCMPin() {
        return pwmHardwareBCMPin;
    }


    public boolean isMoving() {
        return isMoving;
    }
}
