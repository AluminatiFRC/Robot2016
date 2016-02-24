package org.usfirst.frc.team5495.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CancelCommand extends Command {
	Command commandToCancel;
	
    public CancelCommand(Command commandToCancel) {
    	this.commandToCancel = commandToCancel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	commandToCancel.cancel();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
