package com.muzammilpeer.quadcopter.diozero;

import static java.lang.Math.atan;
import static java.lang.Math.atan2;
import static java.lang.StrictMath.sqrt;

public class QuaternionFilterForMPU6050 {
    public double beta, zeta;
    public double PI, GyroMeasError, GyroMeasDrift;
    public double pitch, yaw, roll;
    public double deltat = 0.0f;                             // integration interval for both filter schemes
    public double q[] = {0.0f, 0.0f, 0.0f, 0.0f};           // vector to hold quaternion


    public QuaternionFilterForMPU6050() {
        PI = 3.14159265358979323846f;
//        #define GyroMeasError PI * (40.0f / 180.0f)       // gyroscope measurement error in rads/s (shown as 3 deg/s)
//#define GyroMeasDrift PI * (0.0f / 180.0f)

//        GyroMeasError = PI * (40.0f / 180.0f);     // gyroscope measurement error in rads/s (start at 60 deg/s), then reduce after ~10 s to 3
//        GyroMeasDrift = PI * (0.0f / 180.0f);      // gyroscope measurement drift in rad/s/s (start at 0.0 deg/s/s)
        GyroMeasError = PI * (40.0f / 180.0f);     // gyroscope measurement error in rads/s (start at 60 deg/s), then reduce after ~10 s to 3
        GyroMeasDrift = PI * (1.0f / 180.0f);      // gyroscope measurement drift in rad/s/s (start at 0.0 deg/s/s)

        beta = (double) (sqrt(3.0f / 4.0f) * GyroMeasError);  // compute beta
        zeta = (double) (sqrt(3.0f / 4.0f) * GyroMeasDrift);  // compute zeta, the other free parameter in the Madgwick scheme usually set to a small or zero value
    }


    // Implementation of Sebastian Madgwick's "...efficient orientation filter for... inertial/magnetic sensor arrays"
// (see http://www.x-io.co.uk/category/open-source/ for examples and more details)
// which fuses acceleration and rotation rate to produce a quaternion-based estimate of relative
// device orientation -- which can be converted to yaw, pitch, and roll. Useful for stabilizing quadcopters, etc.
// The performance of the orientation filter is at least as good as conventional Kalman-based filtering algorithms
// but is much less computationally intensive---it can be performed on a 3.3 V Pro Mini operating at 8 MHz!
    public void MadgwickQuaternionUpdate(double ax, double ay, double az, double gx, double gy, double gz) {


        double q1 = q[0], q2 = q[1], q3 = q[2], q4 = q[3];         // short name local variable for readability
        double norm;                                               // vector norm
        double f1, f2, f3;                                         // objetive funcyion elements
        double J_11or24, J_12or23, J_13or22, J_14or21, J_32, J_33; // objective function Jacobian elements
        double qDot1, qDot2, qDot3, qDot4;
        double hatDot1, hatDot2, hatDot3, hatDot4;
        double gerrx, gerry, gerrz, gbiasx = 0, gbiasy = 0, gbiasz = 0;        // gyro bias error

        // Auxiliary variables to avoid repeated arithmetic
        double _halfq1 = 0.5f * q1;
        double _halfq2 = 0.5f * q2;
        double _halfq3 = 0.5f * q3;
        double _halfq4 = 0.5f * q4;
        double _2q1 = 2.0f * q1;
        double _2q2 = 2.0f * q2;
        double _2q3 = 2.0f * q3;
        double _2q4 = 2.0f * q4;
        double _2q1q3 = 2.0f * q1 * q3;
        double _2q3q4 = 2.0f * q3 * q4;

        // Normalise accelerometer measurement
        norm = (double) sqrt(ax * ax + ay * ay + az * az);
        if (norm == 0.0f) return; // handle NaN
        norm = 1.0f / norm;
        ax *= norm;
        ay *= norm;
        az *= norm;

        // Compute the objective function and Jacobian
        f1 = _2q2 * q4 - _2q1 * q3 - ax;
        f2 = _2q1 * q2 + _2q3 * q4 - ay;
        f3 = 1.0f - _2q2 * q2 - _2q3 * q3 - az;
        J_11or24 = _2q3;
        J_12or23 = _2q4;
        J_13or22 = _2q1;
        J_14or21 = _2q2;
        J_32 = 2.0f * J_14or21;
        J_33 = 2.0f * J_11or24;

        // Compute the gradient (matrix multiplication)
        hatDot1 = J_14or21 * f2 - J_11or24 * f1;
        hatDot2 = J_12or23 * f1 + J_13or22 * f2 - J_32 * f3;
        hatDot3 = J_12or23 * f2 - J_33 * f3 - J_13or22 * f1;
        hatDot4 = J_14or21 * f1 + J_11or24 * f2;

        // Normalize the gradient
        norm = (double) sqrt(hatDot1 * hatDot1 + hatDot2 * hatDot2 + hatDot3 * hatDot3 + hatDot4 * hatDot4);
        hatDot1 /= norm;
        hatDot2 /= norm;
        hatDot3 /= norm;
        hatDot4 /= norm;

        // Compute estimated gyroscope biases
        gerrx = _2q1 * hatDot2 - _2q2 * hatDot1 - _2q3 * hatDot4 + _2q4 * hatDot3;
        gerry = _2q1 * hatDot3 + _2q2 * hatDot4 - _2q3 * hatDot1 - _2q4 * hatDot2;
        gerrz = _2q1 * hatDot4 - _2q2 * hatDot3 + _2q3 * hatDot2 - _2q4 * hatDot1;

        // Compute and remove gyroscope biases
        gbiasx += gerrx * deltat * zeta;
        gbiasy += gerry * deltat * zeta;
        gbiasz += gerrz * deltat * zeta;
        gx -= gbiasx;
        gy -= gbiasy;
        gz -= gbiasz;

        // Compute the quaternion derivative
        qDot1 = -_halfq2 * gx - _halfq3 * gy - _halfq4 * gz;
        qDot2 = _halfq1 * gx + _halfq3 * gz - _halfq4 * gy;
        qDot3 = _halfq1 * gy - _halfq2 * gz + _halfq4 * gx;
        qDot4 = _halfq1 * gz + _halfq2 * gy - _halfq3 * gx;

        // Compute then integrate estimated quaternion derivative
        q1 += (qDot1 - (beta * hatDot1)) * deltat;
        q2 += (qDot2 - (beta * hatDot2)) * deltat;
        q3 += (qDot3 - (beta * hatDot3)) * deltat;
        q4 += (qDot4 - (beta * hatDot4)) * deltat;

        // Normalize the quaternion
        norm = (double) sqrt(q1 * q1 + q2 * q2 + q3 * q3 + q4 * q4);    // normalise quaternion
        norm = 1.0f / norm;
        q[0] = q1 * norm;
        q[1] = q2 * norm;
        q[2] = q3 * norm;
        q[3] = q4 * norm;

        calculateYawRollPicth(ax, ay, az, gx, gy, gz);
    }

    public void calculateYawRollPicth(double ax, double ay, double az, double gx, double gy, double gz) {
        // Define output variables from updated quaternion---these are Tait-Bryan angles, commonly used in aircraft orientation.
        // In this coordinate system, the positive z-axis is down toward Earth.
        // Yaw is the angle between Sensor x-axis and Earth magnetic North (or true North if corrected for local declination),
        // looking down on the sensor positive yaw is counterclockwise.
        // Pitch is angle between sensor x-axis and Earth ground plane, toward the Earth is positive, up toward the sky is negative.
        // Roll is angle between sensor y-axis and Earth ground plane, y-axis up is positive roll.
        // These arise from the definition of the homogeneous rotation matrix constructed from quaternions.
        // Tait-Bryan angles as well as Euler angles are non-commutative; that is, to get the correct orientation the rotations must be
        // applied in the correct order which for this configuration is yaw, pitch, and then roll.
        // For more see http://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles which has additional links.
//        yaw = (double) atan2(2.0f * (q[1] * q[2] + q[0] * q[3]), q[0] * q[0] + q[1] * q[1] - q[2] * q[2] - q[3] * q[3]);
//        pitch = (double) -asin(2.0f * (q[1] * q[3] - q[0] * q[2]));
//        roll = (double) atan2(2.0f * (q[0] * q[1] + q[2] * q[3]), q[0] * q[0] - q[1] * q[1] - q[2] * q[2] + q[3] * q[3]);
//        pitch *= 180.0f / PI;
//        yaw *= 180.0f / PI;
//        yaw -= 13.8; // Declination at Danville, California is 13 degrees 48 minutes and 47 seconds on 2014-04-04
//        roll *= 180.0f / PI;


//        // Define output variables from updated quaternion---these are Tait-Bryan angles, commonly used in aircraft orientation.
//        // In this coordinate system, the positive z-axis is down toward Earth.
//        // Yaw is the angle between Sensor x-axis and Earth magnetic North (or true North if corrected for local declination, looking down on the sensor positive yaw is counterclockwise.
//        // Pitch is angle between sensor x-axis and Earth ground plane, toward the Earth is positive, up toward the sky is negative.
//        // Roll is angle between sensor y-axis and Earth ground plane, y-axis up is positive roll.
//        // These arise from the definition of the homogeneous rotation matrix constructed from quaternions.
//        // Tait-Bryan angles as well as Euler angles are non-commutative; that is, the get the correct orientation the rotations must be
//        // applied in the correct order which for this configuration is yaw, pitch, and then roll.
//        // For more see http://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles which has additional links.
//        yaw = atan2(2.0f * (q[1] * q[2] + q[0] * q[3]), q[0] * q[0] + q[1] * q[1] - q[2] * q[2] - q[3] * q[3]);
//        pitch = -asin(2.0f * (q[1] * q[3] - q[0] * q[2]));
//        roll = atan2(2.0f * (q[0] * q[1] + q[2] * q[3]), q[0] * q[0] - q[1] * q[1] - q[2] * q[2] + q[3] * q[3]);
////        pitch *= 180.0f / PI;
////        yaw *= 180.0f / PI;
////        roll *= 180.0f / PI;


//        int gravity = 1;
        // yaw: (about Z axis)
//        data[0] = atan2(2*q -> x*q -> y - 2*q -> w*q -> z, 2*q -> w*q -> w + 2*q -> x*q -> x - 1);
        yaw = atan2(2 * q[1] * q[2] - 2 * q[0] * q[3], 2 * q[0] * q[0] + 2 * q[1] * q[1] - 1);
        // pitch: (nose up/down, about Y axis)
//        data[1] = atan(gravity -> x / sqrt(gravity -> y*gravity -> y + gravity -> z*gravity -> z));
        pitch = atan(gx / sqrt(gy * gy + gz + gz));
        // roll: (tilt left/right, about X axis)
//        data[2] = atan(gravity -> y / sqrt(gravity -> x*gravity -> x + gravity -> z*gravity -> z));
        roll = atan(gy / sqrt(gx * gx + gz + gz));

    }

}
