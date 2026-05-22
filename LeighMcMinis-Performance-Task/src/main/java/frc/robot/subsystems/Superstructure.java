package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.AutoLogOutput;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.grabber.GrabberConstants;
import frc.robot.subsystems.grabber.GrabberSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class Superstructure {
    

    public class SuperstructureStates {
        public enum elevatorState{
            
        };

        public enum grabberState {
            INTAKE,
            HOLD,
            RELEASE
        };

        public enum pivotState{
            
        };
    }

    public record SuperstructureParts(double heightMeters, double pivotRadians, double rollerVolts) {
        public boolean rollersActive() {
            return Math.abs(rollerVolts) > 0.05;
        }
    }

    public enum State {
        L1 (new SuperstructureParts(1, (Math.PI / 6), GrabberConstants.kPeakForwardVoltage / 3)),
        L2 (new SuperstructureParts(2, -(Math.PI / 6), GrabberConstants.kPeakForwardVoltage / 3)),
        L3 (new SuperstructureParts(3, -(Math.PI / 6), GrabberConstants.kPeakForwardVoltage / 3)),
        L4 (new SuperstructureParts(4, -(Math.PI / 3), GrabberConstants.kPeakForwardVoltage / 3)),
        Human_Player_Intake(new SuperstructureParts(1.5, (Math.PI / 3), GrabberConstants.kPeakForwardVoltage / 3)),
        
    }


    
}
