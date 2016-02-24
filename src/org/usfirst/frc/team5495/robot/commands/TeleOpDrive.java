package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleOpDrive extends Command {
	double TWISTFACTOR = 0.4;
	
    public TeleOpDrive() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double forward = Robot.oi.driver.getAxis(Joystick.AxisType.kY); 
    	double turn = -Robot.oi.driver.getAxis(Joystick.AxisType.kX);
    	double twist = -Robot.oi.driver.getAxis(Joystick.AxisType.kTwist);
    	
    	forward = deadzone(forward, .2);
    	turn = deadzone(turn + twist * TWISTFACTOR, .2);
    	
    	Robot.drive.drive(forward, turn);
    }

    private double deadzone(double val, double zone) {
		if (Math.abs(val) < zone)
			return 0;
		return val;
	}

	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
