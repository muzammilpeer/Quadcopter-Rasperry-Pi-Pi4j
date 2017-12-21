package com.muzammilpeer.quadcopter;


import com.muzammilpeer.quadcopter.diozero.MPU6050;
import com.muzammilpeer.quadcopter.diozero.Tools;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws Exception {
//
//        //Raspberry pi 3 Model B, GPIO_26 = BCM = 12 PIN, which has hardware enable PWM
//        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
//        // gpio -readall to get the mapping of all pins check this command on terminal
//
//        ElectronicSpeedController controller = new ElectronicSpeedControllerImpl(12);
//        //calibration then test flight
//        controller.calibrate();
//        controller.testFlight();
//
//        //or you have done calibration with your ESC then run a arm test for 5 seconds
////        Thread.sleep(1 * 1000);
////        controller.arm(true);
////        Thread.sleep(5 * 1000);
//        controller.disArm();

        testMPU6050();

    }


    private static void testMPU6050() throws IOException, InterruptedException {
        //testing
        MPU6050 mpu6050 = new MPU6050();
        mpu6050.startUpdatingThread();

        while (true) {
            Tools.log("-----------------------------------------------------");

            // Accelerometer
            Tools.log("Accelerometer:");
            double[] accelAngles = mpu6050.getAccelAngles();
            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(accelAngles[0]),
                    MPU6050.angleToString(accelAngles[1]), MPU6050.angleToString(accelAngles[2])));

            double[] accelAccelerations = mpu6050.getAccelAccelerations();
            Tools.log("\tAccelerations: " + MPU6050.xyzValuesToString(MPU6050.accelToString(accelAccelerations[0]),
                    MPU6050.accelToString(accelAccelerations[1]), MPU6050.accelToString(accelAccelerations[2])));

            // Gyroscope
            Tools.log("Gyroscope:");
            double[] gyroAngles = mpu6050.getGyroAngles();
            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(gyroAngles[0]),
                    MPU6050.angleToString(gyroAngles[1]), MPU6050.angleToString(gyroAngles[2])));

            double[] gyroAngularSpeeds = mpu6050.getGyroAngularSpeeds();
            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angularSpeedToString(gyroAngularSpeeds[0]),
                    MPU6050.angularSpeedToString(gyroAngularSpeeds[1]), MPU6050.angularSpeedToString(gyroAngularSpeeds[2])));

            // Filtered angles
            Tools.log("Filtered angles:");
            double[] filteredAngles = mpu6050.getFilteredAngles();
            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
                    MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));

            Tools.sleepMilliseconds(1000);
        }
    }
}

