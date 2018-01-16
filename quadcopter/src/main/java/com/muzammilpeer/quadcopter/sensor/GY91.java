package com.muzammilpeer.quadcopter.sensor;

import static com.pi4j.wiringpi.Gpio.wiringPiSetup;

public class GY91 {

    public void readDataFromSensors() throws InterruptedException {
        short[] ACCxyz = new short[3];
        short[] GYRxyz = new short[3];
        short[] MAGxyz = new short[3];
        float[] MagDes = new float[3];
        float CalDes1[] = new float[3];
        float CalDes2[] = new float[3];
        float SelDes[] = new float[6];
        BMP280 bmp280 = new BMP280();
        MPU9250 mpu9250 = new MPU9250();


        wiringPiSetup();

        bmp280.BMP280();
        bmp280.BMP280_read_id();
        bmp280.BMP280_reg_check();
//        mpu9250.resetMPU9250();

//        mpu9250.predefineValues();
//        mpu9250.initMPU9250();

//        mpu9250.initAK8963(MagDes);
//        System.out.printf("MPU9250-calibrate:\n");
//        mpu9250.calibrateMPU9250(CalDes1, CalDes2);
//        Thread.sleep(1000);
//        System.out.printf("MPU9250-SelfTest:\n");
        mpu9250.MPU9250SelfTest(SelDes);

//        System.out.printf("MPU9250-calibrateMPU9250:\n");
//        System.out.printf("CalDes1:  \tX: %5.4f  \tY: %5.4f  \tZ: %5.4f\r\n", (CalDes1[0]), (CalDes1[1]), (CalDes1[2]));
//        System.out.printf("CalDes2:  \tX: %5.4f  \tY: %5.4f  \tZ: %5.4f\r\n", (CalDes2[0]), (CalDes2[1]), (CalDes2[2]));
//
//        System.out.printf("MPU9250-initAK8963:\n");
//        System.out.printf("MagDes:  \tX: %5.4f  \tY: %5.4f  \tZ: %5.4f\r\n", (MagDes[0]), (MagDes[1]), (MagDes[2]));
//
//        System.out.printf("MPU9250-MPU9250SelfTest:\n");
//        for (int i = 0; i < SelDes.length; i++) {
//            System.out.print("SelDes[" + i + "] = " + SelDes[i] + " , ");
//        }
//        System.out.printf("\n");
//
        mpu9250.loop();

//        MPU9250-calibrateMPU9250:
//        CalDes1:  	X: 44.5649  	Y: 0.2366  	Z: 35.1756
//        CalDes2:  	X: 0.9244  	Y: 0.0143  	Z: 0.4063
//        MPU9250-initAK8963:
//        MagDes:  	X: 1.2070  	Y: 1.2109  	Z: 1.1680
//        MPU9250-MPU9250SelfTest:
//        SelDes[0] = 101.15294 , SelDes[1] = 101.10363 , SelDes[2] = 101.860985 , SelDes[3] = -201.6493 , SelDes[4] = -75.34138 , SelDes[5] = -33.878666
//


//        while (true) {
//            mpu9250.readAccelData(ACCxyz);
//            mpu9250.readGyroData(GYRxyz);
//            mpu9250.readMagData(MAGxyz);
//            mpu9250.readTimeDelta();


//            bmp280.bmp280_read();
//            System.out.printf("MPU9250:\r\n");


//            System.out.printf("ACC:  \tX: %5.4f  \tY: %5.4f  \tZ: %5.4f\r\n", (ACCxyz[0] * mpu9250.getAres()), (ACCxyz[1] * mpu9250.getAres()), (ACCxyz[2] * mpu9250.getAres()));
//            System.out.printf("GYRO: \tX: %7.4f  \tY: %7.4f  \tZ: %7.4f\r\n", (GYRxyz[0] * mpu9250.getGres()), (GYRxyz[1] * mpu9250.getGres()), (GYRxyz[2] * mpu9250.getGres()));
//            System.out.printf("MAG:  \tX: %8.3f  \tY: %8.3f  \tZ: %8.3f\r\n", (MAGxyz[0] * mpu9250.getMres()), (MAGxyz[1] * mpu9250.getMres()), (MAGxyz[2] * mpu9250.getMres()));
//            System.out.printf("Temp: \t%3.1f�C\r\n\r\n", mpu9250.readTempInC());
//            System.out.printf("Roll Yaw Pitch:\r\n");

//            MadgwickQuaternionUpdate(-ax, ay, az, gx*pi/180.0f, -gy*pi/180.0f, -gz*pi/180.0f,  my,  -mx, mz);
//            MahonyQuaternionUpdate(-ax, ay, az, gx*pi/180.0f, -gy*pi/180.0f, -gz*pi/180.0f,  my,  -mx, mz);

//            float test = (float) (GYRxyz[0] * Math.PI / 180.0f);
//            float ax, ay, az, gx, gy, gz, mx, my, mz = 0;
//            ax = ACCxyz[0];
//            ay = ACCxyz[1];
//            az = ACCxyz[2];
//            gx = (float) (GYRxyz[0] * Math.PI / 180.0f);
//            gy = (float) (GYRxyz[1] * Math.PI / 180.0f);
//            gz = (float) (GYRxyz[2] * Math.PI / 180.0f);
//            mx = MAGxyz[0];
//            my = MAGxyz[1];
//            mz = MAGxyz[2];
//
//
////            mpu9250.MadgwickQuaternionUpdate(ax, ay, az, gx, gy, gz, mx, my, mz);
//            mpu9250.MahonyQuaternionUpdate(ax, ay, az, gx, gy, gz, mx, my, mz);
//
//
//            mpu9250.calculateYawRollPicth();
//            System.out.printf("Roll = %5.4f Yaw = %5.4f Pitch = %5.4f ", mpu9250.roll, mpu9250.yaw, mpu9250.pitch);


//            System.out.printf("BMP280:\r\n");
//            System.out.printf("Temp:\t\t%2.2f `C\r\n", bmp280.bmp.temperature);
//            System.out.printf("Pressure:\t%5.4f mbar\r\n", bmp280.bmp.pressure);
//            System.out.printf("Altitude:\t%5.3f m\r\n\r\n", bmp280.bmp.altitude);


//            delay(125);
//        }
    }
}
