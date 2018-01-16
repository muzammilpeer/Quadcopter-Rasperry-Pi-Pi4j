package com.muzammilpeer.quadcopter;

import com.muzammilpeer.quadcopter.controller.PWMDriverController;
import com.muzammilpeer.quadcopter.manager.PWMControllerFactory;

public class QuadCopterApp {

    public static void main(String[] args) throws Exception {
        int operatingFrequency = Math.round(1000.0f / 16); // 62.5 Hz
        PWMDriverController controller = PWMControllerFactory.getInstance();
        controller.calibrateESC(controller.getBrushlessMotors());

    }

}
