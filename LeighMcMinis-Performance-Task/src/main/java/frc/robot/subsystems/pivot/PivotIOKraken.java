package frc.robot.subsystems.pivot;

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

public class PivotIOKraken implements PivotIO {
    // creation
    private final TalonFX m_motor = new TalonFX(PivotConstants.kMotorID);
    private final CANcoder m_cancoder = new CANcoder(PivotConstants.kCANcoderID);

    // control requests 
    private final MotionMagicVoltage m_positionRequest = new MotionMagicVoltage(0).withSlot(0);
    private final VoltageOut m_voltageRequest = new VoltageOut(0);

    // status signals
    private final StatusSignal<Voltage> m_motorVoltageSignal;
    private final StatusSignal<Current> m_motorCurrentSignal;
    private final StatusSignal<Temperature> m_motorTemperatureSignal;
    private final StatusSignal<AngularVelocity> m_velocitySignal;
    private final StatusSignal<Angle> m_motorPositionSignal;

    public PivotIOKraken() {
        // cancoder plus config
        CANcoderConfiguration m_cancoderConfig = new CANcoderConfiguration(); // set cancoder so horizontal axis becomes zero line for rotations
        m_cancoderConfig.MagnetSensor.AbsoluteSensorDiscontinuityPoint = 0.5; // 0.5 rotations becomes max number, now goes from -0.5 to 0.5
        m_cancoderConfig.MagnetSensor.withSensorDirection(SensorDirectionValue.CounterClockwise_Positive); // makes positive direction up. assuming motor on right?
        m_cancoderConfig.MagnetSensor.MagnetOffset = PivotConstants.kCANcoderMagnetOffset; // needs physical mechanism to zero sensor
        m_cancoder.getConfigurator().apply(m_cancoderConfig);

        // motor plus config

        TalonFXConfiguration m_config = new TalonFXConfiguration();
        m_config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder; // only free cancoder combo option
        m_config.Feedback.FeedbackRemoteSensorID = PivotConstants.kCANcoderID;
        m_config.Feedback.SensorToMechanismRatio = PivotConstants.kSensorToMechanismRatio; // ratio of axle turns to sensor turns, one
        m_config.Feedback.RotorToSensorRatio = PivotConstants.kMotorToPivotRatio; // based on gearbox, how many motor rotations make one pivot rotation

        // feedforward!! (we all scream in unison)
        m_config.Slot0.kS = PivotConstants.kS;
        m_config.Slot0.kV = PivotConstants.kV; 
        m_config.Slot0.kA = PivotConstants.kA;
        m_config.Slot0.kG = PivotConstants.kG;
        m_config.Slot0.kP = PivotConstants.kP;
        m_config.Slot0.kI = PivotConstants.kI;
        m_config.Slot0.kD = PivotConstants.kD;
        m_config.Slot0.GravityType = GravityTypeValue.Arm_Cosine;

        // soft limits / don't brek
        m_config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        m_config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = PivotConstants.kForwardPositionLimit;
        m_config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        m_config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = PivotConstants.kBackwardPositionLimit;

        // status signal setup

        m_motorVoltageSignal = m_motor.getMotorVoltage();
        m_motorCurrentSignal = m_motor.getTorqueCurrent();
        m_motorTemperatureSignal = m_motor.getDeviceTemp();
        m_velocitySignal = m_motor.getVelocity();
        m_motorPositionSignal = m_motor.getPosition();
    
        // set motion magic settings
        var motionMagicConfigs = m_config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = PivotConstants.MotionMagicCruiseVelocity;
        motionMagicConfigs.MotionMagicAcceleration = PivotConstants.MotionMagicAcceleration;
        motionMagicConfigs.MotionMagicJerk = PivotConstants.MotionMagicJerk; 

        m_motor.getConfigurator().apply(m_config);
    }

    @Override
    public void setAngleRadians(double angleRadians) {
        m_positionRequest.withPosition(angleRadians / (2.0 * Math.PI)); // uses magic motion *sparkles*
        m_motor.setControl(m_positionRequest);

    }

    @Override
    public void setVoltage(double volts) {
        // really only used for stop
        m_voltageRequest.withOutput(volts);
        m_motor.setControl(m_voltageRequest);

    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        StatusSignal.refreshAll(
            m_motorCurrentSignal,
            m_motorTemperatureSignal,
            m_motorVoltageSignal,
            m_velocitySignal
        );
        
        inputs.appliedVoltage = m_motorVoltageSignal.getValueAsDouble();
        inputs.currentAmps = m_motorCurrentSignal.getValueAsDouble();
        inputs.angleRadians = m_motorPositionSignal.getValueAsDouble();
        inputs.velocityRadiansPerSecond = m_velocitySignal.getValueAsDouble() * (2.0 * Math.PI);
        inputs.motorTempDegreesC = m_motorTemperatureSignal.getValueAsDouble();
    }

}
