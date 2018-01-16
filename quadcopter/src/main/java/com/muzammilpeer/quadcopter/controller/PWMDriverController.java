package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.model.BaseMotor;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;
import com.muzammilpeer.quadcopter.model.ServoMotor;

import java.util.List;

public interface PWMDriverController {

    List<BrushlessMotor> getBrushlessMotors();

    List<ServoMotor> getServoMotors();

    BaseMotor getMotor(MotorEnum motorEnum);

    void calibrateESC(List<BrushlessMotor> motorList);

    void calibrateESC(BrushlessMotor motor);

    void armESC(List<BrushlessMotor> motorList, boolean isCalibrated);

    void armESC(BrushlessMotor motor, boolean isCalibrated);

    void disArmESC(List<BrushlessMotor> motorList);

    void disArmESC(BrushlessMotor motor);

    void changeMotorSpeed(List<? extends BaseMotor> motorList, int speed);

    void changeMotorSpeed(BaseMotor motor, int speed);

}
