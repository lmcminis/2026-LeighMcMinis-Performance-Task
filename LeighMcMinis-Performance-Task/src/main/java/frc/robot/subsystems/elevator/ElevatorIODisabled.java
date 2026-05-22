package frc.robot.subsystems.elevator;

public class ElevatorIODisabled implements ElevatorIO {
    private double targetVoltage = 0;
    private double targetPosition = 0;

    @Override
    public void setPosition(double heightMeters) {
        this.targetPosition = heightMeters;
    }

    @Override
    public void setVoltage(double volts){
        targetVoltage = volts;
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs){
        inputs.appliedVoltage[0] = targetVoltage;
        inputs.appliedVoltage[1] = targetVoltage;
        inputs.positionMeters = targetPosition;
        
    }

    @Override
    public void resetEncoder() {


    }

    @Override
    public void stop(){
        this.setVoltage(0);

    }
    
}
