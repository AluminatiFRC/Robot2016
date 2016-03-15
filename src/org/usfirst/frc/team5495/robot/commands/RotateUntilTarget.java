package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateUntilTarget extends Command {

	public RotateUntilTarget() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.drive(0, -Robot.messageClient.getProperty("target/rotation/speed", .4));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		JSONObject obj = Robot.messageClient.getJsonObject("robot/vision/telemetry");

		return (Boolean) obj.get("hasTarget");		
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
