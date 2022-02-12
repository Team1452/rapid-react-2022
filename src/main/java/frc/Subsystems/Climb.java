package frc.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.RobotMap;

public class Climb {
    // limit switches
    private DigitalInput limitLeft;
    private DigitalInput limitRight;

    // motors
    private CANSparkMax gondolaMotor;
    private CANSparkMax motor;

    private static Climb instance = new Climb();

    private Climb() {
        limitLeft = new DigitalInput(RobotMap.CLIMB_LIMIT_LEFT);
        limitRight = new DigitalInput(RobotMap.CLIMB_LIMIT_RIGHT);

        gondolaMotor = new CANSparkMax(RobotMap.CLIMB_GONDOLA_MOTOR, MotorType.kBrushless);
        motor = new CANSparkMax(RobotMap.CLIMB_MOTOR, MotorType.kBrushless);
    }

    public static Climb getInstance() { return instance; }

    private void extend() {

    }

    /*
        extend and push down
        extend gondola until lim switch
        raise about 2 inches up
        traverse gondola until lim switch
    */
}