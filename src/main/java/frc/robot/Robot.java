package frc.robot;

import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.subsystems.Climb;
import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
import frc.utils.SleepUtil;
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

    private XboxController controller = new XboxController(RobotMap.XBOX_CONTROLLER);
    private Drivetrain drivetrain = new Drivetrain();
    private Intake intake = new Intake();
    private Climb climb;

    public Robot() {
        super(PERIODIC_INTERVAL / 1000);
    }

    /** Run when robot is started for initialization */
    @Override
    public void robotInit() {
        // configure UsbCamera for Shuffleboard
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 1280, 720, 30);
    }

    /** Called every PERIODIC_INTERVAL */
    @Override
    public void robotPeriodic() {
    }

    private void waitForAButton() {
        while (!controller.getAButtonPressed())
            SleepUtil.sleep(10);
    }

    /**
     * When autonomous starts, iterate through autonomous
     * routine until done
     */
    @Override
    public void autonomousInit() {
        // TODO: add in SmartDashboard/UI for selecting starting Tarmac
        Auton auton = new Auton(Tarmac.LEFT_TOP);

        auton.stageOne();

        waitForAButton();

        auton.stageTwo(drivetrain);

        waitForAButton();

        auton.stageThree(intake);

        waitForAButton();

        auton.stageFour(drivetrain);
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        
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

        System.out.println("TELEOP: speed: " + speed + "; " + turn);

        drivetrain.drive(speed, turn);

        // read motors
        // System.out.println(drivetrain.getPosition());

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
        // CLIMB (CONNNOR?!?!?!?!?!?!?!)
        // climb = new Climb();

        // drivetrain.driveInches(92.0);

        // drivetrain.rotate(-Math.PI / 2.0);

        Auton drive = new Auton(new Position(new Location(0, 0), 0));

        System.out.println("STARTING TEST:");
        System.out.println("Rotating to 270deg");
        drive.rotateTo(drivetrain, toRadians(270.0));

        System.out.println("Rotating to 0deg");
        drive.rotateTo(drivetrain, toRadians(0.0));

        // List<Location> list = new LinkedList<>();

        // list.add(new Location(51, 51));
        // list.add(new Location(51, 0));
        // list.add(new Location(0, 0));


        // drive.navigate(drivetrain, new Location(51, 51));
        // drive.navigate(drivetrain, new Location(51, 0));
        // drive.navigate(drivetrain, new Location(0, 0));

        // System.out.println("ROTATING...");
        // drivetrain.rotate(2.0 * Math.PI);

        // System.out.println("SLEEPING...");
        // SleepUtil.sleep(1000);

        //System.out.println("DRIVING...");
        //drivetrain.driveInches(24.0);

        //System.out.println("ROTATING...");
        //drivetrain.rotate(2.0 * Math.PI);
    }

    @Override
    public void testPeriodic() {
    }
}