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

    public abstract void setLeftVoltage(double volts);

    public abstract void setLeftVelocity(double radPerSec);

    public abstract void setRightVoltage(double volts);

    public abstract void setRightVelocity(double radPerSec);
    
    public abstract void updateInputs(GrabberIOInputs inputs);

    public abstract void stop();

}
