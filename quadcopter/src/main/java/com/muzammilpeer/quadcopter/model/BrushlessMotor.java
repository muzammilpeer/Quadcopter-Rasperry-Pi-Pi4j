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
        isMoving = true;

        if (currentSpeed > 240) {
            currentSpeed = 240;
        } else if (currentSpeed < 1) {
            currentSpeed = 0;
        }

        isMoving = true;
        if (currentSpeed < 1) {
            isMoving = false;
        }

        this.currentSpeed = currentSpeed;
    }

    public int getPwmHardwareBCMPin() {
        return pwmHardwareBCMPin;
    }


    public boolean isMoving() {
        return isMoving;
    }

    public BrushlessMotor increaseSpeed() {
        setCurrentSpeed(getCurrentSpeed() + 1);
        return this;
    }

    public BrushlessMotor decreaseSpeed() {
        setCurrentSpeed(getCurrentSpeed() - 1);
        return this;
    }

}
