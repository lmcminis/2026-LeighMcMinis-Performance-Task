package frc.robot.subsystems.grabber;

import frc.robot.subsystems.grabber.GrabberIO.GrabberIOInputs;

public class GrabberIODisabled implements GrabberIO {
    private double leftVoltageTarget = 0;
    private double rightVoltageTarget = 0;
    private double leftVelocityTarget = 0;
    private double rightVelocityTarget = 0;

    @Override
    public void setLeftVoltage(double volts){
        this.leftVoltageTarget = volts;
    }

    @Override
    public void setLeftVelocity(double radPerSec){
        this.leftVelocityTarget = radPerSec;
    }

    @Override
    public void setRightVoltage(double volts){
        this.rightVoltageTarget = volts;
    }

    @Override
    public void setRightVelocity(double radPerSec){
        this.rightVelocityTarget = radPerSec;
    }
    
    @Override
    public void updateInputs(GrabberIOInputs inputs){
        inputs.appliedVoltage[0] = leftVoltageTarget;
        inputs.appliedVoltage[1] = rightVoltageTarget;
        inputs.velocityRadiansPerSecond[0] = leftVelocityTarget;
        inputs.velocityRadiansPerSecond[1] = rightVelocityTarget;

    }

    @Override
    public void stop() {
        this.setLeftVoltage(0);
        this.setRightVoltage(0);
    }
    
}
