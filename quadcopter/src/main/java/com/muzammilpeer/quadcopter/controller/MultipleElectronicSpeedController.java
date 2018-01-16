package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;

import java.util.List;

public interface MultipleElectronicSpeedController {

    BrushlessMotor getMotor(MotorEnum motorEnum);

    List<BrushlessMotor> initializeAllESC(List<BrushlessMotor> brushlessMotorList);

    BrushlessMotor initializeESC(BrushlessMotor brushlessMotor);

    List<BrushlessMotor> calibrate(List<BrushlessMotor> brushlessMotorList);

    BrushlessMotor calibrate(BrushlessMotor brushlessMotor);

    List<BrushlessMotor> arm(List<BrushlessMotor> brushlessMotorList, boolean isCalibrated);

    BrushlessMotor arm(BrushlessMotor brushlessMotor, boolean isCalibrated);

    List<BrushlessMotor> disArm(List<BrushlessMotor> brushlessMotorList);

    BrushlessMotor disArm(BrushlessMotor brushlessMotor);

    List<BrushlessMotor> changeMotorSpeed(List<BrushlessMotor> brushlessMotorList, int speed);

    BrushlessMotor changeMotorSpeed(BrushlessMotor brushlessMotor, int speed);

}
