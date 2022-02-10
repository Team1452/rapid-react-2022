package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Auton {
    public static CANSparkMax leftDrive;
    public Auton( int port){
leftDrive = new CANSparkMax(port, MotorType.kBrushless);
    }

    public static void move(double velocity){
    leftDrive.set(velocity);
    }
      
    
}
