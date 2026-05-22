package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ElevatorConstants {
    public static final int kLeaderID = 0;
    public static final int kFollowerID = 0;

    public static final MotorAlignmentValue kFollowerAlignment = MotorAlignmentValue.Opposed;
    //public static final CANBus kCANBus = CANConstants.kRioBus;

    // soft limits
    public static final double kForwardPositionLimit = 0; // TODO: CALCULATE
    public static final double kBackwardPositionLimit = 0; // TODO: CALCULATE

    // voltage
    public static final double kPeakForwardVoltage = 0; 
    public static final double kPeakReverseVoltage = 0;

    // values
    public static final InvertedValue kInverted = InvertedValue.Clockwise_Positive; // depends on orientation
    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Brake;

    // current limits
    public static final int kSupplyCurrentLimit = 0;
    public static final int kStatorCurrentLimit = 0;

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
    
    // cascade elevator calculations
    public static final double kSensorToMechanismRatio = 0;
    //public static final double kVelocityConversionFactor = 2 * Math.PI / kSensorToMechanismRatio / 60.0;
    public static final double kDrumPitchDiameter = 0;
    public static final double kStages = 3; // three stage elevator
    public static final double kMetersPerDrumRotation = kDrumPitchDiameter * Math.PI * kStages;
}
