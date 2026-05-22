package frc.robot.subsystems.grabber;

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
import com.ctre.phoenix6.controls.VelocityDutyCycle;
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

import frc.robot.subsystems.grabber.GrabberConstants;

public class GrabberIOKraken implements GrabberIO {
    private final TalonFX m_leader = new TalonFX(GrabberConstants.kLeaderID);
    private final TalonFX m_follower = new TalonFX(GrabberConstants.kFollowerID);

    private final VoltageOut m_voltageRequest = new VoltageOut(0);
    private final VelocityTorqueCurrentFOC m_velocityRequest = new VelocityTorqueCurrentFOC(0).withSlot(0);
    
    private final Follower m_followerRequest = new Follower(GrabberConstants.kLeaderID, GrabberConstants.kFollowerAlignment);

    // status signals
    private final StatusSignal<Voltage> m_leadVoltageSignal;
    private final StatusSignal<AngularVelocity> m_leadVelocitySignal;
    private final StatusSignal<Current> m_leadCurrentSignal;
    private final StatusSignal<Current> m_leadStatorCurrentSignal;
    private final StatusSignal<Temperature> m_leadTemperatureSignal;
    private final StatusSignal<Voltage> m_followVoltageSignal;
    private final StatusSignal<AngularVelocity> m_followVelocitySignal;
    private final StatusSignal<Current> m_followCurrentSignal;
    private final StatusSignal<Current> m_followStatorCurrentSignal;
    private final StatusSignal<Temperature> m_followTemperatureSignal;

    public GrabberIOKraken() {
        TalonFXConfiguration m_config = new TalonFXConfiguration();

        m_config.MotorOutput.Inverted = GrabberConstants.kInverted;
        m_config.MotorOutput.NeutralMode = GrabberConstants.kNeutralMode;
        
        // voltage and current limits
        m_config.Voltage.PeakForwardVoltage = GrabberConstants.kPeakForwardVoltage;
        m_config.Voltage.PeakReverseVoltage = GrabberConstants.kPeakReverseVoltage;
        m_config.CurrentLimits.SupplyCurrentLimit = GrabberConstants.kSupplyCurrentLimit;
        m_config.CurrentLimits.SupplyCurrentLimitEnable = true;
        m_config.CurrentLimits.StatorCurrentLimit = GrabberConstants.kStatorCurrentLimit;
        m_config.CurrentLimits.SupplyCurrentLimitEnable = true;

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
    
        m_leader.getConfigurator().apply(m_config);
        m_follower.getConfigurator().apply(m_config);
        m_leader.optimizeBusUtilization();

        m_follower.setControl(m_followerRequest);
    }

    public void setVoltage(double volts){
        m_voltageRequest.withOutput(volts);
        m_leader.setControl(m_voltageRequest);
    }

    public void setVelocity(double radPerSec){
        m_velocityRequest.withVelocity(radPerSec);
        m_leader.setControl(m_velocityRequest);
    }
    
    public void updateInputs(GrabberIOInputs inputs){
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
            m_followVelocitySignal
        );

        inputs.appliedVoltage[1] = m_leadVoltageSignal.getValueAsDouble();
        inputs.appliedVoltage[2] = m_followVoltageSignal.getValueAsDouble();
        inputs.currentAmps[1] = m_leadCurrentSignal.getValueAsDouble();
        inputs.currentAmps[2] = m_followCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[1] = m_leadStatorCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[2] = m_followStatorCurrentSignal.getValueAsDouble();
        inputs.motorTempDegreesC[1] = m_leadTemperatureSignal.getValueAsDouble();
        inputs.motorTempDegreesC[2] = m_followTemperatureSignal.getValueAsDouble();
        inputs.velocityRadiansPerSecond[1] =(m_leadVelocitySignal.getValueAsDouble()) * (2.0 * Math.PI);
        inputs.velocityRadiansPerSecond[2] =(m_followVelocitySignal.getValueAsDouble()) * (2.0 * Math.PI);
    }

    @Override
    public void stop() {
        this.setVoltage(0);
    }
}
