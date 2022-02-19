package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.RobotMap;

/**
 * Singleton wrapper class for motor/serial logic for the physical drivetrain
 * Business logic is handled elsewhere in frc.robot
 */
public class Drivetrain {

    private CANSparkMax leftDrive, rightDrive;

    // hold onto auxiliary motors to keep track of opening/closing
    private CANSparkMax leftDrive2, rightDrive2;

    /**
     * Initialize motors
     */
    public Drivetrain() {
        leftDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_BACK_LEFT, MotorType.kBrushless);
        rightDrive = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_BACK_RIGHT, MotorType.kBrushless);
        
        // fetch 
        leftDrive2 = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_FRONT_LEFT, MotorType.kBrushless);
        rightDrive2 = new CANSparkMax(RobotMap.DRIVETRAIN_MOTOR_FRONT_RIGHT, MotorType.kBrushless);

        leftDrive.restoreFactoryDefaults();
        leftDrive2.restoreFactoryDefaults();
        rightDrive.restoreFactoryDefaults();
        rightDrive2.restoreFactoryDefaults();

        leftDrive2.follow(leftDrive);
        rightDrive2.follow(rightDrive);

        leftDrive.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightDrive.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    /**
     * Set speed of left drive
     */
    public void driveLeft(double speed) {
        leftDrive.set(speed);
    }

    /**
     * Set speed of right drive
    */
    public void driveRight(double speed) {
        rightDrive.set(speed);
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
        rightDrive.close();
        leftDrive.close();
        rightDrive2.close();
        leftDrive2.close();
    }
}