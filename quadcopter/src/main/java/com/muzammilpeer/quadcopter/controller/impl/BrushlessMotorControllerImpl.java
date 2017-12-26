package com.muzammilpeer.quadcopter.controller.impl;

import com.muzammilpeer.quadcopter.controller.MotorController;
import com.pi4j.wiringpi.Gpio;

public class BrushlessMotorControllerImpl implements MotorController {
    //    final float PWM_RANGE = 2000;
    //    int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
    //
    //
    //    //For these calculation refer this link :
    //    // https://raspberrypi.stackexchange.com/questions/47300/answers-to-pwm-and-esc/76681#76681
    //    final float DISARM_ESC_SPEED = 0.f;
    //    final float MIN_ESC_SPEED = PWM_RANGE * (6 / 100.f);  //6%   = 120
    //    final float MAX_ESC_SPEED = PWM_RANGE * (12 / 100.f); //8%   = 160
    //    final float ARM_ESC_SPEED = PWM_RANGE * (8 / 100.f);  //12%   = 240

    private int currentSpeed = 0;
    private boolean isMotorSpinnning = false;
    private int pwmHardwareBCMPin;

    private BrushlessMotorControllerImpl() {
    }

    public BrushlessMotorControllerImpl(int pwmHardwareBCMPin) {
        this.pwmHardwareBCMPin = pwmHardwareBCMPin;
        initalizeMotor();
    }

    private void initalizeMotor() {
        Gpio.wiringPiSetupGpio();
        Gpio.pinMode(this.pwmHardwareBCMPin, Gpio.PWM_OUTPUT);
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(192); // 50Hz
        Gpio.pwmSetRange(2000); // 2000 ms
    }

    @Override
    public void changeMotorSpeed(int speed) {
        Gpio.pwmWrite(this.pwmHardwareBCMPin, speed);
    }

    @Override
    public void stopMotor() {
        changeMotorSpeed(0);
    }

    @Override
    public void startMotor() {
        changeMotorSpeed(120);
    }

    @Override
    public void runAtFullSpeedMotor() {
        changeMotorSpeed(240);
    }

    @Override
    public void runAtNormalSpeedMotor() {
        changeMotorSpeed(160);
    }

    @Override
    public boolean isMotorSpinning() {
        return this.isMotorSpinnning;
    }


}
