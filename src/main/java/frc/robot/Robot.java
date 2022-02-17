package frc.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.subsystems.Drivetrain;
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
    private static final double PERIODIC_INTERVAL = 0.03;    

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    private XboxController controller;
    private Drivetrain drivetrain;

    public Robot() {
        super(PERIODIC_INTERVAL);
    }

    /** Run when robot is started for initialization */
    @Override
    public void robotInit() {
        controller = new XboxController(RobotMap.XBOX_CONTROLLER);
        drivetrain = Drivetrain.getInstance();
    }

    /** Called every PERIODIC_INTERVAL */
    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
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
        double speed = -Math.pow(controller.getLeftY(), 3) * 0.6;
        double turn = Math.pow(controller.getLeftX(), 3);

        drivetrain.driveLeft(speed + turn);
        drivetrain.driveRight(turn - speed);
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }
}