package org.whitneyrobotics.ftc.teamcode.subsys;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.whitneyrobotics.ftc.teamcode.lib.util.Functions;
import org.whitneyrobotics.ftc.teamcode.lib.util.Queue.RobotAction;

/**
 * Created by Jason on 10/30/2017.
 */


public class IMU {

    private double imuBias = 0;
    private double calibration = 0;
    private final double Z_ACCEL_THRESHOLD = 6;

    BNO055IMU imu;
    BNO055IMU.Parameters parameters;

    public IMU(HardwareMap theMap) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();


        imu = theMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }



    public double[] getThreeHeading()
    {
        double xheading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).firstAngle;// - calibration;
        double yheading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).secondAngle;// - calibration;
        double zheading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).thirdAngle; //- calibration;

        double[] threeHeading = {xheading,yheading,zheading};
        return threeHeading; // -180 to 180 deg
    }

    public double getHeading(){
        double heading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).thirdAngle - calibration;
        heading = Functions.normalizeAngle(heading); // -180 to 180 deg
        return heading;
    }
    public void zeroHeading(){
        calibration = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ).thirdAngle ;
    }

    // Returns the magnitude of the acceleration, not the direction.
    public double getAccelerationMag(){
        double xAccel = imu.getLinearAcceleration().xAccel;
        double yAccel = imu.getLinearAcceleration().yAccel;
        double zAccel = imu.getLinearAcceleration().zAccel;

        double accelMag =
                Math.sqrt(
                        Math.pow( xAccel, 2 ) + Math.pow( yAccel, 2 ) + Math.pow( zAccel, 2 )
                );
        return accelMag;
    }

    // Returns the linear acceleration in the z direction
    public double getZAcceleration() {
        return imu.getLinearAcceleration().zAccel;
    }

    public double getYAcceleration() {
        return imu.getLinearAcceleration().yAccel;
    }

    // Determines if the linear acceleration in the z direction is over the threshold
    public boolean exceedZAccelThreshold() {
        double zAccel = imu.getLinearAcceleration().zAccel;
        if (zAccel > Z_ACCEL_THRESHOLD) {
            return true;
        }
        return false;
    }

    public double getAngularVelocity() {
        return imu.getAngularVelocity().zRotationRate;
    }

    public void setImuBias(double vuforiaHeading){
        imuBias = Functions.normalizeAngle(vuforiaHeading - getHeading()); // -180 to 180 deg
    }

    public double getImuBias() {
        return imuBias;
    }

    public boolean hasError(){
        return imu.getSystemError().bVal != 0;
    }
}

