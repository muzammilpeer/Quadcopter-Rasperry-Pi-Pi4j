package com.muzammilpeer.quadcopter.controller;

import com.muzammilpeer.quadcopter.controller.impl.MultipleElectronicSpeedControllerImpl;
import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuadCopterElectronicSpeedController extends MultipleElectronicSpeedControllerImpl {

    ArrayList<BrushlessMotor> motors;
    public BrushlessMotor firstMotor;
    public BrushlessMotor secondMotor;
    public BrushlessMotor thirdMotor;
    public BrushlessMotor forthMotor;


    private QuadCopterElectronicSpeedController() {
    }

    public QuadCopterElectronicSpeedController(int firstMotorBCM, int secondMotorBCM, int thirdMotorBCM, int forthMotorBCM) {
        motors = new ArrayList<BrushlessMotor>();

        firstMotor = new BrushlessMotor(firstMotorBCM);
        secondMotor = new BrushlessMotor(secondMotorBCM);
        thirdMotor = new BrushlessMotor(thirdMotorBCM);
        forthMotor = new BrushlessMotor(forthMotorBCM);

        motors.add(firstMotor);
        motors.add(secondMotor);
        motors.add(thirdMotor);
        motors.add(forthMotor);

        motors = (ArrayList<BrushlessMotor>) initalizeQuadESC();
        motors = (ArrayList<BrushlessMotor>) disarmQuadESC();
    }


    private List<BrushlessMotor> initalizeQuadESC() {
        return super.initializeAllESC(motors);
    }

    public List<BrushlessMotor> calibrateQuadESC() {
        return super.calibrate(motors);
    }

    public List<BrushlessMotor> armQuadESC(boolean isCalibrated) {
        return super.arm(motors, isCalibrated);
    }

    public List<BrushlessMotor> disarmQuadESC() {
        return super.disArm(motors);
    }

    public BrushlessMotor disarmESC(MotorEnum motorEnum) {
        return super.disArm(Arrays.asList(motors.get(motorEnum.ordinal()))).get(0);
    }

    public BrushlessMotor changeMotorSpeed(MotorEnum motorEnum, int speed) {
        System.out.println("Inner Motor found Pin No = " + motors.get(motorEnum.ordinal()).getPwmHardwareBCMPin());
        return super.changeMotorSpeed(Arrays.asList(motors.get(motorEnum.ordinal())), speed).get(0);
    }

    public List<BrushlessMotor> changeMotorsSpeed(int speed) {
        return super.changeMotorSpeed(motors, speed);
    }


    public BrushlessMotor increaseMotorSpeed(MotorEnum motorEnum) {
        BrushlessMotor motor = motors.get(motorEnum.ordinal()).increaseSpeed();
        super.changeMotorSpeed(Arrays.asList(motor), motor.getCurrentSpeed());
        return motor;
    }

    public BrushlessMotor increaseMotorSpeed(BrushlessMotor motor) {
        motor = motor.increaseSpeed();
        motor = super.changeMotorSpeed(motor, motor.getCurrentSpeed());
        return motor;
    }

    public List<BrushlessMotor> decreaseMotorSpeed(MotorEnum motorEnum) {
        BrushlessMotor motor = motors.get(motorEnum.ordinal()).decreaseSpeed();
        super.changeMotorSpeed(Arrays.asList(motor), motor.getCurrentSpeed());
        return motors;
    }

    public BrushlessMotor decreaseMotorSpeed(BrushlessMotor motor) {
        motor = motor.decreaseSpeed();
        motor = super.changeMotorSpeed(motor, motor.getCurrentSpeed());
        return motor;
    }

}
