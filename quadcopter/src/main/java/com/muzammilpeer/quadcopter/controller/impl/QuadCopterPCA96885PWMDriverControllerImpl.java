package com.muzammilpeer.quadcopter.controller.impl;

import com.muzammilpeer.quadcopter.config.AppConfig;
import com.muzammilpeer.quadcopter.controller.PWMDriverController;
import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.enums.RunModeEnum;
import com.muzammilpeer.quadcopter.model.BaseMotor;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;
import com.muzammilpeer.quadcopter.model.ServoMotor;
import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QuadCopterPCA96885PWMDriverControllerImpl implements PWMDriverController {

    private final int MIN_ESC_SPEED = 750;
    private final int MAX_ESC_SPEED = 2000;
    private final int ARM_ESC_SPEED = 1500;
    private final int DISARM_ESC_SPEED = 1;
    private final int TIME_INTERVAL = 1000; // 1 second


    private GpioPinPwmOutput[] provisionPwmOutputs(final PCA9685GpioProvider gpioProvider) {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinPwmOutput myOutputs[] = {
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_00, "ESC 00"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_01, "ESC 01"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_02, "ESC 02"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_03, "ESC 03"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_04, "Servo 04"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_05, "Servo 05"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_06, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_07, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_08, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_09, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_10, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_11, "not usedF"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_12, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_13, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_14, "not used"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_15, "not used")};
        return myOutputs;
    }

    private int checkForOverflow(int position) {
        int result = position;
        if (position > PCA9685GpioProvider.PWM_STEPS - 1) {
            result = position - PCA9685GpioProvider.PWM_STEPS - 1;
        }
        return result;
    }

    private void presetupPCA9685() throws IOException, I2CFactory.UnsupportedBusNumberException {
        // This would theoretically lead into a resolution of 5 microseconds per step:
        // 4096 Steps (12 Bit)
        // T = 4096 * 0.000005s = 0.02048s
        // f = 1 / T = 48.828125
        BigDecimal frequency = new BigDecimal("48.828");
        // Correction factor: actualFreq / targetFreq
        // e.g. measured actual frequency is: 51.69 Hz
        // Calculate correction factor: 51.65 / 48.828 = 1.0578
        // --> To measure actual frequency set frequency without correction factor(or set to 1)
        BigDecimal frequencyCorrectionFactor = new BigDecimal("1.0578");
        // Create custom PCA9685 GPIO provider
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        pca9685GpioProvider = new PCA9685GpioProvider(bus, 0x40, frequency, frequencyCorrectionFactor);
        // Define outputs in use for this example
        GpioPinPwmOutput[] myOutputs = provisionPwmOutputs(pca9685GpioProvider);
        // Reset outputs
        pca9685GpioProvider.reset();

    }

    PCA9685GpioProvider pca9685GpioProvider;

    ArrayList<BrushlessMotor> brushlessMotors;
    ArrayList<ServoMotor> servoMotors;

    BrushlessMotor firstMotor;
    BrushlessMotor secondMotor;
    BrushlessMotor thirdMotor;
    BrushlessMotor forthMotor;

    ServoMotor verticalServoMotor;
    ServoMotor horizontalServoMotor;


    public QuadCopterPCA96885PWMDriverControllerImpl() {
        brushlessMotors = new ArrayList<BrushlessMotor>();
        servoMotors = new ArrayList<ServoMotor>();

        firstMotor = new BrushlessMotor(PCA9685Pin.PWM_00);
        secondMotor = new BrushlessMotor(PCA9685Pin.PWM_01);
        thirdMotor = new BrushlessMotor(PCA9685Pin.PWM_02);
        forthMotor = new BrushlessMotor(PCA9685Pin.PWM_03);

        verticalServoMotor = new ServoMotor(PCA9685Pin.PWM_04);
        horizontalServoMotor = new ServoMotor(PCA9685Pin.PWM_05);

        brushlessMotors.add(firstMotor);
        brushlessMotors.add(secondMotor);
        brushlessMotors.add(thirdMotor);
        brushlessMotors.add(forthMotor);

        servoMotors.add(verticalServoMotor);
        servoMotors.add(horizontalServoMotor);
    }

    @Override
    public List<BrushlessMotor> getBrushlessMotors() {
        return brushlessMotors;
    }

    @Override
    public List<ServoMotor> getServoMotors() {
        return servoMotors;
    }

    @Override
    public BaseMotor getMotor(MotorEnum motorEnum) {
        switch (motorEnum) {
            case MOTOR_1: {
                return brushlessMotors.get(motorEnum.ordinal());
            }
            case MOTOR_2: {
                return brushlessMotors.get(motorEnum.ordinal());
            }
            case MOTOR_3: {
                return brushlessMotors.get(motorEnum.ordinal());
            }
            case MOTOR_4: {
                return brushlessMotors.get(motorEnum.ordinal());
            }
            case SERVO_MOTOR_1: {
                return servoMotors.get(motorEnum.ordinal());
            }
            case SERVO_MOTOR_2: {
                return servoMotors.get(motorEnum.ordinal());
            }
            default:
                return null;
        }
    }

    @Override
    public void calibrateESC(List<BrushlessMotor> motorList) {
        try {
            disArmESC(motorList);
            System.out.println("Disconnect your power from All ESC");
            System.in.read();
            //Full Speed
            changeMotorSpeed(motorList, MAX_ESC_SPEED);
            System.out.println("Connect your power to All ESC");
            System.in.read();
            //Min Speed
            changeMotorSpeed(motorList, MIN_ESC_SPEED);
            Thread.sleep(TIME_INTERVAL);
        } catch (Exception e) {
            disArmESC(motorList);
        }
        disArmESC(motorList);
    }

    @Override
    public void calibrateESC(BrushlessMotor motor) {
        try {
            disArmESC(motor);
            System.out.println("Disconnect your power from ESC");
            System.in.read();
            //Full Speed
            changeMotorSpeed(motor, MAX_ESC_SPEED);
            System.out.println("Connect your power to ESC");
            System.in.read();
            //Min Speed
            changeMotorSpeed(motor, MIN_ESC_SPEED);
            Thread.sleep(TIME_INTERVAL);
        } catch (Exception e) {
            disArmESC(motor);
        }
        disArmESC(motor);
    }

    @Override
    public void armESC(List<BrushlessMotor> motorList, boolean isCalibrated) {
        if (isCalibrated) {
            try {
                disArmESC(motorList);
                Thread.sleep(TIME_INTERVAL);
                //Full Speed
                changeMotorSpeed(motorList, MAX_ESC_SPEED);
                Thread.sleep(TIME_INTERVAL);
                //Min Speed
                changeMotorSpeed(motorList, MIN_ESC_SPEED);
                Thread.sleep(2 * TIME_INTERVAL);
            } catch (Exception e) {
                disArmESC(motorList);
            }
        }
        //Arming speed  Speed 50% or some other value
        changeMotorSpeed(motorList, ARM_ESC_SPEED);
    }

    @Override
    public void armESC(BrushlessMotor motor, boolean isCalibrated) {
        if (isCalibrated) {
            try {
                disArmESC(motor);
                Thread.sleep(TIME_INTERVAL);
                //Full Speed
                changeMotorSpeed(motor, MAX_ESC_SPEED);
                Thread.sleep(TIME_INTERVAL);
                //Min Speed
                changeMotorSpeed(motor, MIN_ESC_SPEED);
                Thread.sleep(2 * TIME_INTERVAL);
            } catch (Exception e) {
                disArmESC(motor);
            }
        }
        //Arming speed  Speed 50% or some other value
        changeMotorSpeed(motor, ARM_ESC_SPEED);
    }

    @Override
    public void disArmESC(List<BrushlessMotor> motorList) {
        changeMotorSpeed(motorList, DISARM_ESC_SPEED);
    }

    @Override
    public void disArmESC(BrushlessMotor motor) {
        changeMotorSpeed(motor, DISARM_ESC_SPEED);
    }

    @Override
    public void changeMotorSpeed(List<? extends BaseMotor> motorList, int speed) {
        for (BaseMotor motor : motorList) {
            changeMotorSpeed(motor, speed);
        }
    }

    @Override
    public void changeMotorSpeed(BaseMotor motor, int speed) {
        motor.setCurrentSpeed(speed);
        pca9685GpioProvider.setPwm(motor.getPin(), motor.getCurrentSpeed());
        if (AppConfig.CURRENT_MODE == RunModeEnum.DEBUG) {
            System.out.println("Motor[" + motor.getPin().getName() + "] changeSpeedTo=" + motor.getCurrentSpeed() + "  calculateSpeed(speed) = " + speed);
        }
    }

}
