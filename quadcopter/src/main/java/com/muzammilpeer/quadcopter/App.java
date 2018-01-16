package com.muzammilpeer.quadcopter;


import com.muzammilpeer.quadcopter.diozero.MPU6050;
import com.muzammilpeer.quadcopter.diozero.QuaternionFilterForMPU6050;
import com.muzammilpeer.quadcopter.diozero.Tools;
import com.muzammilpeer.quadcopter.sensor.GY91;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws Exception {
//        Pin esc1Pin = CommandArgumentParser.getPin(
//                RaspiPin.class,    // pin provider class to obtain pin instance from
//                RaspiPin.GPIO_01,  // default pin if no pin argument found
//                args);             // argument array to search in
//        Pin esc2Pin = CommandArgumentParser.getPin(
//                RaspiPin.class,    // pin provider class to obtain pin instance from
//                RaspiPin.GPIO_02,  // default pin if no pin argument found
//                args);             // argument array to search in
//        Pin esc3Pin = CommandArgumentParser.getPin(
//                RaspiPin.class,    // pin provider class to obtain pin instance from
//                RaspiPin.GPIO_24,  // default pin if no pin argument found
//                args);             // argument array to search in
//        Pin esc4Pin = CommandArgumentParser.getPin(
//                RaspiPin.class,    // pin provider class to obtain pin instance from
//                RaspiPin.GPIO_23,  // default pin if no pin argument found
//                args);             // argument array to search in
//
//        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
//        // gpio -readall to get the mapping of all pins check this command on terminal
//        ElectronicSpeedController controller1 = new ElectronicSpeedControllerImpl(18);
//        ElectronicSpeedController controller2 = new ElectronicSpeedControllerImpl(12);
////        ElectronicSpeedController controller3 = new ElectronicSpeedControllerImpl(13);
////        ElectronicSpeedController controller4 = new ElectronicSpeedControllerImpl(esc4Pin);
////        System.out.print("Start calibration for ESC 1");
////        System.in.read();
////        controller1.calibrate();
////        System.out.print("End calibration for ESC 1");
////        controller1.disArm();
////        System.out.print("Start calibration for ESC 1");
////        System.in.read();
////        controller2.calibrate();
////        System.out.print("End calibration for ESC 1");
////        controller2.disArm();
////        controller3.calibrate();
////        controller3.disArm();
////        controller4.calibrate();
////        controller4.disArm();
//
//        controller1.arm(true);
//        controller2.arm(true);
////        Thread.sleep(2 * 1000);
////        controller2.arm(true);
////        Thread.sleep(2 * 1000);
////        controller3.arm(true);
////        Thread.sleep(5 * 1000);
////        controller4.arm(true);
//        System.out.print("Call ESC are ARMED, Verify");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//        for (int i = 0; i < 100; i++) {
//            controller1.changeSpeedTo(i + 1);
//            controller2.changeSpeedTo(i + 1);
//////            controller3.changeSpeedTo(i + 1);
////            controller4.changeSpeedTo(i + 1);
//            Thread.sleep(operatingFrequency);
//        }
//
////        Thread.sleep(2 * 1000);
////        for (int i = 0; i < 100; i++) {
////            controller1.changeSpeedTo(i + 1);
////            Thread.sleep(operatingFrequency);
////        }
////        System.out.print("Call ESC are ARMED, Verify");
////        System.in.read();
////
////        for (int i = 0; i < 100; i++) {
////            controller2.changeSpeedTo(i + 1);
////            Thread.sleep(operatingFrequency);
////        }
////
////
////        for (int i = 0; i < 100; i++) {
////            controller3.changeSpeedTo(i + 1);
////            Thread.sleep(operatingFrequency);
////        }
////
////
////        for (int i = 0; i < 100; i++) {
////            controller4.changeSpeedTo(i + 1);
////            Thread.sleep(operatingFrequency);
////        }
//
//
////        Thread.sleep(5 * 1000);
////
////        for (int i = 100; i >= 0; i--) {
////            controller1.changeSpeedTo(i);
////            controller2.changeSpeedTo(i);
////            Thread.sleep(operatingFrequency);
////        }
////        Thread.sleep(1 * 1000);
////        controller.arm(true);
//        Thread.sleep(3 * 1000);
//        controller1.disArm();
//        controller2.disArm();
////        controller3.disArm();
////        controller4.disArm();
//        System.out.printf("Program end all ESC disarmed");


//        testMPU6050();
        GY91 gy91 = new GY91();
        gy91.readDataFromSensors();
    }


    private static void testMPU6050() throws IOException, InterruptedException {
        //testing
        MPU6050 mpu6050 = new MPU6050();
        mpu6050.startUpdatingThread();
        QuaternionFilterForMPU6050 quaternionFilterForMPU6050 = new QuaternionFilterForMPU6050();


        while (true) {
            Tools.log("-----------------------------------------------------");

//            // Accelerometer
//            Tools.log("Accelerometer:");
//            double[] accelAngles = mpu6050.getAccelAngles();
//            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(accelAngles[0]),
//                    MPU6050.angleToString(accelAngles[1]), MPU6050.angleToString(accelAngles[2])));
//
//            double[] accelAccelerations = mpu6050.getAccelAccelerations();
//            Tools.log("\tAccelerations: " + MPU6050.xyzValuesToString(MPU6050.accelToString(accelAccelerations[0]),
//                    MPU6050.accelToString(accelAccelerations[1]), MPU6050.accelToString(accelAccelerations[2])));
//
//            // Gyroscope
//            Tools.log("Gyroscope:");
//            double[] gyroAngles = mpu6050.getGyroAngles();
//            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(gyroAngles[0]),
//                    MPU6050.angleToString(gyroAngles[1]), MPU6050.angleToString(gyroAngles[2])));
//
//            double[] gyroAngularSpeeds = mpu6050.getGyroAngularSpeeds();
//            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angularSpeedToString(gyroAngularSpeeds[0]),
//                    MPU6050.angularSpeedToString(gyroAngularSpeeds[1]), MPU6050.angularSpeedToString(gyroAngularSpeeds[2])));

            // Filtered angles
            Tools.log("Quaterion angles:");
            double[] filteredAngles = mpu6050.getFilteredAngles();
            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
                    MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));
//            mpu6050.MadgwickQuaternionUpdate(filteredAngles[1], filteredAngles[0], filteredAngles[2], gyroAngles[1], gyroAngles[0], gyroAngles[2]);
//            quaternionFilterForMPU6050.MadgwickQuaternionUpdate(filteredAngles[0], filteredAngles[1], filteredAngles[2], gyroAngles[0], gyroAngles[1], gyroAngles[2]);

//            System.out.println("Yaw=" + mpu6050.yaw + " Roll=" + mpu6050.roll + " Pitch=" + mpu6050.pitch);


            Tools.sleepMilliseconds(100);
        }
    }
}

