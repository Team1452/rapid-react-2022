package frc.subsystems;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;

public class Climb {
    private static int LIMIT_LEFT_PORT = 10;
    private static int LIMIT_RIGHT_PORT = 10;

    private DigitalInput limitLeft = new DigitalInput(LIMIT_LEFT_PORT);
    private DigitalInput limitRight = new DigitalInput(LIMIT_RIGHT_PORT);
    private static Climb instance;

    private Climb(CANSparkMax leftMotor, CANSparkMax rightMotor) {
           leftMotor.follow(rightMotor);
    }

    public static Climb getInstance() {
        return instance;
    }

    private void extend() {

    }

    /*
        extend and push down
        extend gondola until lim switch
        raise about 2 inches up
        traverse gondola until lim switch
    */
}