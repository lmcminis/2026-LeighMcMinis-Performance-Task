package frc.robot.subsystems.pivot;

public class PivotConstants {
    // CAN ID
    public static final int kMotorID = 0;
    public static final int kCANcoderID = 0;

    // cancoder
    public static final double kCANcoderMagnetOffset = 0; // find using physical robot
    
    public static final double kMotorToPivotRatio = 0;
    public static final double kForwardPositionLimit = 0; // should be less than 0.5
    public static final double kBackwardPositionLimit = 0; // should be greater than -0.5
    public static final double kSensorToMechanismRatio = 1; // cancoder is directly on the pivot, so 1:1 ratio

    public static final double kS = 0;
    public static final double kV = 0;
    public static final double kG = 0;
    public static final double kA = 0;
    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;
}
