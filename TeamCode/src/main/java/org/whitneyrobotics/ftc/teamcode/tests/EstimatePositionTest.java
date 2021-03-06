package org.whitneyrobotics.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.lib.geometry.Coordinate;
import org.whitneyrobotics.ftc.teamcode.subsys.WHSRobotImplOld;


@TeleOp(name = "Estimate Position Test")
public class EstimatePositionTest extends OpMode {

    WHSRobotImplOld robot;
    double maxAccel = 0;
    double currentAccel;

    @Override
    public void init() {
        robot = new WHSRobotImplOld(hardwareMap);
        robot.setInitialCoordinate(new Coordinate(0, 0, 90));
    }

    @Override
    public void loop() {


        robot.drivetrain.operateMecanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x,robot.getCoordinate().getHeading());
        //robot.estimateHeading();
        robot.estimatePosition();
        //robot.estimateCoordinate();
        robot.estimateHeading();
        //robot.deadWheelEstimateCoordinate();
        //robot.deadWheelPickup.setPosition(DeadWheelPickup.DeadWheelPickupPosition.DOWN);

        telemetry.addData("IMU", robot.imu.getHeading());
        telemetry.addData("X", robot.getCoordinate().getX());
        telemetry.addData("Y", robot.getCoordinate().getY());
        telemetry.addData("Heading", robot.getCoordinate().getHeading());
        telemetry.addData("FL Position", robot.drivetrain.frontLeft.getCurrentPosition());
        telemetry.addData("BL Position", robot.drivetrain.backLeft.getCurrentPosition());
        telemetry.addData("FR Position", robot.drivetrain.frontRight.getCurrentPosition());
        telemetry.addData("BR Position", robot.drivetrain.backRight.getCurrentPosition());
        telemetry.addData("X heading: ", robot.imu.getThreeHeading()[0]);
        telemetry.addData("Y heading: ", robot.imu.getThreeHeading()[1]);
        telemetry.addData("Z heading: ", robot.imu.getThreeHeading()[2]);

    }
}