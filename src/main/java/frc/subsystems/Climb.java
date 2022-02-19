package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;

public class Climb {
    // limit switches
    private final DigitalInput limitLeft;
    private final DigitalInput limitRight;
    private final DigitalInput limitCalibrate;

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
        gondolaMotor.set(-0.5);

        while (!limitCalibrate.get()) {
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    private void extendGondola() {
        gondolaMotor.set(1);

        final RelativeEncoder encoder = gondolaMotor.getEncoder();
        final SparkMaxPIDController pid = gondolaMotor.getPIDController();

        double initial = encoder.getPosition(),
                target = 1000, // magic number for revolutions to full extension
                position,
                proportional;

        while (!limitLeft.get() && !limitRight.get()) {
            // encoder.setP(RobotMap.GONDOLA_PID_SETTINGS);

            // gondolaMotor.set(proportional);

            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    /*
     * extend and push down
     * extend gondola until lim switch
     * raise about 2 inches up
     * traverse gondola until lim switch
     */
} 