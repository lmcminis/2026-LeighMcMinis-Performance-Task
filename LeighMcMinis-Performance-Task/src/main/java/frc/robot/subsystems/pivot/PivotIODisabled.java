package frc.robot.subsystems.pivot;

public class PivotIODisabled implements PivotIO {
    private double targetAngleRadians = 0;
    private double targetVoltage = 0;

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        inputs.appliedVoltage = targetVoltage;
        inputs.angleRadians = targetAngleRadians;
        
    }

    @Override
    public void setAngleRadians(double angleRadians) {
        this.targetAngleRadians = angleRadians;

    }

    @Override
    public void setVoltage(double volts) {
        this.targetVoltage = volts;

    }
    
}
