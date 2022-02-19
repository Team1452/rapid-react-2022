package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.RobotMap;

public class Climb {
    // limit switches
    private final DigitalInput limitLeft;
    private final DigitalInput limitRight;
    private final DigitalInput limitCalibrate;
    private XboxController controller;
    // motors
    private final CANSparkMax gondolaMotor;
    private final CANSparkMax motor;

    public Climb(XboxController controller) {
        this.controller = controller;

        limitLeft = new DigitalInput(RobotMap.CLIMB_LIMIT_LEFT);
        limitRight = new DigitalInput(RobotMap.CLIMB_LIMIT_RIGHT);
        limitCalibrate = new DigitalInput(RobotMap.CLIMB_LIMIT_CALIBRATE);

        gondolaMotor = new CANSparkMax(RobotMap.CLIMB_GONDOLA_MOTOR, MotorType.kBrushless);
        motor = new CANSparkMax(RobotMap.CLIMB_MOTOR, MotorType.kBrushless);
    }

    private void calibrateGondola() {
        gondolaMotor.set(-0.25);

        while (!limitCalibrate.get()) {
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        gondolaMotor.stopMotor();
        gondolaMotor.getEncoder().setPosition(0);
        controller.setRumble(RumbleType.kLeftRumble, 0.5);
        controller.setRumble(RumbleType.kRightRumble, 0.5);
            try {
                Thread.sleep(300);
            } catch (Exception err) {
                err.printStackTrace();
            }
        controller.setRumble(RumbleType.kLeftRumble, 0);
        controller.setRumble(RumbleType.kRightRumble, 0);
    }

    private void retractGondola() {
        gondolaMotor.set(-1);
        final RelativeEncoder encoder = gondolaMotor.getEncoder();
        while (controller.getRightBumper()) {
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        gondolaMotor.stopMotor();
    }
    private void extendGondola() {
        final RelativeEncoder encoder = gondolaMotor.getEncoder();
        while (controller.getLeftTriggerAxis()>0) {
            gondolaMotor.set(controller.getRightTriggerAxis());
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        gondolaMotor.stopMotor();
    }
    private void retractLift() {
        motor.set(-1);
        final RelativeEncoder encoder = motor.getEncoder();
        while (controller.getRightBumperPressed()) {
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        motor.stopMotor();
    }
    private void extendLift() {
        motor.set(1);
        final RelativeEncoder encoder = motor.getEncoder();
        while (controller.getRightTriggerAxis() > 0) {
            motor.set(controller.getRightTriggerAxis());
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        motor.stopMotor();
    }

    /*
     * extend and push down
     * extend gondola until lim switch
     * raise about 2 inches up
     * traverse gondola until lim switch
     */
} 