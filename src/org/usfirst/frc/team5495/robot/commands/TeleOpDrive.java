package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleOpDrive extends Command {

    public TeleOpDrive() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double forward = Robot.oi.driver.getRawAxis(1); 
    	double turn = -Robot.oi.driver.getRawAxis(0);
    	
    	forward = deadzone(forward, .2);
    	turn = deadzone(turn, .2);
    	
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
