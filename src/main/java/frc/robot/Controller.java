package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private static final Controller instance = new Controller();

    private XboxController controller;

    private Controller() {
        controller = new XboxController(RobotMap.XBOX_CONTROLLER);
    }

    public static Controller getInstance() {
        return instance;
    }

    public XboxController getController() {
        return controller;
    }
}
