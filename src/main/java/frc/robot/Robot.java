package frc.robot;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.subsystems.Drivetrain;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;
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
    // run periodic methods every 30ms
    private static final double PERIODIC_INTERVAL = 30;

    private final XboxController controller = new XboxController(RobotMap.XBOX_CONTROLLER);
    private final Drivetrain drivetrain = new Drivetrain();
    private final DrivetrainController drivetrainController = new DrivetrainController(drivetrain);
    private final Intake intake = new Intake();
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
        auton = new Auton(Tarmac.LEFT_BOTTOM, drivetrainController);
        auton.start();
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

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        // update drivetrain
        double speed = -Math.pow(controller.getRightY(), 3) * 0.6;
        double turn = Math.pow(controller.getLeftX(), 3);

        drivetrainController.update(speed, turn);

        // update intake
        // intake.set(controller.getLeftTriggerAxis());
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
        auton = new Auton(drivetrainController, new Position(new Location(0, 0), 0));

        auton.navigate(new Location(51, 51));
    }

    @Override
    public void testPeriodic() {
        auton.update();
    }
}