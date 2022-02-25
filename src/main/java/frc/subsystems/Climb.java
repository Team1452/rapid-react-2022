package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.RobotMap;
import frc.utils.SleepUtil;

public class Climb {
    // limit switches
    private final DigitalInput limitLeft;
    private final DigitalInput limitRight;
    private final DigitalInput limitCalibrate;

    // motors
    private final CANSparkMax gondola;
    private final CANSparkMax lift;

    public Climb() {
        limitLeft = new DigitalInput(RobotMap.CLIMB_LIMIT_LEFT);
        limitRight = new DigitalInput(RobotMap.CLIMB_LIMIT_RIGHT);
        limitCalibrate = new DigitalInput(RobotMap.CLIMB_LIMIT_CALIBRATE);

        gondola = new CANSparkMax(RobotMap.CLIMB_GONDOLA_MOTOR, MotorType.kBrushless);
        lift = new CANSparkMax(RobotMap.CLIMB_MOTOR, MotorType.kBrushless);
    }

    private void calibrate(XboxController controller) {
        gondola.set(-0.25);

        while (!limitCalibrate.get())
            SleepUtil.sleep(10);

        // stop and floor gondola
        gondola.stopMotor();
        gondola.getEncoder().setPosition(0);

        controller.setRumble(RumbleType.kLeftRumble, 0.5);
        controller.setRumble(RumbleType.kRightRumble, 0.5);

        SleepUtil.sleep(300);

        controller.setRumble(RumbleType.kLeftRumble, 0);
        controller.setRumble(RumbleType.kRightRumble, 0);
    }

    private void retractGondola(XboxController controller) {
        gondola.set(-1);
        while (controller.getRightBumper()) 
            SleepUtil.sleep(10);

        gondola.stopMotor();
    }
    private void retractGondola(double speed, int millis) {
            gondola.set(-speed);
            SleepUtil.sleep(millis);
        gondola.stopMotor();
    }

    private void extendGondola(XboxController controller) {
        while (controller.getLeftTriggerAxis() > 0) {
            gondola.set(controller.getRightTriggerAxis());
            SleepUtil.sleep(10);
        }

        gondola.stopMotor();
    }
    private void extendGondola(double speed, int millis) {
            gondola.set(speed);
            SleepUtil.sleep(millis);
        gondola.stopMotor();
    }

    private void retractLift(XboxController controller) {
        lift.set(-1);
        while (controller.getRightBumperPressed())
            SleepUtil.sleep(10);

        lift.stopMotor();
    }
    private void retractLift(double speed, int millis) {
            gondola.set(-speed);
            SleepUtil.sleep(millis);
        gondola.stopMotor();
    }

    private void extendLift(XboxController controller) {
        lift.set(1);

        while (controller.getRightTriggerAxis() > 0) {
            lift.set(controller.getRightTriggerAxis());
            SleepUtil.sleep(10);
        }

        lift.stopMotor();
    }
    private void extendLift(double speed, int millis) {
            gondola.set(speed);
            SleepUtil.sleep(millis);
        gondola.stopMotor();
    }
    
    private void grabBar(XboxController controller){
        
        
    }
    /*
     * extend and push down
     * extend gondola until lim switch
     * raise about 2 inches up
     * traverse gondola until lim switch
     */
} 