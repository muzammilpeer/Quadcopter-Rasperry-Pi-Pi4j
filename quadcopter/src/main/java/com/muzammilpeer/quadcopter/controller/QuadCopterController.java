package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.model.BrushlessMotor;

public interface QuadCopterController {
    //while (1)
    void readSensorData();

    void attitudeEstimation();

    void receiveControlSignalFromNetwork();

    void pidController();

    void controlMotorSpeed();

}
