package frc.robot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonSequence;
import frc.robot.auton.AutonSequences;
import frc.robot.auton.Motion;
import frc.subsystems.Drivetrain;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        // Add commands to the autonomous command chooser
        SendableChooser<Tarmac> autonomousChooser = new SendableChooser<>();
        autonomousChooser.setDefaultOption("Simple Auto", Tarmac.RIGHT_BOTTOM);
        autonomousChooser.addOption("Complex Auto", Tarmac.LEFT_BOTTOM);
        SmartDashboard.putData(autonomousChooser);
        Field2d autoFieldBack = new Field2d();
        SmartDashboard.putData(autoFieldBack);
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
        auton = new Auton(
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
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