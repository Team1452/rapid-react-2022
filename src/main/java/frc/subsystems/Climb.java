package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;

public class Climb {
    private final CANSparkMax lift = new CANSparkMax(RobotMap.CLIMB_MOTOR, MotorType.kBrushless);

    public void lift(double speed) {
        lift.set(speed);
    }
} 