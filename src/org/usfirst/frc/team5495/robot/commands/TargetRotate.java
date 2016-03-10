package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TargetRotate extends Command {
	private double offset;

	public TargetRotate() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	JSONObject obj = Robot.messageClient.getJsonObject("robot/vision/telemetry");
    	
    	boolean hasTarget = (Boolean)obj.get("hasTarget");
    	if (!hasTarget){
    		Robot.drive.drive(0, 0);
    		return;
    	}
    
    	double distance = (Double) obj.get("targetDistance");
    	//Values sampled physically
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
        return Math.abs(offset) < Robot.messageClient.getProperty("target/rotation/tolerance");
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
