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

    public double getStatorCurrent() {
        return inputs.statorCurrentAmps[1];
    }

    public double getVelocity() {
        return inputs.velocityRadiansPerSecond[1];
    }

    public void setVoltage(double volts) {
        inputs.appliedVoltage[1] = volts;
        inputs.appliedVoltage[2] = volts;
    }

    //public Command intakeCommand() {
    //    return run(() -> io.setVoltage(GrabberConstants.kIntakeVoltage)).finallyDo(interrupted -> io.stop());
    //}

    //public Command holdCommand() {
    //    return run(() -> io.setVoltage(GrabberConstants.kHoldVoltage)).finallyDo(interrupted -> io.stop());
    //}

    //public Command releaseCommand() {
    //    return run(() -> io.setVoltage(GrabberConstants.kReleaseVoltage)).withTimeout(0.3).andThen(runOnce (io::stop));
    //}
    
}
