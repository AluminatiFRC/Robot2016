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
    	
    	boolean hasTarget = (Boolean)obj.getOrDefault("hasTarget",false);
    	if (!hasTarget){
    		Robot.drive.drive(0, 0);
    		return;
    	}
    
    	double distance = (Double) obj.get("targetDistance");
    	
    	if (distance > 9999){
    		Robot.drive.drive(0, 0);
    		System.out.println("Insanely huge distance");
    		return;
    	}
    	
    	//Values sampled physically
    	double horizError = (distance * .001406397) - .3897812794; 
//    	double offsetCorrection = .045;
    	double offsetCorrection = 0;
    	offset = (Double) obj.get("horizDelta") - horizError + offsetCorrection;
    	
    	//Clamp
    	double rotation = Math.max(offset * 10.0, -.45);
    	rotation = Math.min(rotation, .45);
    	
    	Robot.drive.drive(0, -rotation);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("TargetRotate offset:" + offset);
        return Math.abs(offset) < Robot.messageClient.getProperty("target/rotation/tolerance",.032);
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
