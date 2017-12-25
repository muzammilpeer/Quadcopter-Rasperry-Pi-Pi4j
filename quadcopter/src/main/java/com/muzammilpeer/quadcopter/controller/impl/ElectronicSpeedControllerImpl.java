package com.muzammilpeer.quadcopter.controller.impl;

import com.muzammilpeer.quadcopter.controller.ElectronicSpeedController;
import com.pi4j.wiringpi.Gpio;

public class ElectronicSpeedControllerImpl implements ElectronicSpeedController {
    //Electronic speed controller range from 1000 to 2000  µs (microsecond)
    // with delay of 1 second for calibration or arming.


    //Some calculation regarding duty cycles and relevant values

    //Duty Cycle = 1/2 half 50% => 2000/2 => 1000 duty cycle (half the length)
    // duty cycle /frequency = 1000/60hz = 16.66 pulse width
    // Operating range = 1/pulse width => 1/16.66 => 0.06 % of actual range 6%
    // 0.06 * 2000 => 120

    //Duty Cycle = 1/3 quarter half 37.5%=> 2000/3 => 666.66 duty cycle (half the length)
    // duty cycle /frequency = 666.66/60hz = 11.11 pulse width
    // Operating range = 1/pulse width => 1/11.11 => 0.09 % of actual range 8%
    // 0.09 * 2000 => 180


    //Duty Cycle = 1/4 quarter 25%=> 2000/4 => 500 duty cycle (half the length)
    // duty cycle /frequency = 500/60hz = 8.33 pulse width
    // Operating range = 1/pulse width => 1/8.33 => 0.12 % of actual range 12%
    // 0.12 * 2000 => 240

    //Duty Cycle = 1/8 quarter half 12.5%=> 2000/8 => 250 duty cycle (half the length)
    // duty cycle /frequency = 250/60hz = 4.16 pulse width
    // Operating range = 1/pulse width => 1/4.16 => 0.24 % of actual range 24%


    final float PWM_RANGE = 2000;
    int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz


    //For these calculation refer this link :
    // https://raspberrypi.stackexchange.com/questions/47300/answers-to-pwm-and-esc/76681#76681
    final float DISARM_ESC_SPEED = 0.f;
    final float MIN_ESC_SPEED = PWM_RANGE * (6 / 100.f);  //6%
    final float MAX_ESC_SPEED = PWM_RANGE * (12 / 100.f); //8%
    final float ARM_ESC_SPEED = PWM_RANGE * (8 / 100.f);  //12%
    final float SPEED_RANGE = MAX_ESC_SPEED - MIN_ESC_SPEED;

    private int SPEED_CHANGE_MULTIPLE_OF = 10; //default value
    private int SPEED_CHANGE_UNIT = 1; //default value

    final int TIME_INTERVAL = 1000; // 1 second

    int currentESCSpeed = 0;

    int bcmPinNumber = -1;

    private ElectronicSpeedControllerImpl() {
    }

    public ElectronicSpeedControllerImpl(int bcmPin) {
        super();
        this.bcmPinNumber = bcmPin;
        this.initializeESC();
    }

    private void initializeESC() {
        //pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange.
        // Frequency  = 19200000 / 1920 / 200 == 50 hz freq
        // 19200000/192 = 100,000 //microseconds
        // 100,000/2000 = 50hz


        //Reference http://pijava.com/SePiServoControlExplained.html
        //I take my 50Hz * 20ms Period * 100 thing-a-ma-jigs Ratio = 100,000
        //To get my PWM Clock, I divide 19,200,000 / 100,000 = 192,
        //I need to divide 100,000 / 50Hz = 2000
//        try {
//            Class<Gpio> c = (Class<Gpio>) Class.forName("com.pi4j.wiringpi.Gpio");
//            com.pi4j.wiringpi.Gpio sample = (com.pi4j.wiringpi.Gpio) c.newInstance();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


        Gpio.wiringPiSetupGpio();
        Gpio.pinMode(this.bcmPinNumber, Gpio.PWM_OUTPUT);
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(192); // 50Hz
        Gpio.pwmSetRange(2000); // 2000 ms
    }


    public void calibrate() {

        try {
            fullSpeed();
            System.out.println("Connect your power");
            System.in.read();
            minSpeed();
            Thread.sleep(TIME_INTERVAL);
        } catch (Exception e) {
            disArm();
        }
        disArm();
    }

    public void arm(boolean isCalibrated) {
        if (isCalibrated) {
            try {
                disArm();
                Thread.sleep(TIME_INTERVAL);
                fullSpeed();
                Thread.sleep(TIME_INTERVAL);
                minSpeed();
                Thread.sleep(2 * TIME_INTERVAL);
            } catch (Exception e) {
                disArm();
            }
        }
//        currentESCSpeed = Math.round(ARM_ESC_SPEED); //50% for arming
//        updateSpeed(currentESCSpeed);
    }


    public void testFlight() {
        try {
            System.out.println("Ready for arming");
            System.in.read();
            Thread.sleep(TIME_INTERVAL);
            this.arm(false);
            Thread.sleep(3 * TIME_INTERVAL);
            this.minSpeed();
            Thread.sleep(2 * TIME_INTERVAL);
            for (int i = 0; i < 100; i++) {
                this.changeSpeedTo(i + 1);
                Thread.sleep(operatingFrequency);
            }
            Thread.sleep(3 * TIME_INTERVAL);
            this.disArm();
        } catch (Exception e) {
            this.disArm();
        }
        this.disArm();
    }


    public void disArm() {
        currentESCSpeed = Math.round(DISARM_ESC_SPEED);
        updateSpeed(currentESCSpeed);
    }

    public void updateSpeed(int speed) {

        Gpio.pwmWrite(this.bcmPinNumber, speed);
    }


    public void fullSpeed() {
        currentESCSpeed = Math.round(MAX_ESC_SPEED);
        updateSpeed(currentESCSpeed);
    }

    public void minSpeed() {
        currentESCSpeed = Math.round(MIN_ESC_SPEED);
        updateSpeed(currentESCSpeed);
    }

    //Speed operations
    public int calculateSpeed(int percentage) {
        int updatedSpeed = Math.round((percentage / 100.f) * SPEED_RANGE);
        return updatedSpeed;
    }

    public void changeSpeedTo(int percentage) {
        currentESCSpeed = Math.round((MIN_ESC_SPEED + calculateSpeed(percentage)));
        System.out.println("changeSpeedTo=" + currentESCSpeed + "  calculateSpeed(percentage) = " + percentage);
        updateSpeed(currentESCSpeed);
    }


    public void increaseSpeed() {
        currentESCSpeed = currentESCSpeed + calculateSpeed(SPEED_CHANGE_UNIT);
        if (currentESCSpeed >= MAX_ESC_SPEED) {
            currentESCSpeed = Math.round(MAX_ESC_SPEED);
        }
        updateSpeed(currentESCSpeed);
    }

    public void decreaseSpeed() {
        currentESCSpeed = currentESCSpeed - calculateSpeed(SPEED_CHANGE_UNIT);
        if (currentESCSpeed <= MIN_ESC_SPEED) {
            currentESCSpeed = Math.round(MIN_ESC_SPEED);
        }
        updateSpeed(currentESCSpeed);
    }

    public void increaseSpeedByMultiple() {
        currentESCSpeed = currentESCSpeed + calculateSpeed(SPEED_CHANGE_MULTIPLE_OF);
        if (currentESCSpeed >= MAX_ESC_SPEED) {
            currentESCSpeed = Math.round(MAX_ESC_SPEED);
        }
        updateSpeed(currentESCSpeed);
    }

    public void decreaseSpeedByMultiple() {
        currentESCSpeed = currentESCSpeed - calculateSpeed(SPEED_CHANGE_MULTIPLE_OF);
        if (currentESCSpeed <= MIN_ESC_SPEED) {
            currentESCSpeed = Math.round(MIN_ESC_SPEED);
        }
        updateSpeed(currentESCSpeed);
    }

    public void setSpeedMultiple(int multiple) {
        SPEED_CHANGE_MULTIPLE_OF = multiple;
    }


}
