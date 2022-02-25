package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.RobotMap;

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
    }

    private void retractGondola(XboxController controller) {
    }

    private void extendGondola(XboxController controller) {
    }

    private void retractLift(XboxController controller) {
    }

    private void extendLift(XboxController controller) {
    }

    /*
     * extend and push down
     * extend gondola until lim switch
     * raise about 2 inches up
     * traverse gondola until lim switch
     */
} 