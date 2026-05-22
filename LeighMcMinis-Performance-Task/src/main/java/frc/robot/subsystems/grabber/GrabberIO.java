package frc.robot.subsystems.grabber;

import org.littletonrobotics.junction.AutoLog;

public interface GrabberIO {

    @AutoLog
    public static class GrabberIOInputs {
        public double[] appliedVoltage = new double[2];
        public double[] currentAmps = new double[2];
        public double[] statorCurrentAmps = new double[2];
        public double[] velocityRadiansPerSecond = new double[2];
        public double[] motorTempDegreesC = new double[2];
    }

    public abstract void setVoltage(double volts);

    public abstract void setVelocity(double radPerSec);
    
    public abstract void updateInputs(GrabberIOInputs inputs);

    public abstract void stop();

}
