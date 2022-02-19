package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Controller;
import frc.robot.RobotMap;
public class Intake {
    // motors
    private final CANSparkMax lift, intake;
    private IntakeMode intakeMode = IntakeMode.INWARD; // either -1 or 1 
    // intake state
    private LiftPosition position;
    private boolean isOn = false;
    private XboxController controller = Controller.getInstance().getController();
    // singleton instance
    private static final Intake instance = new Intake();

    public Intake getInstance() { return instance; }

    private Intake() {
        lift = new CANSparkMax(RobotMap.INTAKE_MOTOR_LIFT, MotorType.kBrushless);
        intake = new CANSparkMax(RobotMap.INTAKE_MOTOR_INTAKE, MotorType.kBrushless);
    }

    public void spin(){
        if(intakeMode == intakeMode.INWARD){
        while (controller.getLeftTriggerAxis()>0) {
            intake.set(controller.getLeftTriggerAxis());
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        }else if(intakeMode == intakeMode.OUTWARD){
        while (controller.getLeftTriggerAxis()>0) {
            intake.set(-controller.getLeftTriggerAxis());
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        }
        while (controller.getLeftTriggerAxis()>0) {
            intake.set(controller.getLeftTriggerAxis());
            try {
                Thread.sleep(10);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        intake.stopMotor();
    }
    /**
     * Intake uses a toggle system, spin will turn it 
     * 
     */
    public void toggleIntakeDirection(){
        if(intakeMode == intakeMode.INWARD){
            intakeMode = intakeMode.OUTWARD;
        }else if(intakeMode == intakeMode.OUTWARD){
            intakeMode = intakeMode.INWARD;
        }
    }
    public int getIntakeDirection(){
        return intakeMode;
    }
    public void setIntakeState(boolean on) { //set Intake to state x
        isOn = on;
        intake.set(isOn ? 1 : 0);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setLiftPosition(LiftPosition desiredPos) {
        position = desiredPos;
    }

    public void output() { //bring lift to correct pos and output balls
        setIntakeState(false);
        setLiftPosition(LiftPosition.HIGH);
    }
}