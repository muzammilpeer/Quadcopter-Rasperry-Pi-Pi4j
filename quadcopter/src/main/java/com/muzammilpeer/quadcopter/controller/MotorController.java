package com.muzammilpeer.quadcopter.controller;

public interface MotorController {

    void changeMotorSpeed(int speed);

    void stopMotor();

    void startMotor();

    void runAtFullSpeedMotor();

    void runAtNormalSpeedMotor();

    boolean isMotorSpinning();

}
