package frc.robot;

import java.util.Arrays;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonSequence;
import frc.robot.auton.Motion;
import frc.subsystems.Climb;
import frc.subsystems.Drivetrain;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;
import frc.subsystems.Intake.IntakeMode;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    // run periodic methods every 20ms
    public static final double PERIODIC_INTERVAL = 20;

    private final XboxController controller = new XboxController(RobotMap.XBOX_CONTROLLER);
    private final Drivetrain drivetrain = new Drivetrain();
    private final DrivetrainController drivetrainController = new DrivetrainController(drivetrain);
    private final Intake intake = new Intake();
    private final Climb climb = new Climb();
    private Auton auton;


    public Robot() {
        super(PERIODIC_INTERVAL / 1000);
    }

    /** Run when robot is started for initialization */
    @Override
    public void robotInit() {
        // start camera stream for USB camera
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 1280, 720, 30);
    }

    /** Called every PERIODIC_INTERVAL */
    @Override
    public void robotPeriodic() {
    }

    /**
     * When autonomous starts, iterate through autonomous
     * routine until done
     */
    @Override
    public void autonomousInit() {
        // TODO: get tarmac from Shuffleboard
        auton = new Auton(Tarmac.LEFT_MIDDLE, drivetrainController, intake);
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        auton.update(controller.getAButtonPressed());
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }

    // teleop state
    private enum RightJoystickControlMode { CLIMB, INTAKE }
    private enum DriveMode { FAST, SLOW }

    private RightJoystickControlMode curRightJoystickControlMode = RightJoystickControlMode.INTAKE;
    private DriveMode driveMode = DriveMode.FAST;

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        // UPDATE DRIVETRAIN
        
        // drivetrain mode
        if (controller.getYButtonPressed()) {
            driveMode = driveMode == DriveMode.FAST ? DriveMode.SLOW : DriveMode.FAST;
        }

        double speed = -Math.pow(controller.getLeftY(), 5) * 0.6;
        if (driveMode == DriveMode.SLOW) speed *= 0.25; 

        double turn = Math.pow(controller.getLeftX(), 5);
        if (driveMode == DriveMode.SLOW) turn *= 0.25;

        drivetrainController.update(speed, turn);

        // UPDATE INTAKE
        IntakeMode intakeMode = controller.getLeftTriggerAxis() > 0.1
            ? IntakeMode.INWARD
            : controller.getRightTriggerAxis() > 0.1
                ? IntakeMode.OUTWARD
                : IntakeMode.IDLE;

        System.out.println("INTAKE MODE: " + intakeMode);

        // ball intake
        intake.setIntake(intakeMode);

        // switch to climb mode if B button pressed
        if (controller.getBButtonPressed()) {
            // stop intake
            intake.stopLift();

            // if B button pressed then joystick controls climb
            curRightJoystickControlMode = RightJoystickControlMode.CLIMB;
            
            // update gui
            GUI.setToClimbMode();
        }

        // switch to intake mode if X button pressed
        if (controller.getXButtonPressed()) {
            // stop climb
            climb.stop();

            // if X button pressed then joystick controls intake
            curRightJoystickControlMode = RightJoystickControlMode.INTAKE;

            // update gui
            GUI.setToIntakeMode();
        }

        double rightJoystickSpeed = -Math.signum(controller.getRightY()) * Math.pow(controller.getRightY(), 2);

        switch (curRightJoystickControlMode) {
            case CLIMB: climb.lift(rightJoystickSpeed); break;
            case INTAKE: intake.setLift(rightJoystickSpeed); break;
        }
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }


    private double toRadians(double deg) {
        return deg / 180.0 * Math.PI;
    }

    @Override
    public void testInit() {
        auton = new Auton(
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(IntakeMode.INWARD),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            ),
            drivetrainController,
            intake
        );
    }

    @Override
    public void testPeriodic() {
        auton.update();
    }
}