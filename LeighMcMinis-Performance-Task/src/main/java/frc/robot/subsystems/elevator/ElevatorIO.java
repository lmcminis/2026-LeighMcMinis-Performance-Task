package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {

    @AutoLog
    public static class ElevatorIOInputs {

        public double[] appliedVoltage = new double[2];
        public double[] currentAmps = new double[2];
        public double[] statorCurrentAmps = new double[2];
        public double[] motorTempDegreesC = new double[2];

        public double velocityMetersPerSecond = 0;
        public double positionMeters = 0;
    }

    public abstract void updateInputs(ElevatorIOInputs inputs);

    public abstract void setPosition(double heightMeters);

    public abstract void setVoltage(double appliedVoltage);

    public abstract void resetEncoder();

    public abstract void stop();

}
