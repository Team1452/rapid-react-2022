package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wraps SmartDashboard for reading, writing
 * and displaying information
 */
public class GUI {
    private static final String INTAKE_SELECTED = "INTAKE SELECTED";
    private static final String CLIMB_SELECTED = "CLIMB SELECTED";

    private static final SendableChooser<Optional<Tarmac>> autonomousChooser = new SendableChooser<>();

    static {
        // add options for sequences
        for (Tarmac tarmac : Tarmac.values())
            autonomousChooser.addOption(tarmac.name(), Optional.of(tarmac));

        autonomousChooser.addOption("CUSTOM", Optional.empty());

        SmartDashboard.putData(autonomousChooser); 
    }

    public static void setToIntakeMode() {
        setIntakeSelectedLabel(true);
        setClimbSelectedLabel(false);
    }

    public static void setToClimbMode() {
        setClimbSelectedLabel(true);
        setIntakeSelectedLabel(false);
    }

    private static void setIntakeSelectedLabel(boolean visible) {
        SmartDashboard.putString(INTAKE_SELECTED, visible ? INTAKE_SELECTED : "");
    }

    private static void setClimbSelectedLabel(boolean visible) {
        SmartDashboard.putString(CLIMB_SELECTED, visible ? CLIMB_SELECTED : "");
    }
}
