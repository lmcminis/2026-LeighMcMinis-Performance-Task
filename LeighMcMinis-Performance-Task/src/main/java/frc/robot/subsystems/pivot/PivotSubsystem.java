package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

public class PivotSubsystem extends SubsystemBase{
    private final PivotIO io;
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
    private final SysIdRoutine sysId;

    public PivotSubsystem(PivotIO io) {
        this.io = io;

        this.sysId = new SysIdRoutine(
            new SysIdRoutine.Config(
            null, Volts.of(1), null,
            (state) -> Logger.recordOutput("Pivot/SysIdState", state.toString())
            ),

            new SysIdRoutine.Mechanism(
                (voltage) -> io.setVoltage(voltage.in(Volts)),
                (log) ->
                    log.motor("Pivot Kraken")
                        .voltage(Volts.of(inputs.appliedVoltage))
                        .angularPosition(Radians.of(inputs.angleRadians))
                        .angularVelocity(RadiansPerSecond.of(inputs.velocityRadiansPerSecond)),
                this
            )
        );
    }

    @Override
    public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Pivot", inputs);
    }

    public double getAngleRadians() {
        return inputs.angleRadians;
    }

    public double getVelocity() {
        return inputs.velocityRadiansPerSecond;
    }

    public Command setVoltage(double volts) {
        return Commands.runOnce(
            () -> io.setVoltage(volts),
            this
        );
    }

    public Command setAngle(double radians) {
        return Commands.runOnce(
            () -> io.setAngleRadians(MathUtil.clamp(radians, PivotConstants.kBackwardPositionLimit, PivotConstants.kForwardPositionLimit)),
            this
        );
    }

    public Command stop() {
        return setVoltage(0);
    }

    public Command sysIdQuasistatic(Direction direction) {
        return sysId.quasistatic(direction);
    }

    public Command sysIdDynamic(Direction direction) {
        return sysId.dynamic(direction);
    }
    
}
