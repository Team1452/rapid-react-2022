package frc.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;

/**
 * Singleton wrapper class for motor/serial logic for the physical drivetrain
 * Business logic is handled elsewhere in frc.robot
 */
public class Drivetrain {

    private CANSparkMax backLeftDrive, backRightDrive;

    // hold onto auxiliary motors to keep track of opening/closing
    private CANSparkMax frontLeftDrive, frontRightDrive;

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

    /**
     * Close motor IO when class is no longer needed
     */
    public void close() {
        backRightDrive.close();
        backLeftDrive.close();
        frontRightDrive.close();
        frontLeftDrive.close();
    }
}