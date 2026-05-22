package frc.robot.subsystems.elevator;

public class ElevatorConstants {
    public static final int kLeaderID = 0;
    public static final int kFollowerID = 0;
    //public static final CANBus kCANBus = CANConstants.kRioBus;

    public static final double kSupplyCurrentLimit = 40;
    public static final double kStatorCurrentLimit = 40;

    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;

    public static final double kV = 0;
    public static final double kS = 0;
    public static final double kG = 0;
    public static final double kA = 0;
    
    public static final double kGearRatio = 0;
    public static final double kVelocityConversionFactor = 2 * Math.PI / kGearRatio / 60.0;
}
