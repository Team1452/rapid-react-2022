package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.Controller;
import frc.robot.RobotMap;

public class Climb {
    // limit switches
    private final DigitalInput limitLeft;
    private final DigitalInput limitRight;
    private final DigitalInput limitCalibrate;
    private XboxController controller = Controller.getInstance().getController();
    // motors
    private final CANSparkMax gondolaMotor;
    private final CANSparkMax motor;

    private static final Climb instance = new Climb();

    private Climb() {
        limitLeft = new DigitalInput(RobotMap.CLIMB_LIMIT_LEFT);
        limitRight = new DigitalInput(RobotMap.CLIMB_LIMIT_RIGHT);
        limitCalibrate = new DigitalInput(RobotMap.CLIMB_LIMIT_CALIBRATE);

        gondolaMotor = new CANSparkMax(RobotMap.CLIMB_GONDOLA_MOTOR, MotorType.kBrushless);
        motor = new CANSparkMax(RobotMap.CLIMB_MOTOR, MotorType.kBrushless);
    }

    public static Climb getInstance() {
        return instance;
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

    private void extendGondola() {

        gondolaMotor.set(1);
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
    private void retractGondola() {
        gondolaMotor.set(1);
        final RelativeEncoder encoder = gondolaMotor.getEncoder();
        while (controller.getLeftBumper()) {
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        gondolaMotor.stopMotor();
    }

    /*
     * extend and push down
     * extend gondola until lim switch
     * raise about 2 inches up
     * traverse gondola until lim switch
     */
} 