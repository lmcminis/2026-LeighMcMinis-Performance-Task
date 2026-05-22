package frc.robot.subsystems.grabber;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GrabberSubsystem extends SubsystemBase{
    private final GrabberIO io;
    private final GrabberIOInputsAutoLogged inputs = new GrabberIOInputsAutoLogged();

    public GrabberSubsystem(GrabberIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Grabber", inputs);
    }

    public double getLeftStatorCurrent() {
        return inputs.statorCurrentAmps[0];
    }

    public double getRightStatorCurrent() {
        return inputs.statorCurrentAmps[1];
    }

    public double getLeftVelocity() {
        return inputs.velocityRadiansPerSecond[0];
    }

    public double getRightVelocity() {
        return inputs.velocityRadiansPerSecond[1];
    }

    public void setLeftVoltage(double volts) {
        inputs.appliedVoltage[0] = volts;
    }

    public void setRightVoltage(double volts) {
        inputs.appliedVoltage[1] = volts;
    }

    public Command intakeCommand() {
        return run(() -> { 
        io.setLeftVoltage(GrabberConstants.kIntakeVoltage);
        io.setRightVoltage(GrabberConstants.kIntakeVoltage);
        }).finallyDo(interrupted -> io.stop());
    }

    public Command holdCommand() {
        return run(() -> { 
        io.setLeftVoltage(GrabberConstants.kHoldVoltage);
        io.setRightVoltage(GrabberConstants.kHoldVoltage);
        }).finallyDo(interrupted -> io.stop());
    }

    public Command releaseCommand() {
        return run(() -> { 
        io.setLeftVoltage(GrabberConstants.kReleaseVoltage);
        io.setRightVoltage(GrabberConstants.kReleaseVoltage);
        }).withTimeout(0.3).andThen(runOnce (io::stop));
    }
    
}
