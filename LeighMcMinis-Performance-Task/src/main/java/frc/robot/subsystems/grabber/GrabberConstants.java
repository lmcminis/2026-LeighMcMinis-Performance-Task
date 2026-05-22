package frc.robot.subsystems.grabber;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class GrabberConstants {
    public static final int kLeaderID = 0;
    public static final int kFollowerID = 0;

    public static final MotorAlignmentValue kFollowerAlignment = MotorAlignmentValue.Opposed;
    //public static final CANBus kCANBus = CANConstants.kRioBus;

    // voltage
    public static final double kPeakForwardVoltage = 0; 
    public static final double kPeakReverseVoltage = 0;
    public static final double holdVoltage = 0;
    public static final double kIntakeVoltage = 0;
    public static final double kHoldVoltage = 0;
    public static final double kReleaseVoltage = 0;

    // values
    public static final InvertedValue kInverted = InvertedValue.Clockwise_Positive; // depends on orientation
    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Brake;

    // current limits
    public static final int kSupplyCurrentLimit = 0;
    public static final int kStatorCurrentLimit = 0;
    
}
