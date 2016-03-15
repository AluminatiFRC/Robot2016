package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TargetRotate extends Command {
	private double offset;
	
	//Major is the large rotation phase, and minor is the small
	private double MAJOR_SPEED = .50;
	private double MINOR_SPEED = .25;
	private double TRANSITION_ERROR = .35;
	private double TOLERANCE = .02;
	
	PIDController rotationPID;
	FloatInput errorInput;
	
	
	public TargetRotate() {
    	requires(Robot.drive);
    	
    	
    	
    	rotationPID = new PIDController(.7, 0, .2, errorInput, rotateOutput);
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

    	double speed = 0;
    	if (Math.abs(offset) > TRANSITION_ERROR){
    		speed = MAJOR_SPEED;
    	} else {
    		speed = MINOR_SPEED;
    	}
    	
    	if (offset < 0) {
    		speed = -speed;
    	}
    	
    	if (Math.abs(offset) < TOLERANCE) {
    		speed = 0;
    	}
    	
    	System.out.println(speed);
    	
    	Robot.drive.drive(0, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("TargetRotate offset:" + offset);
        return Math.abs(offset) < TOLERANCE;
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
