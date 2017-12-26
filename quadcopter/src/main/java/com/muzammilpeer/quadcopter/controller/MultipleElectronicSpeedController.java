package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.model.BrushlessMotor;

import java.util.List;

public interface MultipleElectronicSpeedController {

    void initializeAllESC(List<BrushlessMotor> brushlessMotorList);

    void calibrate(List<BrushlessMotor> brushlessMotorList);

    void arm(List<BrushlessMotor> brushlessMotorList, boolean isCalibrated);

    void disArm(List<BrushlessMotor> brushlessMotorList);

    void changeMotorSpeed(List<BrushlessMotor> brushlessMotorList, int speed);

}
