package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.subsystems.Climb;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;
import frc.subsystems.Intake.IntakeMode;

/**
 * Wraps XboxController for getting
 * controller to interact with Robot
 * during teleop
 */
public class TeleopController {
    private final XboxController controller;

    private enum RightJoystickControlMode { CLIMB, INTAKE }
    private enum DriveMode { FAST, SLOW }

    private RightJoystickControlMode curRightJoystickControlMode = RightJoystickControlMode.INTAKE;
    private DriveMode driveMode = DriveMode.FAST;

    public TeleopController(XboxController controller) {
        this.controller = controller;
    }    

    private void toggleDriveMode() {
        driveMode = driveMode == DriveMode.FAST ? DriveMode.SLOW : DriveMode.FAST;
    }

    public void update(DrivetrainController drivetrainController, Intake intake, Climb climb) {
        // drivetrain
        if (controller.getYButtonPressed()) toggleDriveMode();

        double speed = -Math.pow(controller.getLeftY(), 5) * 0.6;
        if (driveMode == DriveMode.SLOW) speed *= 0.25; 

        double turn = Math.pow(controller.getLeftX(), 5);
        if (driveMode == DriveMode.SLOW) turn *= 0.25;

        drivetrainController.update(speed, turn);

        // intake
        IntakeMode intakeMode = controller.getLeftTriggerAxis() > 0.1
            ? IntakeMode.INWARD
            : controller.getRightTriggerAxis() > 0.1
                ? IntakeMode.OUTWARD
                : IntakeMode.IDLE;
        intake.setIntake(intakeMode);

        System.out.println("INTAKE MODE: " + intakeMode);

        // right joystick
        // switch to climb mode if B button pressed
        if (controller.getBButtonPressed()) {
            intake.stopLift();
            curRightJoystickControlMode = RightJoystickControlMode.CLIMB;
            GUI.setToClimbMode();
        }

        // switch to intake mode if X button pressed
        if (controller.getXButtonPressed()) {
            climb.stop();
            curRightJoystickControlMode = RightJoystickControlMode.INTAKE;
            GUI.setToIntakeMode();
        }

        double rightJoystickSpeed = -Math.signum(controller.getRightY()) * Math.pow(controller.getRightY(), 2);

        switch (curRightJoystickControlMode) {
            case CLIMB: climb.lift(rightJoystickSpeed); break;
            case INTAKE: intake.setLift(rightJoystickSpeed); break;
        }
    }
}
