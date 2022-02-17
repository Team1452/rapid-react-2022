package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Entrypoint for Auton/drive
 * 
 * Don't add any additional initialization/business logic
 * here unless you know what you're doing
 */
public final class Main {
    private Main() {
    }

    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
