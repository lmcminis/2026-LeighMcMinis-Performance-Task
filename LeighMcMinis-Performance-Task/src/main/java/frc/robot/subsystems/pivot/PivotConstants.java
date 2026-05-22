package frc.robot.subsystems.pivot;

public class PivotConstants {
    // CAN ID
    public static final int kMotorID = 0;
    public static final int kCANcoderID = 0;

    // cancoder
    public static final double kCANcoderMagnetOffset = 0; // find using physical robot
    
    public static final double kMotorToPivotRatio = 0;  // gear ratio, cancoder ticks to radians
    public static final double kForwardPositionLimit = 0.25; // should not be greater than 0.25
    public static final double kBackwardPositionLimit = -0.25; // should not be less than -0.25
    public static final double kSensorToMechanismRatio = 1; // cancoder is directly on the pivot, so 1:1 ratio

    // magic motion
    public static final double MotionMagicCruiseVelocity = 0; // target cruise velocity
    public static final double MotionMagicJerk = 0; // target jerk
    public static final double MotionMagicAcceleration = 0; // target acceleration

    public static final double kS = 0; // to be tuned
    public static final double kV = 0;
    public static final double kG = 0; // gravity
    public static final double kA = 0; 
    public static final double kP = 0; // pids
    public static final double kI = 0;
    public static final double kD = 0;
}
