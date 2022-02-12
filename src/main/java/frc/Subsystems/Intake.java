package frc.subsystems;

import com.revrobotics.CANSparkMax;

public class Intake {
    private int position = 0;
    private CANSparkMax lift, intake;
    private boolean isOn = false;
   public Intake(CANSparkMax lift, CANSparkMax intake){
        this.lift = lift;
        this.intake = intake;
   }

    public static Intake getInstance() {
        return instance;
    }

   public void setIntakeState(boolean x){ //set Intake to state x
        isOn = x;
        if(isOn){
            intake.set(1);
        }
   }
   public boolean getIntakeState(){
        return isOn;
   }
   public void setLiftPosition(int desiredPos){
        position = desiredPos;
   }
   public void Output(){ //bring lift to correct pos and output balls
        if(isOn){
            isOn = false;
        }
        
   }
}
