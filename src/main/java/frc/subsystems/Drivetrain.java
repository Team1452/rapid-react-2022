package frc.subsystems;

import frc.robot.RobotMap;

import java.util.Optional;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Wrapper class for motor/serial logic for the physical drivetrain
 */
public class Drivetrain {

    private CANSparkMax backLeftDrive, backRightDrive;

    // hold onto auxiliary motors to keep track of opening/closing
    private CANSparkMax frontLeftDrive, frontRightDrive;

    private Optional<Double> deltaInitial = Optional.empty();

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
    public void setLeft(double speed) {
        backLeftDrive.set(speed);
    }

    /**
     * Set speed of right drive
    */
    public void setRight(double speed) {
        backRightDrive.set(speed);
    }

    /**
     * Internal helper for totaling current drivetrain rotation encoder state
     * @return Total position for both left and right motors of drivetrain
     */
    private double getTotalEncodedPosition() {
        return (backLeftDrive.getEncoder().getPosition() + backRightDrive.getEncoder().getPosition()) / 2d;
    }

    /**
     * Set initial encoder reading for getting encoder displacement
     */
    public void tare() {
        deltaInitial = Optional.of(0d);
    }

    /**
     * Get encoder displacement (current - initial)
     * @return Drivetrain displacement in terms of motor
     */
    public double getDisplacement() {
        if (deltaInitial.isEmpty()) throw new IllegalStateException("Tried to get encoder position without taring first");

        return getTotalEncodedPosition() - deltaInitial.get();
    }

    public void stop() {
        setLeft(0);
        setRight(0);
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