package com.muzammilpeer.quadcopter.controller;

public interface ElectronicSpeedController {
    //Speed start from 1000 to 2000  µs (microsecond)

    void calibrate();

    void arm(boolean isCalibrated);

    void disArm();

    void fullSpeed();

    void minSpeed();

    int calculateSpeed(int percentage);

    void changeSpeedTo(int percentage);

    void updateSpeed(int speed);


    void increaseSpeed();

    void decreaseSpeed();

    void increaseSpeedByMultiple();

    void decreaseSpeedByMultiple();

    void setSpeedMultiple(int multiple);

    void testFlight();


}
