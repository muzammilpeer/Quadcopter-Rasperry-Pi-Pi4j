package com.muzammilpeer.quadcopter.controller.impl;

import com.muzammilpeer.quadcopter.controller.MultipleElectronicSpeedController;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;
import com.pi4j.wiringpi.Gpio;

import java.util.List;

public class MultipleElectronicSpeedControllerImpl implements MultipleElectronicSpeedController {


    final float PWM_RANGE = 2000;
    int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz


    //For these calculation refer this link :
    // https://raspberrypi.stackexchange.com/questions/47300/answers-to-pwm-and-esc/76681#76681
    final float DISARM_ESC_SPEED = 0.f;
    final float MIN_ESC_SPEED = PWM_RANGE * (6 / 100.f);  //6%
    final float MAX_ESC_SPEED = PWM_RANGE * (12 / 100.f); //8%
    final float ARM_ESC_SPEED = PWM_RANGE * (8 / 100.f);  //12%
    final float SPEED_RANGE = MAX_ESC_SPEED - MIN_ESC_SPEED;

    final int TIME_INTERVAL = 1000; // 1 second


    @Override
    public void initializeAllESC(List<BrushlessMotor> brushlessMotorList) {
        //For reference
        //pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange.
        // Frequency  = 19200000 / 1920 / 200 == 50 hz freq
        // 19200000/192 = 100,000 //microseconds
        // 100,000/2000 = 50hz


        //Reference http://pijava.com/SePiServoControlExplained.html
        //I take my 50Hz * 20ms Period * 100 thing-a-ma-jigs Ratio = 100,000
        //To get my PWM Clock, I divide 19,200,000 / 100,000 = 192,
        //I need to divide 100,000 / 50Hz = 2000


        Gpio.wiringPiSetupGpio();
        for (BrushlessMotor motor : brushlessMotorList) {
            Gpio.pinMode(motor.getPwmHardwareBCMPin(), Gpio.PWM_OUTPUT);
        }
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(192); // 50Hz
        Gpio.pwmSetRange(2000); // 2000 ms
    }

    @Override
    public void calibrate(List<BrushlessMotor> brushlessMotorList) {
        try {
            disArm(brushlessMotorList);
            System.out.println("Disconnect your power from All ESC");
            System.in.read();
            //Full Speed
            changeMotorSpeed(brushlessMotorList, Math.round(MAX_ESC_SPEED));
            System.out.println("Connect your power to All ESC");
            System.in.read();
            //Min Speed
            changeMotorSpeed(brushlessMotorList, Math.round(MIN_ESC_SPEED));
            Thread.sleep(TIME_INTERVAL);
        } catch (Exception e) {
            disArm(brushlessMotorList);
        }
        disArm(brushlessMotorList);
    }

    @Override
    public void arm(List<BrushlessMotor> brushlessMotorList, boolean isCalibrated) {
        if (isCalibrated) {
            try {
                disArm(brushlessMotorList);
                Thread.sleep(TIME_INTERVAL);
                //Full Speed
                changeMotorSpeed(brushlessMotorList, Math.round(MAX_ESC_SPEED));
                Thread.sleep(TIME_INTERVAL);
                //Min Speed
                changeMotorSpeed(brushlessMotorList, Math.round(MIN_ESC_SPEED));
                Thread.sleep(2 * TIME_INTERVAL);
            } catch (Exception e) {
                disArm();
            }
        }
        //Arming speed  Speed 50% or some other value
        changeMotorSpeed(brushlessMotorList, Math.round(ARM_ESC_SPEED));
    }

    @Override
    public void disArm(List<BrushlessMotor> brushlessMotorList) {
        changeMotorSpeed(brushlessMotorList, Math.round(DISARM_ESC_SPEED));
    }


    //Speed operations
    private int calculateSpeed(int percentage) {
        int updatedSpeed = Math.round((percentage / 100.f) * SPEED_RANGE);
        return updatedSpeed;
    }

    @Override
    public void changeMotorSpeed(List<BrushlessMotor> brushlessMotorList, int speed) {
        for (BrushlessMotor motor : brushlessMotorList) {
            motor.setCurrentSpeed(Math.round((MIN_ESC_SPEED + calculateSpeed(speed))));
            Gpio.pwmWrite(motor.getPwmHardwareBCMPin(), motor.getCurrentSpeed());
            System.out.println("MotorBCM[" + motor.getPwmHardwareBCMPin() + "] changeSpeedTo=" + motor.getCurrentSpeed() + "  calculateSpeed(speed) = " + speed);
        }
    }
}
