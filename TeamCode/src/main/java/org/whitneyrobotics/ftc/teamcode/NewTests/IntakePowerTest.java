package org.whitneyrobotics.ftc.teamcode.NewTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;

@TeleOp(name="Intake Power Test", group="Tests")
public class IntakePowerTest extends OpMode {
    private DcMotorEx surgicalTubes;
    private Toggler powerSelector = new Toggler(201);

    @Override
    public void init() {
        surgicalTubes = hardwareMap.get(DcMotorEx.class,"surgicalTubesMotor");
        surgicalTubes.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        powerSelector.setState(101); //0
    }

    @Override
    public void loop() {
        double power = 0;
        powerSelector.changeState(gamepad1.dpad_up,gamepad1.dpad_down);

        if(gamepad1.y){
            throw new RuntimeException("Emergency Stop");
        }

        if(gamepad1.b){
            surgicalTubes.setPower(0);
        } else {
            power = (powerSelector.currentState()-101)/100;
            surgicalTubes.setPower(power);
        }

        telemetry.addData("Motor Power",power);
        telemetry.addData("Motor Current in mA",surgicalTubes.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("Motor Velocity in Ticks/Sec",surgicalTubes.getVelocity());
        telemetry.addData("Motor velocity in RPM",surgicalTubes.getVelocity(AngleUnit.DEGREES)/360/60); //degress per second to rotations per minute
    }
}
