package frc.robot;

// the WPILib BSD license file in the root directory of this project.
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

public class XboxController {
    
    private Joystick m_pad;
    
    public XboxController(int port) {
        m_pad = new Joystick(port);
    }
    
    public double getLeftX() {
        return m_pad.getRawAxis(LEFT_X_AXIS);
    }
    
    public double getLeftY() {
        return m_pad.getRawAxis(LEFT_Y_AXIS);
    }
    
    public double getRightX() {
        return m_pad.getRawAxis(RIGHT_X_AXIS);
    }
    
    public double getRightY() {
        return m_pad.getRawAxis(RIGHT_Y_AXIS);
    }
    
    public double getTriggers() {
        return m_pad.getRawAxis(TRIGGERS);
    }
    
    public double getDpadX() {
        return m_pad.getRawAxis(DPAD_LR);
    }
             
    public double applyDeadband(int axis) {
        if(Math.abs(m_pad.getRawAxis(axis)) < .1) {
            return 0;
        } else {
            return axis;
        }
    }          
    
    // Creates buttons
    public Button X = new JoystickButton(m_pad, BUTTON_X);
    public Button Y = new JoystickButton(m_pad, BUTTON_Y);
    public Button A = new JoystickButton(m_pad, BUTTON_A);
    public Button B = new JoystickButton(m_pad, BUTTON_B);
    public Button lBumper = new JoystickButton(m_pad, BUMPER_L);
    public Button rBumper = new JoystickButton(m_pad, BUMPER_R);
    public Button start = new JoystickButton(m_pad, BUTTON_START);
    public Button back = new JoystickButton(m_pad, BUTTON_BACK);
    public Button lStick = new JoystickButton(m_pad, LEFT_STICK_PRESS);
    public Button rStick = new JoystickButton(m_pad, RIGHT_STICK_PRESS);
    
    public boolean getButton(int btn) {
        return m_pad.getRawButton(btn);
    }
    
    // Axis indexes:
    public static final int
            LEFT_X_AXIS = 1,
            LEFT_Y_AXIS = 2,
            TRIGGERS = 3,
            RIGHT_X_AXIS = 4,
            RIGHT_Y_AXIS = 5,
            DPAD_LR = 6;
    
    // Button mappings:
    public static final int
            BUTTON_A = 1,
            BUTTON_B = 2,
            BUTTON_X = 3,
            BUTTON_Y = 4,
            BUMPER_L = 5,
            BUMPER_R = 6,
            BUTTON_BACK = 7,
            BUTTON_START = 8,
            LEFT_STICK_PRESS = 9,
            RIGHT_STICK_PRESS = 10;
    
}