package com.muzammilpeer.quadcopter.model;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;

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
        gpioPinPwmOutput = gpioController.provisionPwmOutputPin(pin);
    }


    public void setCurrentSpeed(int currentSpeed) {
        isMoving = true;

        if (currentSpeed > 2000) {
            currentSpeed = 2000;
        } else if (currentSpeed < 1) {
            currentSpeed = 1;
        }

        isMoving = true;
        if (currentSpeed < 2) {
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
