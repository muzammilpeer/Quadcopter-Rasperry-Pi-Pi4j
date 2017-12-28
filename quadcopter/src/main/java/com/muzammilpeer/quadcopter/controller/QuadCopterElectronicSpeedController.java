package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.controller.impl.MultipleElectronicSpeedControllerImpl;
import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;

import java.util.ArrayList;
import java.util.Arrays;

public class QuadCopterElectronicSpeedController extends MultipleElectronicSpeedControllerImpl {

    ArrayList<BrushlessMotor> motors;

    private QuadCopterElectronicSpeedController() {
    }

    public QuadCopterElectronicSpeedController(int firstMotorBCM, int secondMotorBCM, int thirdMotorBCM, int forthMotorBCM) {
        motors = new ArrayList<BrushlessMotor>();

        BrushlessMotor firstMotor = new BrushlessMotor(firstMotorBCM);
        BrushlessMotor secondMotor = new BrushlessMotor(secondMotorBCM);
        BrushlessMotor thirdMotor = new BrushlessMotor(thirdMotorBCM);
        BrushlessMotor forthMotor = new BrushlessMotor(forthMotorBCM);

        motors.add(firstMotor);
        motors.add(secondMotor);
        motors.add(thirdMotor);
        motors.add(forthMotor);

        initalizeQuadESC();
        disarmQuadESC();
    }


    private void initalizeQuadESC() {
        super.initializeAllESC(motors);
    }

    public void calibrateQuadESC() {
        super.calibrate(motors);
    }

    public void armQuadESC(boolean isCalibrated) {
        super.arm(motors, isCalibrated);
    }

    public void disarmQuadESC() {
        super.disArm(motors);
    }

    public void changeMotorSpeed(MotorEnum motorEnum, int speed) {
        super.changeMotorSpeed(Arrays.asList(motors.get(motorEnum.ordinal())), speed);
    }

    public void changeMotorsSpeed(int speed) {
        super.changeMotorSpeed(motors, speed);
    }


    public void increaseMotorSpeed(MotorEnum motorEnum) {
        BrushlessMotor motor = motors.get(motorEnum.ordinal()).increaseSpeed();
        super.changeMotorSpeed(Arrays.asList(motor), motor.getCurrentSpeed());
    }

    public void decreaseMotorSpeed(MotorEnum motorEnum, int speed) {
        BrushlessMotor motor = motors.get(motorEnum.ordinal()).decreaseSpeed();
        super.changeMotorSpeed(Arrays.asList(motor), motor.getCurrentSpeed());
    }

}
