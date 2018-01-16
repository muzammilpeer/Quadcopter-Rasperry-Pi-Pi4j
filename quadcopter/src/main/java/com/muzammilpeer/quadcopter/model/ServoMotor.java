package com.muzammilpeer.quadcopter.model;

import com.pi4j.io.gpio.Pin;

public class ServoMotor extends BaseMotor {


    private ServoMotor() {
    }

    public ServoMotor(Pin pin) {
        super();
        this.pin = pin;
    }


    public void setCurrentSpeed(int currentSpeed) {

        if (currentSpeed > 2000) {
            currentSpeed = 2000;
        } else if (currentSpeed < 1) {
            currentSpeed = 0;
        }

        if (currentSpeed < 1) {
//            isMoving = false;
        }

        this.currentSpeed = currentSpeed;
    }

    public ServoMotor increaseSpeed() {
        setCurrentSpeed(getCurrentSpeed() + 1);
        return this;
    }

    public ServoMotor decreaseSpeed() {
        setCurrentSpeed(getCurrentSpeed() - 1);
        return this;
    }

}
