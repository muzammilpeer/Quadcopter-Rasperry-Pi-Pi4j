package com.muzammilpeer.quadcopter;

import com.muzammilpeer.quadcopter.controller.QuadCopterElectronicSpeedController;

public class QuadApp {

    public static void main(String[] args) throws Exception {
        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz

        QuadCopterElectronicSpeedController controller = new QuadCopterElectronicSpeedController(12, 18, 13, 19);
//        System.out.println("Calibrate Quadcopter");
//        System.in.read();
//        Thread.sleep(2 * 1000);
//        controller.calibrateQuadESC();
        System.out.println("Arming Quadcopter");
        System.in.read();
        Thread.sleep(2 * 1000);
        controller.armQuadESC(true);
        System.out.println("Speed run on  Quadcopter");
        System.in.read();
        Thread.sleep(2 * 1000);


        for (int i = 0; i < 100; i++) {
            controller.changeMotorsSpeed(i);
            Thread.sleep(operatingFrequency);
        }

        Thread.sleep(3 * 1000);
        controller.disarmQuadESC();
        System.out.println("Program end");

    }

}
