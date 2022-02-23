package frc.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotMap;
import frc.utils.SleepUtil;

/**
 * Singleton wrapper class for motor/serial logic for the physical drivetrain
 * Business logic is handled elsewhere in frc.robot
 */
public class Drivetrain {

    private CANSparkMax backLeftDrive, backRightDrive;

    // hold onto auxiliary motors to keep track of opening/closing
    private CANSparkMax frontLeftDrive, frontRightDrive;

    private static double WHEEL_CIRCUM = 6.0 * Math.PI; // diameter of each main/drive wheels in inches
    private static double TURN_DIAMETER = 21.5; // diameter of inscribed circle in inches
    private static double GEAR_RATIO = 1.0 / 10.71; // gear ratio

    private static double kP = 0.1;
    private static double kI = 0.1;
    private static double kD = 0.1;

    private PIDController backLeftPID;

    /**
     * Initialize motors
     */
    public Drivetrain() {
        backLeftDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_BACK_LEFT, MotorType.kBrushless);
        backRightDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_BACK_RIGHT, MotorType.kBrushless);
        
        // fetch 
        frontLeftDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_FRONT_LEFT, MotorType.kBrushless);
        frontRightDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_FRONT_RIGHT, MotorType.kBrushless);

        backLeftDrive.restoreFactoryDefaults();
        backRightDrive.restoreFactoryDefaults();

        frontRightDrive.restoreFactoryDefaults();
        frontLeftDrive.restoreFactoryDefaults();

        frontLeftDrive.follow(backLeftDrive);
        frontRightDrive.follow(backRightDrive);

        backLeftDrive.setIdleMode(CANSparkMax.IdleMode.kBrake);
        backRightDrive.setIdleMode(CANSparkMax.IdleMode.kBrake);

        backLeftPID = new PIDController(kP, kI, kD);
        backLeftPID.setTolerance(5, 10);
    }

    /**
     * Set speed of left drive
     */
    public void driveLeft(double speed) {
        backLeftDrive.set(speed);
    }

    /**
     * Set speed of right drive
    */
    public void driveRight(double speed) {
        backRightDrive.set(speed);
    }
 
    public List<Double> getPosition() {
        List<Double> result = new ArrayList<>();

        result.add(backLeftDrive.getEncoder().getPosition());
        result.add(backRightDrive.getEncoder().getPosition());
        result.add(frontRightDrive.getEncoder().getPosition());
        result.add(frontLeftDrive.getEncoder().getPosition());

        return result;
    }

    /**
     * Move robot at arbitrary speed and turn
     * @param speed rate at which the robot will accelerate
     * @param turn rate at which the robot should turn (left/right differential)
     */
    public void drive(double speed, double turn) {
        driveLeft(speed + turn);
        driveRight(-speed + turn);
    }


    private static final double driveKp = 7.0;
    private static final double driveKi = 0.018;
    private static final double driveKd = 1.5;

    public void driveInches(double inches) {
        // TODO: make more precise (half inch off)
        double setpoint = Math.abs(inches) / (WHEEL_CIRCUM * GEAR_RATIO);

        System.out.println("STARTING driveInches(" + inches + ")");

        RelativeEncoder encoder = backLeftDrive.getEncoder();
        double initial = encoder.getPosition();

        PIDController pid = new PIDController(driveKp, driveKi, driveKd);
        pid.setSetpoint(setpoint);
        pid.setTolerance(0.05);

        System.out.println("Starting pos: " + encoder.getPosition() + "; target: " + setpoint);

        double sign = inches > 0 ? 1 : -1;

        while (!pid.atSetpoint()) {
            double rate = pid.calculate(Math.abs(encoder.getPosition() - initial));

            // TODO: normalize PID rate/tweak gains for motors?
            driveLeft(sign * rate);
            driveRight(-sign * rate);

            SleepUtil.sleep(10);
        }

        pid.close();

        System.out.println("driveInches() has completed: " + encoder.getPosition() + ", " + setpoint);

        stop();
    }


    private static final double rotateKp = 7.0;
    private static final double rotateKi = 0.018;
    private static final double rotateKd = 1.5;

    public void rotate(double radians) {
        /* 
            Calculate target encoder position
            by getting the arc length of the 
            sector of the enscribed circle of the drivetrain
        */
        double setpoint = Math.abs(radians) * TURN_DIAMETER / (WHEEL_CIRCUM * GEAR_RATIO * 2.0);

        // TODO: Try and get ControlType.kPosition to work (drive rotations instead of velocity)
        // backLeftDrive.getPIDController().setReference(setpoint / 2.0, com.revrobotics.CANSparkMax.ControlType.kPosition);
        // backRightDrive.getPIDController().setReference(setpoint / 2.0, com.revrobotics.CANSparkMax.ControlType.kPosition);

        RelativeEncoder encoder = backLeftDrive.getEncoder();
        double initial = encoder.getPosition();

        PIDController pid = new PIDController(rotateKp, rotateKi, rotateKd);
        pid.setSetpoint(setpoint);
        pid.setTolerance(0.05);

        // TODO: make rotations more precise
        double sign = Math.signum(radians);
        while (!pid.atSetpoint()) {
            SleepUtil.sleep(5);
            
            double rate = pid.calculate(Math.abs(initial - encoder.getPosition()));

            System.out.println(rate);

            // TODO: normalize PID calculate() output for motors/tweak gains?

            driveLeft(sign * -rate);
            driveRight(sign * -rate);
        }

        pid.close();

        stop();
    }

    public void stop() {
        driveLeft(0);
        driveRight(0);
    }

    /**
     * Close motor IO when class is no longer needed
     */
    public void close() {
        backRightDrive.close();
        backLeftDrive.close();
        frontRightDrive.close();
        frontLeftDrive.close();
        backLeftPID.close();
    }
}