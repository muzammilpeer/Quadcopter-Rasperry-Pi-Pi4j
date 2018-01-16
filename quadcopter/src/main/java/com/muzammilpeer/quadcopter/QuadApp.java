package com.muzammilpeer.quadcopter;

//import com.muzammilpeer.quadcopter.controller.PIDController;

public class QuadApp {

    public static void main(String[] args) throws Exception {
        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
//        PIDController controller = new PIDController();
//        controller.setup();
//        Thread.sleep(15 * 1000);
//        System.out.println("Main loop started");
//        controller.loop();

//
//        QuadCopterPCA96885PWMDriverControllerImpl controller = new QuadCopterPCA96885PWMDriverControllerImpl(12, 18, 13, 19);
////        System.out.println("Calibrate Quadcopter");
////        System.in.read();
////        Thread.sleep(2 * 1000);
////        controller.calibrateQuadESC();
//        System.out.println("Arming Quadcopter");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//        controller.armQuadESC(true);
//        System.out.println("Start test run on  Quadcopter");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//
//
//        for (int i = 0; i < 100; i++) {
//            controller.changeMotorsSpeed(i);
//            Thread.sleep(operatingFrequency);
//        }
//        System.out.println("End test run on  Quadcopter");
//
        Thread.sleep(3 * 1000);
        System.out.println("Program end");

    }

}
