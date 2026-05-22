// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.SystemConstants;
import frc.robot.Constants.SystemConstants.RobotMode;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.elevator.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorIODisabled;
import frc.robot.subsystems.elevator.ElevatorIOKraken;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.grabber.GrabberIODisabled;
import frc.robot.subsystems.grabber.GrabberIOKraken;
import frc.robot.subsystems.grabber.GrabberSubsystem;
import frc.robot.subsystems.pivot.PivotIODisabled;
import frc.robot.subsystems.pivot.PivotIOKraken;
import frc.robot.subsystems.pivot.PivotSubsystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // the robot's subsystems and commands are defined here...
  private final ElevatorSubsystem m_elevator;
  private final GrabberSubsystem m_grabber;
  private final PivotSubsystem m_pivot;
  private final Superstructure m_superstructure;

  // controller
  private final CommandXboxController m_driverController = new CommandXboxController(Constants.ControllerConstants.kDriverControllerPort);
  
  // The container for the robot. Contains subsystems, OI devices, and commands.
  public RobotContainer() {
    if (SystemConstants.kMode == RobotMode.REAL || RobotBase.isReal()) {
      // using real io layers on real robot
      this.m_elevator = new ElevatorSubsystem(new ElevatorIOKraken());
      this.m_grabber = new GrabberSubsystem(new GrabberIOKraken());
      this.m_pivot = new PivotSubsystem(new PivotIOKraken());
    }
    else {
      // use sim/disabled on not real robot
      this.m_elevator = new ElevatorSubsystem(new ElevatorIODisabled());
      this.m_grabber = new GrabberSubsystem(new GrabberIODisabled());
      this.m_pivot = new PivotSubsystem(new PivotIODisabled());
    }

    m_superstructure = new Superstructure(m_elevator, m_grabber, m_pivot);

    configureBindings();

    configureDriverBindings();

    configureOperatorBindings();

    //m_superstructure.configureStateBasedBindings();
    //registerNamedCommands();
    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  private void configureDriverBindings(){

  }

  private void configureOperatorBindings(){
    
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
