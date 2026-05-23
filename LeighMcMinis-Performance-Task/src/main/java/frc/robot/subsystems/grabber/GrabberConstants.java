package frc.robot.subsystems.grabber;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants;

public class GrabberConstants {
    public static final int kLeftID = 0;
    public static final int kRightID = 0;
    public static final CANBus kCANBus = Constants.CANConstants.kRioBus;

    // voltage
    public static final double kPeakForwardVoltage = 12;
    public static final double kPeakReverseVoltage = -12;
    public static final double kIntakeVoltage = 0;
    public static final double kHoldVoltage = 0;
    public static final double kReleaseVoltage = 0;

    // values
    public static final InvertedValue kInverted = InvertedValue.Clockwise_Positive; // depends on orientation
    public static final InvertedValue kOppositeInverted = InvertedValue.CounterClockwise_Positive; // always opposite of whatever is above for second motor
    public static final NeutralModeValue kNeutralMode = NeutralModeValue.Brake;

    // current limits
    public static final int kSupplyCurrentLimit = 0;
    public static final int kStatorCurrentLimit = 0;
    
}
