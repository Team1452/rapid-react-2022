package frc.subsystems;

import java.util.Optional;

import edu.wpi.first.math.controller.PIDController;

/**
 * Wrapper class for interfacing with Drivetrain
 */
public class DrivetrainController {
    private static double WHEEL_CIRCUM = 6.0 * Math.PI; // diameter of each main/drive wheels in inches
    private static double TURN_DIAMETER = 21.5; // diameter of inscribed circle in inches
    private static double GEAR_RATIO = 1.0 / 10.71; // gear ratio
    
    private final Drivetrain drivetrain;
    
    public enum State {
        TELEOP,
        ROTATING_ANGLE,
        DRIVING_DISTANCE
    }

    private abstract class Target {
        private double target;

        Target(double target) { this.target = target; }
        public double getSetpoint() { return target; }
    }

    private class Angle extends Target {
        Angle(double target) {
            super(target);
            if (target > Math.PI || target < -Math.PI) throw new IllegalArgumentException("Expected angle to be between -pi and pi");
        }
    }

    private class Distance extends Target {
        Distance(double target) { super(target); }
    }

    private State state = State.TELEOP;
    private Optional<Target> setpoint = Optional.empty();
    private Optional<PIDController> currentPid = Optional.empty();
 
    /** Inject Drivetrain into Controller */
    public DrivetrainController(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    /**
     * Differential drive for teleop control
     * @param speed rate at which the robot will accelerate
     * @param turn rate at which the robot should turn (left/right differential)
     */
    public void teleop(double speed, double turn) {
        if (state != State.TELEOP) throw new IllegalStateException("Tried to control robot through teleop when not in teleop mode");

        drivetrain.setLeft(speed + turn);
        drivetrain.setRight(-speed + turn);
    }

    private static final double driveKp = 7.0;
    private static final double driveKi = 0.018;
    private static final double driveKd = 1.5;
    private static final double drivePidTolerance = 0.1;

    /**
     * Dedicate controller to driving robot an arbitrary number of inches 
     * @param inches
     */
    public void driveInches(double inches) {
        if (!available()) throw new IllegalStateException("Tried to drive distance while already rotating or driving");

        state = State.DRIVING_DISTANCE;

        // calculate target motor rotations from given distance  
        setpoint = Optional.of(new Distance(inches / (WHEEL_CIRCUM * GEAR_RATIO)));

        currentPid = Optional.of(new PIDController(driveKp, driveKi, driveKd));
        currentPid.get().setTolerance(drivePidTolerance);

        drivetrain.tare();
    }

    private static final double rotateKp = 7.0;
    private static final double rotateKi = 0.018;
    private static final double rotateKd = 1.5;
    private static final double rotatePidTolerance = 0.1;

    /**
     * Dedicate controller to rotating robot an arbitrary angle in radians between -pi and pi
     * @param radians
     */
    public void rotateRadians(double radians) {
        if (!available()) throw new IllegalStateException("Tried to rotate angle while already rotating or driving");

        state = State.ROTATING_ANGLE;

        // calculate target motor rotations from given radians 
        setpoint = Optional.of(new Angle(Math.abs(radians) * TURN_DIAMETER / (WHEEL_CIRCUM * GEAR_RATIO)));

        currentPid = Optional.of(new PIDController(rotateKp, rotateKi, rotateKd));
        currentPid.get().setTolerance(rotatePidTolerance);

        drivetrain.tare();
    }

    /**
     * Rotate drivetrain at arbitrary rate through voltage control mode
     * @param voltage
     */
    private void rotateVoltage(double voltage) {
        drivetrain.setLeft(voltage);
        drivetrain.setRight(voltage);
    }

    /**
     * Drive drivetrain at arbitrary rate through voltage control mode
     * @param voltage
     */
    private void driveVoltage(double voltage) {
        double sign = Math.signum(voltage);

        drivetrain.setLeft(sign * voltage);
        drivetrain.setRight(-sign * voltage);
    }

    /**
     * Returns whether controller is ready for rotating or driving arbitrary amounts
     * @return
     */
    public boolean available() {
        return state == State.TELEOP;
    }

    /**
     * Update controller to update state machine and robot 
     * actuators from a periodic method
     */
    public void update(double humanInputSpeed, double humanInputTurn) {
        switch (state) {
            case DRIVING_DISTANCE:
            case ROTATING_ANGLE:
                double displacement = drivetrain.getDisplacement();

                PIDController pid = currentPid.get();

                double targetSetpoint;
                if (state == State.DRIVING_DISTANCE) targetSetpoint = ((Distance)setpoint.get()).getSetpoint();
                else if (state == State.ROTATING_ANGLE) targetSetpoint = ((Angle)setpoint.get()).getSetpoint();
                else throw new IllegalStateException("Unmatched state when setting targetSetpoint");

                if (pid.atSetpoint()) {
                    state = State.TELEOP;
                    setpoint = Optional.empty();
                    currentPid = Optional.empty();
                    drivetrain.stop();
                    break;
                }

                double rate = pid.calculate(displacement, targetSetpoint);

                if (state == State.DRIVING_DISTANCE) driveVoltage(rate);
                else if (state == State.ROTATING_ANGLE) rotateVoltage(rate);

                break;
            case TELEOP:
                teleop(humanInputSpeed, humanInputTurn);
                break;
        }
    }
}
