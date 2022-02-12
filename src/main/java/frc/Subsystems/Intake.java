package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;
public class Intake {
    // motors
    private final CANSparkMax lift, intake;

    // intake state
    private liftPosition position;
    private boolean isOn = false;

    // singleton instance
    private static final Intake instance = new Intake();

    public Intake getInstance() { return instance; }

    private Intake() {
        lift = new CANSparkMax(RobotMap.INTAKE_MOTOR_LIFT, MotorType.kBrushless);
        intake = new CANSparkMax(RobotMap.INTAKE_MOTOR_INTAKE, MotorType.kBrushless);
    }
    
    public void setIntakeState(boolean on) { //set Intake to state x
        isOn = on;
        intake.set(isOn ? 1 : 0);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setLiftPosition(liftPosition desiredPos) {
        position = desiredPos;
    }

    public void output() { //bring lift to correct pos and output balls
        setIntakeState(false);
        setLiftPosition(liftPosition.HIGH);
    }
}
