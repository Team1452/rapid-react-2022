package frc.Subsystems;

//import com.ctre.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Tank {
    private WPI_TalonSRX leftMotor;
    private WPI_TalonSRX rightMotor;

    public Tank(int port) {
        leftMotor = new WPI_TalonSRX(port);
        rightMotor = new WPI_TalonSRX(port + 1);
    }

    public double TankDrive(double botSpeed) {
        leftMotor.set(botSpeed);
        return 0;
    }

    public WPI_TalonSRX getLeftMotor() {
        return leftMotor;
    }

    public WPI_TalonSRX getRightMotor() {
        return rightMotor;
    }
}
