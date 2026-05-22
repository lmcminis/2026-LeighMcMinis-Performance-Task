package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
    @AutoLog

    public static class PivotIOInputs {
        public double appliedVoltage = 0;
        public double currentAmps = 0;
        public double motorTempDegreesC = 0;

        public double velocityRadiansPerSecond = 0;
        public double angleRadians = 0;
    }

    public abstract void updateInputs(PivotIOInputs inputs);

    public abstract void setAngleRadians(double angleRadians);

    public abstract void setVoltage(double appliedVoltage);

}
