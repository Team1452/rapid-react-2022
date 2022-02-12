package frc.subsystems;

import com.revrobotics.CANSparkMax;
public class Intake {
    private static liftPosition position = liftPosition.LOW;
    private static CANSparkMax lift, intake;
    private static boolean isOn = false;
    private static Intake instance = new Intake(lift, intake);

    private Intake(CANSparkMax lift, CANSparkMax intake) {
        this.lift = lift;
        this.intake = intake;
    }

    public Intake getInstance() {
        return instance;
    }
    
    public void setIntakeState(boolean isOn) { //set Intake to state x
        this.isOn;
        intake.set(isOn ? 1 : 0);
    }

    public boolean getIntakeState() {
        return isOn;
    }

    public void setLiftPosition(liftPosition desiredPos) {
        position = desiredPos;
    }

    public void Output() { //bring lift to correct pos and output balls
        setIntakeState(false);
        setLiftPosition(liftPosition.HIGH);
    }
}
