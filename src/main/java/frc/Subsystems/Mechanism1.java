package frc.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.*;

public class Mechanism1 {
    public WPI_TalonSRX shooter;

    public Mechanism1(int port) {
        shooter = new WPI_TalonSRX(port);
    }

    public void turn(double speed) {
        shooter.set(speed);
    }

    public double getSpeed() {
        return shooter.get();
    }

}
