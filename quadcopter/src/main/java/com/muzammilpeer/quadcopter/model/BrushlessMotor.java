package com.muzammilpeer.quadcopter.model;

import com.pi4j.io.gpio.*;

public class BrushlessMotor extends BaseMotor {

    private int pwmHardwareBCMPin;
    private boolean isMoving = false;
    private GpioController gpioController = GpioFactory.getInstance();
    private GpioPinPwmOutput gpioPinPwmOutput;

    private BrushlessMotor() {
    }

//    public BrushlessMotor(int pwmHardwareBCMPin) {
//        this.pwmHardwareBCMPin = pwmHardwareBCMPin;
//    }


    public BrushlessMotor(Pin pin) {
        super();
        this.pin = pin;

//        gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin);
    }

    public int getCurrentSpeed() {
        if (currentSpeed > 2000) {
            currentSpeed = 2000;
        } else if (currentSpeed < 750) {
            currentSpeed = 750;
        }
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        isMoving = true;

        if (currentSpeed > 2000) {
            currentSpeed = 2000;
        } else if (currentSpeed < 750) {
            currentSpeed = 750;
        }

        isMoving = true;
        if (currentSpeed <= 750) {
            isMoving = false;
        }

        this.currentSpeed = currentSpeed;
    }


//
//    public int getPwmHardwareBCMPin() {
//        return pwmHardwareBCMPin;
//    }


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

    public GpioPinPwmOutput getGpioPinPwmOutput() {
        return gpioPinPwmOutput;
    }
}
