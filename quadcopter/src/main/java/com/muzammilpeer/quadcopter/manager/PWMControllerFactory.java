package com.muzammilpeer.quadcopter.manager;

import com.muzammilpeer.quadcopter.controller.PWMDriverController;
import com.muzammilpeer.quadcopter.controller.impl.QuadCopterPCA96885PWMDriverControllerImpl;

public class PWMControllerFactory {

    public static PWMDriverController getInstance() {
        return new QuadCopterPCA96885PWMDriverControllerImpl();
    }
}
