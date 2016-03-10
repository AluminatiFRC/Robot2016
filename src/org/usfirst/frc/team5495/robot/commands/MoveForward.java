package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveForward extends Command {
	public double distanceInches;
	private double turnAmount;
	
    public MoveForward(double distanceInches, double turnAmount) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.distanceInches = distanceInches;
    	this.turnAmount = turnAmount;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Moving forward...");
    	Robot.drive.drive(-.65, turnAmount);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("" + Robot.drive.rightDistance());
        if ((Robot.drive.rightDistance() <= -distanceInches)&&
        		(Robot.drive.leftDistance() <= -distanceInches)) {
        	return true;
        } else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
