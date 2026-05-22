package frc.robot.subsystems.grabber;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.right;
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
    private final TalonFX m_left = new TalonFX(GrabberConstants.kLeftID, GrabberConstants.kCANBus);
    private final TalonFX m_right = new TalonFX(GrabberConstants.kRightID, GrabberConstants.kCANBus);

    private final VoltageOut m_LeftVoltageRequest = new VoltageOut(0);
    private final VelocityTorqueCurrentFOC m_LeftVelocityRequest = new VelocityTorqueCurrentFOC(0).withSlot(0);

    private final VoltageOut m_RightVoltageRequest = new VoltageOut(0);
    private final VelocityTorqueCurrentFOC m_RightVelocityRequest = new VelocityTorqueCurrentFOC(0).withSlot(0);
    
    // status signals
    private final StatusSignal<Voltage> m_leftVoltageSignal;
    private final StatusSignal<AngularVelocity> m_leftVelocitySignal;
    private final StatusSignal<Current> m_leftCurrentSignal;
    private final StatusSignal<Current> m_leftStatorCurrentSignal;
    private final StatusSignal<Temperature> m_leftTemperatureSignal;
    private final StatusSignal<Voltage> m_rightVoltageSignal;
    private final StatusSignal<AngularVelocity> m_rightVelocitySignal;
    private final StatusSignal<Current> m_rightCurrentSignal;
    private final StatusSignal<Current> m_rightStatorCurrentSignal;
    private final StatusSignal<Temperature> m_rightTemperatureSignal;

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

        m_leftVoltageSignal = m_left.getMotorVoltage();
        m_rightVoltageSignal = m_right.getMotorVoltage();
        m_leftCurrentSignal = m_left.getTorqueCurrent();
        m_leftStatorCurrentSignal = m_left.getStatorCurrent();
        m_rightCurrentSignal = m_right.getTorqueCurrent();
        m_rightStatorCurrentSignal = m_right.getStatorCurrent();
        m_leftTemperatureSignal = m_left.getDeviceTemp();
        m_rightTemperatureSignal = m_right.getDeviceTemp();
        m_leftVelocitySignal = m_left.getVelocity();
        m_rightVelocitySignal = m_right.getVelocity();
    
        m_left.getConfigurator().apply(m_config);
        m_right.getConfigurator().apply(m_config.MotorOutput.withInverted(GrabberConstants.kOppositeInverted));
        m_left.optimizeBusUtilization();
        m_right.optimizeBusUtilization();
    }

    public void setLeftVoltage(double volts){
        m_LeftVoltageRequest.withOutput(volts);
        m_left.setControl(m_LeftVoltageRequest);
    }

    public void setLeftVelocity(double radPerSec) {
        if (radPerSec == 0) {
            m_left.setVoltage(0);
        } else {
            m_LeftVelocityRequest.withVelocity(radPerSec);
            m_left.setControl(m_LeftVelocityRequest);
        }
    }

    public void setRightVoltage(double volts){
        m_RightVoltageRequest.withOutput(volts);
        m_right.setControl(m_RightVoltageRequest);
    }

    public void setRightVelocity(double radPerSec) {
        if (radPerSec == 0) {
            m_right.setVoltage(0);
        } else {
            m_RightVelocityRequest.withVelocity(radPerSec);
            m_right.setControl(m_RightVelocityRequest);
        }
    }
    
    public void updateInputs(GrabberIOInputs inputs){
        StatusSignal.refreshAll(
            m_leftVoltageSignal,
            m_rightVoltageSignal,
            m_leftCurrentSignal,
            m_leftStatorCurrentSignal,
            m_rightCurrentSignal,
            m_rightStatorCurrentSignal,
            m_leftTemperatureSignal,
            m_rightTemperatureSignal,
            m_leftVelocitySignal,
            m_rightVelocitySignal
        );

        inputs.appliedVoltage[0] = m_leftVoltageSignal.getValueAsDouble();
        inputs.appliedVoltage[1] = m_rightVoltageSignal.getValueAsDouble();
        inputs.currentAmps[0] = m_leftCurrentSignal.getValueAsDouble();
        inputs.currentAmps[1] = m_rightCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[0] = m_leftStatorCurrentSignal.getValueAsDouble();
        inputs.statorCurrentAmps[1] = m_rightStatorCurrentSignal.getValueAsDouble();
        inputs.motorTempDegreesC[0] = m_leftTemperatureSignal.getValueAsDouble();
        inputs.motorTempDegreesC[1] = m_rightTemperatureSignal.getValueAsDouble();
        inputs.velocityRadiansPerSecond[0] =(m_leftVelocitySignal.getValueAsDouble()) * (2.0 * Math.PI);
        inputs.velocityRadiansPerSecond[1] =(m_rightVelocitySignal.getValueAsDouble()) * (2.0 * Math.PI);
    }

    @Override
    public void stop() {
        this.setLeftVoltage(0);
        this.setRightVoltage(0);
    }
}
