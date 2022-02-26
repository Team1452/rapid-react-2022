package frc.robot.auton;

import frc.robot.Location;
import frc.robot.Position;
import frc.subsystems.Intake;

public abstract class Motion {
    public abstract void accept(AutonState state);

    public static class Rotation extends Motion {
        private double angle;

        public Rotation(double angle) {
            this.angle = angle; 
        }

        @Override
        public void accept(AutonState state) {
            state.getDrivetrainController().rotateRadians(angle);
        }
    }

    public static class Drive extends Motion {
        private double inches;

        public Drive(double inches) {
            this.inches = inches;
        }

        @Override
        public void accept(AutonState state) {
            state.getDrivetrainController().driveInches(inches);
        }
    }

    public static class MoveTo extends Motion {
        private Location to;

        public MoveTo(Location to) {
            this.to = to;
        }

        /**
         * Take arctan of angle, with result
         * being angle from 0 to 2pi in unit circle
         */
        private double absoluteAtan(double y, double x) {
            double angle = Math.atan(y / x);

            if (x < 0) angle += Math.PI;
            else if (y < 0) angle += 2.0 * Math.PI;

            return angle;
        }

        @Override
        public void accept(AutonState state) {
            double dX = to.getX() - state.getPosition().getLocation().getX();
            double dY = to.getY() - state.getPosition().getLocation().getY();

            double targetAngle = absoluteAtan(dY, dX);
            double delta = Math.abs(state.getPosition().getAngle() - targetAngle);

            if (delta > Math.PI) delta = 2.0 * Math.PI - delta;

            state.addMotion(new Rotation(delta));
            state.getPosition().setAngle(targetAngle);

            double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

            state.addMotion(new Drive(distance));
            state.getPosition().setLocation(to);            
        }
    }

    public static class LiftAndShoot extends Motion {
        @Override
        public void accept(AutonState state) {
            // TODO: Lift intake and then shoot ball (flywheels)
            Intake intake = state.getIntake();
        }
    }

    public static class SetIntake extends Motion {
        boolean on;

        public SetIntake(boolean on) { this.on = on; }

        @Override
        public void accept(AutonState state) {
            Intake intake = state.getIntake();

            if (on) intake.turnOn();
            else intake.turnOff();
        }
    }    
}
