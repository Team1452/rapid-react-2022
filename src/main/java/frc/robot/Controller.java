package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private static final Controller instance = new Controller();
    XboxController XBOXController;
    private Controller(){
         XBOXController = new XboxController(RobotMap.XBOX_CONTROLLER);
    }
    public static Controller getInstance(){
        return instance;
    }
    public XboxController getController(){
        return XBOXController;
    }
}
