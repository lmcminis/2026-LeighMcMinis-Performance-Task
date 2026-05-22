package frc.robot.subsystems.grabber;

import frc.robot.subsystems.grabber.GrabberIO.GrabberIOInputs;

public class GrabberIODisabled implements GrabberIO {
    private double voltageTarget = 0;
    private double velocityTarget = 0;

    @Override
    public void setVoltage(double volts){
        this.voltageTarget = volts;

    }

    @Override
    public void setVelocity(double radPerSec){
        this.velocityTarget = radPerSec;

    }
    
    @Override
    public void updateInputs(GrabberIOInputs inputs){
        inputs.appliedVoltage[1] = voltageTarget;
        inputs.appliedVoltage[2] = voltageTarget;
        inputs.velocityRadiansPerSecond[1] = velocityTarget;
        inputs.velocityRadiansPerSecond[2] = velocityTarget;

    }

    @Override
    public void stop() {
        this.setVoltage(0);
    }
    
}
