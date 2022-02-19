package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
import frc.subsystems.IntakeMode;
import frc.utils.SleepUtil;

/**
 * Encapsulates autonomous routine
 */
public class Auton {
    /**
     * Stage 1: Shoot pre-loaded ball
     */
    public static void stageOne() {
        // TODO: Shoot pre-loaded ball into low goal
    }

    /**
     * Stage 2: Drive to nearest ball
     */
    public static void stageTwo(Drivetrain drivetrain) {
        /* TODO:
            1. Rotate left to have intake face ball
            2. Move forward to ball
        */

        // TODO: rotate/drive using gyro and encoders
        drivetrain.rotate(100);
        SleepUtil.sleep(30);
        drivetrain.stop();

        drivetrain.drive(20, 0);
        SleepUtil.sleep(30);
        drivetrain.stop();
    }

    /**
     * Stage 3: Intake reached ball
     */
    public static void stageThree(Intake intake) {
        intake.setMode(IntakeMode.INWARD);
        intake.set(30);

        SleepUtil.sleep(20);

        intake.stop();
    }

    /**
     * Stage 4: Drive to terminal
     */
    public static void stageFour(Drivetrain drivetrain) {
        // TODO: input actual rotation/drive amounts
        drivetrain.rotate(100);
        SleepUtil.sleep(30);

        drivetrain.drive(30, 0);
        SleepUtil.sleep(30);
    }
}
