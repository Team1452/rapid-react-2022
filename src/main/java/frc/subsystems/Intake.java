package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class Intake {
    // motors
    private final CANSparkMax lift = new CANSparkMax(RobotMap.INTAKE_MOTOR_LIFT, MotorType.kBrushed);
    private final CANSparkMax intake = new CANSparkMax(RobotMap.INTAKE_MOTOR_INTAKE, MotorType.kBrushless);
    private IntakeMode intakeMode = IntakeMode.INWARD;

    private LiftPosition currentLiftPosition;
    
    // limit switches
    // private final DigitalInput liftBottomLimit = new DigitalInput(RobotMap.INTAKE_LIMIT_BOTTOM);
    // private final DigitalInput liftTopLimit = new DigitalInput(RobotMap.INTAKE_LIMIT_TOP);

    public enum LiftPosition { HIGH, LOW }
    public enum IntakeMode { IDLE, INWARD, OUTWARD }

    // private final static double LIFT_SPEED = 0.1;
    private final static double INTAKE_SPEED = 0.3;
    private final static double OUTTAKE_SPEED = 1;

    public void setIntake(IntakeMode mode) {
        double speed = -1; // initial value not used for anything

        switch (mode) {
            case IDLE: speed = 0; break;
            case INWARD: speed = INTAKE_SPEED; break;
            case OUTWARD: speed = -OUTTAKE_SPEED; break;
        }

        intake.set(speed);
    }

    public void setCurrentPosition(LiftPosition position) {
        currentLiftPosition = position;
    }

    public void toggleLiftPosition() {
        currentLiftPosition = currentLiftPosition == LiftPosition.HIGH ? LiftPosition.LOW : LiftPosition.HIGH;
    }

    public void setLift(double speed) {
        lift.set(speed);
    }

    public void stopLift() {
        lift.set(0);
    }

    // public void updateLift() {
    //     switch (currentLiftPosition) {
    //         case HIGH:
    //             if (!liftTopLimit.get()) lift.set(LIFT_SPEED);
    //             else lift.set(0);
    //             break;
    //         case LOW:
    //             if (!liftBottomLimit.get()) lift.set(-LIFT_SPEED);
    //             else lift.set(0);
    //             break;
    //     }
    // }

    public IntakeMode getMode() { return intakeMode; }
    public void setMode(IntakeMode mode) { intakeMode = mode; }
}