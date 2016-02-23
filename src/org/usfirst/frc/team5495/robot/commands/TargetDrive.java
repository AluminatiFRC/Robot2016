package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.usfirst.frc.team5495.PollingMessageClient;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TargetDrive extends Command {
    private static final double TARGET_DISTANCE = 140.0;
	private static final double TOLERANCE_IN = 4;
	private Double distanceOffset;

	public TargetDrive() {
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	JSONObject obj = Robot.messageClient.getJsonObject("5495.targetting");

    	boolean hasTarget = (Boolean)obj.get("hasTarget");
    	if (!hasTarget){
    		Robot.drive.drive(0, 0);
    		return;
    	}

    	distanceOffset = ((Double) obj.get("targetDistance")) - TARGET_DISTANCE;
    	
    	//Clamp the move amount so we can't go like a billion miles per hour, man
    	double moveAmount = distanceOffset * .009;
    	moveAmount = Math.max(moveAmount, -.2);
    	moveAmount = Math.min(moveAmount, .2);
    	
    	Robot.messageClient.publish("testing", String.valueOf(moveAmount));
    	
    	Robot.drive.drive(-moveAmount, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(distanceOffset) < TOLERANCE_IN;
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
