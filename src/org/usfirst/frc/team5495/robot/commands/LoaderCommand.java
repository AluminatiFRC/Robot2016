package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * load and unloads the ball. takes a boolean when called, where true loads the
 * ball and false unloads the ball. 
 * only ends when you call interrupted.
 * stops loader when command ends.
 * it requires the loader subsystem
 */
public class LoaderCommand extends Command {
	private boolean isForward;
	
    public LoaderCommand(boolean isForward) {
    	this.isForward = isForward;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.loader);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (isForward) {
    		Robot.loader.load();
    	} else {
    		Robot.loader.unload();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.loader.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
