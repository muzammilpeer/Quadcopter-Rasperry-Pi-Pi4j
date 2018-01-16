//package com.muzammilpeer.quadcopter.controller;
//
//
//import com.muzammilpeer.quadcopter.controller.impl.QuadCopterPCA96885PWMDriverControllerImpl;
//import com.muzammilpeer.quadcopter.diozero.MPU6050;
//import com.muzammilpeer.quadcopter.diozero.Tools;
//import com.muzammilpeer.quadcopter.enums.MotorEnum;
//
//import java.io.IOException;
//
//import static com.pi4j.wiringpi.Gpio.millis;
//
////Propotional integral derivative
////http://www.electronoobs.com/eng_robotica_tut6_2.php
//public class PIDController {
//    double elapsedTime, time, timePrev;
//    double rad_to_deg = 180 / 3.141592654;
//    double pwmLeft = 0, pwmRight = 0;
//    double PID = 0, error = 0, previous_error = 0;
//    double pid_p = 0;
//    double pid_i = 0;
//    double pid_d = 0;
//    /////////////////PID CONSTANTS/////////////////
//    double kp = 3.55;
//    double ki = 0.003;
//    double kd = 2.05;
/////////////////////////////////////////////////
//
//    double throttle = 15; //initial value of throttle to the motors
//    double desired_angle = 0; //This is the angle in which we whant the
//    //balance to stay steady
//    QuadCopterPCA96885PWMDriverControllerImpl controller = new QuadCopterPCA96885PWMDriverControllerImpl(12, 18, 13, 19);
////    MPU6050 mpu6050 = new MPU6050();
//        MPU6050 mpu6050;
//    double[] filteredAngles;
//
//    int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
//
//
//    public void setup() throws IOException, InterruptedException {
//        time = millis(); //Start counting time in milliseconds
//        System.out.println("Calibrate Quadcopter");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//        controller.calibrateQuadESC();
//        System.out.println("Arming Quadcopter");
//        Thread.sleep(2 * 1000);
//        controller.armQuadESC(false);
//
////            controller.changeMotorsSpeed(i);
////
//
//        System.out.println("Run motor 1");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//
////        Gpio.pinMode(controller.getMotor(MotorEnum.MOTOR_1).getPwmHardwareBCMPin(), Gpio.PWM_OUTPUT);
//        for (int i = 0; i < 100; i++) {
//            controller.changeMotorSpeed(MotorEnum.MOTOR_1, i);
//            Thread.sleep(operatingFrequency);
//        }
//        controller.disarmESC(MotorEnum.MOTOR_1);
//        System.out.println("Run motor 2");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//
////        Gpio.pinMode(controller.getMotor(MotorEnum.MOTOR_2).getPwmHardwareBCMPin(), Gpio.PWM_OUTPUT);
//        for (int i = 0; i < 100; i++) {
//            controller.changeMotorSpeed(MotorEnum.MOTOR_2, i);
//            Thread.sleep(operatingFrequency);
//        }
//        controller.disarmESC(MotorEnum.MOTOR_2);
//        System.out.println("Run motor 3");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//
////        Gpio.pinMode(controller.getMotor(MotorEnum.MOTOR_3).getPwmHardwareBCMPin(), Gpio.PWM_OUTPUT);
//        for (int i = 0; i < 100; i++) {
//            controller.changeMotorSpeed(MotorEnum.MOTOR_3, i);
//            Thread.sleep(operatingFrequency);
//        }
//        controller.disarmESC(MotorEnum.MOTOR_3);
//        System.out.println("Run motor 4");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//
////        Gpio.pinMode(controller.getMotor(MotorEnum.MOTOR_4).getPwmHardwareBCMPin(), Gpio.PWM_OUTPUT);
//        for (int i = 0; i < 100; i++) {
//            controller.changeMotorSpeed(MotorEnum.MOTOR_4, i);
//            Thread.sleep(operatingFrequency);
//        }
//        controller.disarmESC(MotorEnum.MOTOR_4);
//
////        Thread.sleep(3 * 1000);
////        controller.disArm(controller.thirdMotor);
////        controller.disarmQuadESC();
//
//        //        controller.disarmQuadESC();
////
////        for (int i = 0; i < 50; i++) {
////            controller.changeMotorSpeed(MotorEnum.MOTOR_2, i);
//////            controller.changeMotorsSpeed(i);
////            Thread.sleep(operatingFrequency);
////        }
//
//        controller.disarmQuadESC();
//        System.out.println("End test run on  Quadcopter");
//
//    }
//
//    public void loop() {
//        mpu6050.startUpdatingThread();
//        while (true) {
//            Tools.log("-----------------------------------------------------");
//
//
///////////////////////////////I M U/////////////////////////////////////
//            timePrev = time;  // the previous time is stored before the actual time read
//            time = millis();  // actual time read
//            elapsedTime = (time - timePrev) / 1000;
//
//  /*The tiemStep is the time that elapsed since the previous loop.
//   * This is the value that we will use in the formulas as "elapsedTime"
//   * in seconds. We work in ms so we haveto divide the value by 1000
//   to obtain seconds*/
//
//            /*Reed the values that the accelerometre gives.
//             * We know that the slave adress for this IMU is 0x68 in
//             * hexadecimal. For that in the RequestFrom and the
//             * begin functions we have to put this value.*/
//
////            Tools.log("Filtered angles:");
////            filteredAngles = mpu6050.getFilteredAngles();
////            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
////                    MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));
//
//            Tools.log("Quaterion angles:");
//            filteredAngles = mpu6050.getFilteredAngles();
//            Tools.log("\t" + MPU6050.xyzValuesToString(MPU6050.angleToString(filteredAngles[0]),
//                    MPU6050.angleToString(filteredAngles[1]), MPU6050.angleToString(filteredAngles[2])));
//
//
//            /*///////////////////////////P I D///////////////////////////////////*/
///*Remember that for the balance we will use just one axis. I've choose the x angle
//to implement the PID with. That means that the x axis of the IMU has to be paralel to
//the balance*/
//
//            /*First calculate the error between the desired angle and
//             *the real measured angle*/
//            error = (filteredAngles[1]) - desired_angle;
//
//            /*Next the proportional value of the PID is just a proportional constant
//             *multiplied by the error*/
//
//            pid_p = kp * error;
//
///*The integral part should only act if we are close to the
//desired position but we want to fine tune the error. That's
//why I've made a if operation for an error between -2 and 2 degree.
//To integrate we just sum the previous integral value with the
//error multiplied by  the integral constant. This will integrate (increase)
//the value each loop till we reach the 0 point*/
////            if (-3 < error < 3) {
//            if (-3 < error && error < 3) {
//                pid_i = pid_i + (ki * error);
//            }
//
///*The last part is the derivate. The derivate acts upon the speed of the error.
//As we know the speed is the amount of error that produced in a certain amount of
//time divided by that time. For taht we will use a variable called previous_error.
//We substract that value from the actual error and divide all by the elapsed time.
//Finnaly we multiply the result by the derivate constant*/
//
//            pid_d = kd * ((error - previous_error) / elapsedTime);
//
//            /*The final PID values is the sum of each of this 3 parts*/
//            PID = pid_p + pid_i + pid_d;
//
///*We know taht the min value of PWM signal is 1000us and the max is 2000. So that
//tells us that the PID value can/s oscilate more than -1000 and 1000 because when we
//have a value of 2000us the maximum value taht we could sybstract is 1000 and when
//we have a value of 1000us for the PWM sihnal, the maximum value that we could add is 1000
//to reach the maximum 2000us*/
//            if (PID < 1) {
//                PID = 1;
//            }
//            if (PID > 100) {
//                PID = 100;
//            }
//
//
//            /*Finnaly we calculate the PWM width. We sum the desired throttle and the PID value*/
//            pwmLeft = throttle + PID;
//            pwmRight = throttle - PID;
//
//
///*Once again we map the PWM values to be sure that we won't pass the min
//and max values. Yes, we've already maped the PID values. But for example, for
//throttle value of 1300, if we sum the max PID value we would have 2300us and
//that will mess up the ESC.*/
////Right
//            if (pwmRight < 1) {
//                pwmRight = 1;
//            }
//            if (pwmRight > 100) {
//                pwmRight = 100;
//            }
////Left
//            if (pwmLeft < 1) {
//                pwmLeft = 1;
//            }
//            if (pwmLeft > 100) {
//                pwmLeft = 100;
//            }
//
///*Finnaly using the servo function we create the PWM pulses with the calculated
//width for each pulse*/
//            controller.changeMotorSpeed(MotorEnum.MOTOR_1, (int) pwmLeft);
//            controller.changeMotorSpeed(MotorEnum.MOTOR_2, (int) pwmRight);
//            controller.changeMotorSpeed(MotorEnum.MOTOR_3, (int) pwmLeft);
//            controller.changeMotorSpeed(MotorEnum.MOTOR_4, (int) pwmRight);
////            controller.changeMotorsSpeed((int) pwmLeft);
////            controller.disarmESC(MotorEnum.MOTOR_2);
////            controller.disarmESC(MotorEnum.MOTOR_4);
////            controller.changeMotorsSpeed((int) pwmLeft);
//            System.out.println("Left motor = " + (int) pwmLeft + " , RIghtmotor = " + (int) pwmRight);
//
//            previous_error = error; //Remember to store the previous error.
//
//            Tools.sleepMilliseconds(operatingFrequency);
//        }
//    }//end of loop void
//
//
//}
