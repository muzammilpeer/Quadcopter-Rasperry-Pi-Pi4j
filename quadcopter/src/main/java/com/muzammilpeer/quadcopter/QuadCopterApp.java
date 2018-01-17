package com.muzammilpeer.quadcopter;

import com.muzammilpeer.quadcopter.controller.PWMDriverController;
import com.muzammilpeer.quadcopter.enums.MotorEnum;
import com.muzammilpeer.quadcopter.manager.PWMControllerFactory;
import com.muzammilpeer.quadcopter.model.BrushlessMotor;
import com.muzammilpeer.quadcopter.sensor.camera.RaspiStill;
import com.muzammilpeer.quadcopter.sensor.camera.RaspiVideo;

public class QuadCopterApp {

    // Define the number of photos to take.
    private static final long _numberOfImages = 20;
    // Define the interval between photos.
    private static final int _delayInterval = 170;

    public static void main(String[] args) throws Exception {
//        testESC();

//        testServo();
        moveAndTakePicture();
    }

    private static void moveAndTakePicture() {
        try {
            // Create a new RaspiStill object.
//            RaspiStill camera = new RaspiStill();
            PWMDriverController controller = PWMControllerFactory.getInstance();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    RaspiVideo video = new RaspiVideo();
                    video.recordVideo();
                }
            }).start();

//            int diff = 0;
            for (int i = 400; i < 2100; i++) {
                controller.changeMotorSpeed(controller.getServoMotors(), i);
//                if (diff > 100) {
//                    diff = 0;
//                    camera.TakePicture("image" + i + ".jpg", 800, 600);
//                }
//                diff++;

                Thread.sleep(2);
            }


            Thread.sleep(2 * 1000);
            controller.shutdown();
            Thread.sleep(2 * 1000);


        } catch (Exception e) {
            // Exit the application with the exception's hash code.
            System.exit(e.hashCode());
        }
    }

    private static void takePictures() {
        try {
            // Create a new RaspiStill object.
            RaspiStill camera = new RaspiStill();
            // Loop through the number of images to take.
            for (long i = 0; i < _numberOfImages; ++i) {
                // Capture the image.
                camera.TakePicture("image" + i + ".jpg", 800, 600);
                // Pause after each photo.
                Thread.sleep(_delayInterval);
            }
        } catch (Exception e) {
            // Exit the application with the exception's hash code.
            System.exit(e.hashCode());
        }
    }

    private static void testServo() throws Exception {
        PWMDriverController controller = PWMControllerFactory.getInstance();


        for (int i = 400; i < 2100; i++) {
            controller.changeMotorSpeed(controller.getServoMotors(), i);
            Thread.sleep(2);
        }
        Thread.sleep(2 * 1000);


        for (int i = 2100; i > 400; i--) {
            controller.changeMotorSpeed(controller.getServoMotors(), i);
            Thread.sleep(2);
        }


        Thread.sleep(2 * 1000);
        controller.shutdown();
        Thread.sleep(2 * 1000);

    }

    private static void testESC() throws Exception {
        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
        PWMDriverController controller = PWMControllerFactory.getInstance();
        controller.disArmESC(controller.getBrushlessMotors());

//        controller.calibrateESC(controller.getBrushlessMotors());
        Thread.sleep(2 * 1000);
        controller.armESC(controller.getBrushlessMotors(), true);

        Thread.sleep(3 * 1000);
        controller.disArmESC(controller.getBrushlessMotors());
        Thread.sleep(3 * 1000);
        for (int i = controller.getMinSpeed(); i < controller.getMaxSpeed(); i++) {
            controller.increaseMotorSpeed(controller.getMotor(MotorEnum.MOTOR_1));
            Thread.sleep(20);
        }
        controller.disArmESC((BrushlessMotor) controller.getMotor(MotorEnum.MOTOR_1));

        Thread.sleep(1 * 1000);
        for (int i = controller.getMinSpeed(); i < controller.getMaxSpeed(); i++) {
            controller.increaseMotorSpeed(controller.getMotor(MotorEnum.MOTOR_2));
            Thread.sleep(20);
        }
        controller.disArmESC((BrushlessMotor) controller.getMotor(MotorEnum.MOTOR_2));

        Thread.sleep(1 * 1000);
        for (int i = controller.getMinSpeed(); i < controller.getMaxSpeed(); i++) {
            controller.increaseMotorSpeed(controller.getMotor(MotorEnum.MOTOR_3));
            Thread.sleep(20);
        }
        controller.disArmESC((BrushlessMotor) controller.getMotor(MotorEnum.MOTOR_3));


        Thread.sleep(1 * 1000);
        for (int i = controller.getMinSpeed(); i < controller.getMaxSpeed(); i++) {
            controller.increaseMotorSpeed(controller.getMotor(MotorEnum.MOTOR_4));
            Thread.sleep(20);
        }
        controller.disArmESC((BrushlessMotor) controller.getMotor(MotorEnum.MOTOR_4));


        Thread.sleep(2 * 1000);
        controller.disArmESC(controller.getBrushlessMotors());

    }

}
