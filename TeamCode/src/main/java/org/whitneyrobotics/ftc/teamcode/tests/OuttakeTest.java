package org.whitneyrobotics.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.whitneyrobotics.ftc.teamcode.lib.util.Toggler;
import org.whitneyrobotics.ftc.teamcode.subsys.Outtake;

@TeleOp (name="Outtake Test", group="Tests")
public class OuttakeTest extends OpMode {

    public Outtake outtake;
    public double power = 1;
    public double servoPosition;
    FtcDashboard dashboard;
    //Telemetry dashboardTelemetry;
    TelemetryPacket packet = new TelemetryPacket();
    private Toggler modeTog = new Toggler(2);

    @Override
    public void init() {
        outtake = new Outtake(hardwareMap);
        outtake.linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtake.linearSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtake.linearSlides.setDirection(DcMotorSimple.Direction.REVERSE);
        servoPosition = outtake.gate.getPosition();
    }

    public void kill() {
        outtake.linearSlides.setPower(0);
        System.exit(69);
    }

    @Override
    public void loop() {
        modeTog.changeState(gamepad1.x);
        if(modeTog.currentState() == 1){
            outtake.togglerOuttake(gamepad1.a, gamepad1.b);
        }else {
            if (gamepad1.dpad_up) {
                outtake.linearSlides.setPower(power);
            }
            else if (gamepad1.dpad_down) {
                outtake.linearSlides.setPower(-power);
            }
            else {
                outtake.linearSlides.setPower(0);
            }
        }

        if (gamepad1.dpad_left){
            servoPosition --;
            servoPosition = servoPosition<0 ? 1 : servoPosition;
            outtake.gate.setPosition(servoPosition);
        } else if(gamepad1.dpad_right){
            servoPosition++;
            servoPosition = servoPosition>1 ? 0 : servoPosition;
            outtake.gate.setPosition(servoPosition);
        }

        if(gamepad1.left_bumper){
            outtake.level1 =  outtake.linearSlides.getCurrentPosition();
            gamepad1.rumble(250);
        }
        else if (gamepad1.right_bumper) {
            outtake.level2 = outtake.linearSlides.getCurrentPosition();
            gamepad1.rumble(250);
        }
        else if (gamepad1.right_trigger >= 0.01) {
            outtake.level3 = outtake.linearSlides.getCurrentPosition();
            gamepad1.rumble(250);
        }

        //outtake.togglerOuttake(gamepad1.b, gamepad1.a);
        if (gamepad1.x) { outtake.reset(); }

        //emergency kill switch
        if(gamepad1.left_trigger > 0.99){kill();}

        telemetry.addData("Mode", (modeTog.currentState() == 0) ? "Manual Configure Mode" : "Test Mode");
        telemetry.addData("Encoder Position", outtake.linearSlides.getCurrentPosition());
        telemetry.addData("Current Tier (0-2)", outtake.getTier());
        telemetry.addData("Level 1:", outtake.level1);
        telemetry.addData("Level 2:", outtake.level2);
        telemetry.addData("Level 3:", outtake.level3);

        packet.put("Encoder Position", outtake.linearSlides.getCurrentPosition());
        packet.put("Current Tier (0-2)",outtake.getTier());
        packet.put("Level 1:", outtake.level1);
        packet.put("Level 2:", outtake.level2);
        packet.put("Level 3", outtake.level3);
        //dashboard.sendTelemetryPacket(packet);
    }

}
