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

public class ElevatorIOKraken implements ElevatorIO{
    // creation
    private final TalonFX m_leader = new TalonFX(ElevatorConstants.kLeaderID, ElevatorConstants.kCANBus);
    private final TalonFX m_follower = new TalonFX(ElevatorConstants.kFollowerID, ElevatorConstants.kCANBus);

    // magic motion
    private final MotionMagicVoltage m_positionRequest = new MotionMagicVoltage(0).withSlot(0);
    private final VoltageOut m_voltageRequest = new VoltageOut(0);
    private final Follower m_followerRequest = new Follower(ElevatorConstants.kLeaderID, ElevatorConstants.kFollowerAlignment);

    // status signals
    private final StatusSignal<Voltage> m_leadVoltageSignal;
    private final StatusSignal<AngularVelocity> m_leadVelocitySignal;
    private final StatusSignal<Current> m_leadCurrentSignal;
    private final StatusSignal<Current> m_leadStatorCurrentSignal;
    private final StatusSignal<Temperature> m_leadTemperatureSignal;
    private final StatusSignal<Angle> m_leadPositionSignal;
    private final StatusSignal<Voltage> m_followVoltageSignal;
    private final StatusSignal<AngularVelocity> m_followVelocitySignal;
    private final StatusSignal<Current> m_followCurrentSignal;
    private final StatusSignal<Current> m_followStatorCurrentSignal;
    private final StatusSignal<Temperature> m_followTemperatureSignal;
    private final StatusSignal<Angle> m_followPositionSignal;

    public ElevatorIOKraken() {
        TalonFXConfiguration m_config = new TalonFXConfiguration();

        m_config.Feedback.SensorToMechanismRatio = ElevatorConstants.kSensorToMechanismRatio;
        m_config.MotorOutput.Inverted = ElevatorConstants.kInverted;
        m_config.MotorOutput.NeutralMode = ElevatorConstants.kNeutralMode;


        // feedforward!! (we all scream in unison)
        m_config.Slot0.kS = ElevatorConstants.kS;
        m_config.Slot0.kV = ElevatorConstants.kV; 
        m_config.Slot0.kA = ElevatorConstants.kA;
        m_config.Slot0.kG = ElevatorConstants.kG;
        m_config.Slot0.kP = ElevatorConstants.kP;
        m_config.Slot0.kI = ElevatorConstants.kI;
        m_config.Slot0.kD = ElevatorConstants.kD;
        m_config.Slot0.GravityType = GravityTypeValue.Elevator_Static;

        // voltage and current limits
        m_config.Voltage.PeakForwardVoltage = ElevatorConstants.kPeakForwardVoltage;
        m_config.Voltage.PeakReverseVoltage = -ElevatorConstants.kPeakReverseVoltage;
        m_config.CurrentLimits.SupplyCurrentLimit = ElevatorConstants.kSupplyCurrentLimit;
        m_config.CurrentLimits.SupplyCurrentLimitEnable = true;
        m_config.CurrentLimits.StatorCurrentLimit = ElevatorConstants.kStatorCurrentLimit;
        m_config.CurrentLimits.SupplyCurrentLimitEnable = true;


        // soft limits / don't brek
        m_config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        m_config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = ElevatorConstants.kForwardPositionLimit;
        m_config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        m_config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = ElevatorConstants.kBackwardPositionLimit;

        // status signal setup

        m_leadVoltageSignal = m_leader.getMotorVoltage();
        m_followVoltageSignal = m_follower.getMotorVoltage();
        m_leadCurrentSignal = m_leader.getTorqueCurrent();
        m_leadStatorCurrentSignal = m_leader.getStatorCurrent();
        m_followCurrentSignal = m_follower.getTorqueCurrent();
        m_followStatorCurrentSignal = m_follower.getStatorCurrent();
        m_leadTemperatureSignal = m_leader.getDeviceTemp();
        m_followTemperatureSignal = m_follower.getDeviceTemp();
        m_leadVelocitySignal = m_leader.getVelocity();
        m_followVelocitySignal = m_follower.getVelocity();
        m_leadPositionSignal = m_leader.getPosition();
        m_followPositionSignal = m_follower.getPosition();
    
        // set motion magic settings
        var motionMagicConfigs = m_config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = ElevatorConstants.MotionMagicCruiseVelocity;
        motionMagicConfigs.MotionMagicAcceleration = ElevatorConstants.MotionMagicAcceleration;
        motionMagicConfigs.MotionMagicJerk = ElevatorConstants.MotionMagicJerk; 

        m_leader.getConfigurator().apply(m_config);
        m_follower.getConfigurator().apply(m_config);
        m_leader.optimizeBusUtilization();

        m_follower.setControl(m_followerRequest);
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs){
        StatusSignal.refreshAll(
            m_leadVoltageSignal,
            m_followVoltageSignal,
            m_leadCurrentSignal,
            m_leadStatorCurrentSignal,
            m_followCurrentSignal,
            m_followStatorCurrentSignal,
            m_leadTemperatureSignal,
            m_followTemperatureSignal,
            m_leadVelocitySignal,
            m_followVelocitySignal,
            m_leadPositionSignal,
            m_followPositionSignal
        );

        inputs.appliedVoltage[0] = m_leadVoltageSignal.getValueAsDouble();
        inputs.appliedVoltage[1] = m_followVoltageSignal.getValueAsDouble();
        inputs.currentAmps[0] = m_leadCurrentSignal.getValueAsDouble();
        inputs.currentAmps[1] = m_followCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[0] = m_leadStatorCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[1] = m_followStatorCurrentSignal.getValueAsDouble();
        inputs.motorTempDegreesC[0] = m_leadTemperatureSignal.getValueAsDouble();
        inputs.motorTempDegreesC[1] = m_followTemperatureSignal.getValueAsDouble();
        inputs.velocityMetersPerSecond = rotationsToMeters(m_leadVelocitySignal.getValueAsDouble());
        inputs.positionMeters = rotationsToMeters(m_leadPositionSignal.getValueAsDouble());

    }

    @Override
    public void setPosition(double heightMeters){
        m_positionRequest.withPosition(heightMeters);
        m_leader.setControl(m_positionRequest);
    }

    @Override
    public void setVoltage(double volts){
        // really only used for stop
        m_voltageRequest.withOutput(volts);
        m_leader.setControl(m_voltageRequest);
    }

    @Override
    public void resetEncoder() { 
        m_leader.setPosition(0);
    }

    @Override
    public void stop(){
        setVoltage(0);
    }

    
    private static double rotationsToMeters(double rotations) { 
        return rotations * ElevatorConstants.kMetersPerDrumRotation; 
    }

    private static double metersToRotations(double meters) { 
        return meters  / ElevatorConstants.kMetersPerDrumRotation;
    }
}