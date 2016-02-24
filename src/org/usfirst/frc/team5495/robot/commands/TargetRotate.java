package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.usfirst.frc.team5495.PollingMessageClient;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TargetRotate extends Command {
	private JSONParser parser;
	private double offset;

	public TargetRotate() {
    	parser = new JSONParser();

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
    	
    	
    	double distance = (Double) obj.get("targetDistance");
    	//Values sampled
    	double horizError = (distance * .001406397) - .3897812794; 
    	offset = (Double) obj.get("horizDelta") - horizError;
    	
    	//Clamp
    	double rotation = Math.max(offset * 3.0, -.35);
    	rotation = Math.min(rotation, .35);
    	
    	Robot.messageClient.publish("test", "rotation: " + String.valueOf(rotation));
    	
    	Robot.drive.drive(0, -rotation);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(offset) < .02;
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
