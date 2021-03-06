package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RumbleCommand extends Command {

    public RumbleCommand() {
    	setTimeout(.3);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.oi.operator.setRumble(RumbleType.kLeftRumble, (float) .5);
    	Robot.oi.operator.setRumble(RumbleType.kRightRumble, (float) .5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.oi.operator.setRumble(RumbleType.kLeftRumble, (float) 0);
    	Robot.oi.operator.setRumble(RumbleType.kRightRumble, (float) 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
