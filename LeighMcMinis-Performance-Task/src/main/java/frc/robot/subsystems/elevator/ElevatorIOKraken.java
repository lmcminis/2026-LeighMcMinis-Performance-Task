package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.subsystems.elevator.ElevatorConstants;

public class ElevatorIOKraken implements ElevatorIO{
    private final TalonFX leader = new TalonFX(ElevatorConstants.kLeaderID);
    private final TalonFX follower = new TalonFX(ElevatorConstants.kFollowerID);

    private final MotionMagicVoltage positionRequest = new MotionMagicVoltage(0).withSlot(0);
    private final VoltageOut voltageRequest = new VoltageOut(0);

    public ElevatorIOKraken() {
        TalonFXConfiguration config = new TalonFXConfiguration();

    }

    public void updateInputs(ElevatorIOInputs inputs){

    }

    public void setPosition(double heightMeters){

    }

    public void setVoltage(double appliedVoltage){

    }

    public void stop(){
        
    }
}