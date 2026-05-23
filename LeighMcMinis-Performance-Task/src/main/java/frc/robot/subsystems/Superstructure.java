package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.AutoLogOutput;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.elevator.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.grabber.GrabberConstants;
import frc.robot.subsystems.grabber.GrabberSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class Superstructure {

    private final ElevatorSubsystem m_elevator;
    private final GrabberSubsystem m_grabber;
    private final PivotSubsystem m_pivot;

    public Superstructure(ElevatorSubsystem elevator, GrabberSubsystem grabber, PivotSubsystem pivot) {
        this.m_elevator = elevator;
        this.m_grabber = grabber;
        this.m_pivot = pivot;

    }

    public ElevatorSubsystem getElevatorSubsystem() {
        return m_elevator;
    }

    public GrabberSubsystem getGrabberSubsystem() {
        return m_grabber;
    }

    public PivotSubsystem getPivotSubsystem() {
        return m_pivot;
    }

    public enum States {
        L1,
        L2,
        L3,
        L4,
        Intake,
        Throw
    }

    public Command L1() {
        return Commands.sequence(
            m_pivot.setAngle(Math.PI / 6.0),
            m_elevator.setPosition(1),
            m_grabber.releaseCommand(4, -4)
        );
    }

    public Command L2() {
        return Commands.sequence(
            m_elevator.setPosition(2),
            m_pivot.setAngle(-1 * (Math.PI / 6.0)),
            m_grabber.releaseCommand(4,4)
        );
    }

    public Command L3() {
        return Commands.sequence(
            m_elevator.setPosition(3),
            m_pivot.setAngle(-1 * (Math.PI / 6.0)),
            m_grabber.releaseCommand(4,4)
        );
    }

    public Command L4() {
        return Commands.sequence(
            m_elevator.setPosition(4),
            m_pivot.setAngle(-1 * (Math.PI / 3.0)),
            m_grabber.releaseCommand(4,4)
        );
    }

    public Command Intake() {

        return Commands.parallel(
            m_elevator.setPosition(1.5),
            m_pivot.setAngle(Math.PI / 3),
            m_grabber.holdCommand(-6, -6) // add timer
        );
    
    }

    public Command Throw() {

        if (m_elevator.getPosition() < 1) {
            return Commands.parallel(
                m_pivot.setAngle((2 * Math.PI) / 3.0),
                m_elevator.setPosition(ElevatorConstants.kForwardPositionLimit),
                Commands.waitUntil(() -> (m_elevator.getVelocity() > 2.0 && m_elevator.getPosition() > 3.5))
                .andThen(m_grabber.releaseCommand(12,12))
            );
        }
        else {
            return Commands.sequence(
                m_elevator.setPosition(1),
                Commands.parallel(
                    m_pivot.setAngle((2 * Math.PI) / 3.0),
                    m_elevator.setPosition(ElevatorConstants.kForwardPositionLimit),
                    Commands.waitUntil(() -> (m_elevator.getVelocity() > 2.0 && m_elevator.getPosition() > 3.5))
                    .andThen(m_grabber.releaseCommand(12,12))
                )
            );
        }
    }
}
