package org.whitneyrobotics.ftc.teamcode.lib.purepursuit;


import com.acmerobotics.dashboard.config.Config;
import org.whitneyrobotics.ftc.teamcode.lib.control.ControlConstants;

@Config
public class PurePursuitRobotConstants {

    public final static double MAX_ACCELERATION = 800.0;

    public  static double SWERVE_KP = 0.0001;
    public  static double SWERVE_KV = 0.0006338;
    public  static double SWERVE_KA = 0.0012;

    public final static double STRAFE_KP = 0, STRAFE_KV = 0, STRAFE_KA = 0;

    public final static double hKP = 0, hKI = 0, hKD = 0;
    public final static ControlConstants STRAFE_HEADING_CONSTANTS = new ControlConstants(hKP, hKI, hKD);

}
