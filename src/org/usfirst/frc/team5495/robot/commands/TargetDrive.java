package org.usfirst.frc.team5495.robot.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.usfirst.frc.team5495.MessageClient;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TargetDrive extends Command {
	private static final double TOLERANCE = 10;
	private Double distance;
	
	private static final double MAX_DISTANCE = 185;
	private static final double MIN_DISTANCE = 170;

	public TargetDrive() {
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

    	distance = ((Double) obj.get("targetDistance"));
    	
    	if (distance > 9999){
    		Robot.drive.drive(0, 0);
    		System.out.println("Insanely huge distance");
    		return;
    	}
    	
    	double movement = 0;
    	
    	if (distance > MAX_DISTANCE){
    		movement = 1.0;
    	} else if (distance < MIN_DISTANCE) {
    		movement = -1.0;
    	}
    	
    	//Clamp the move amount so we can't go like a billion miles per hour, man
    	double moveAmount = movement * .4;
    	moveAmount = Math.max(moveAmount, -.4);
    	moveAmount = Math.min(moveAmount, .4);
    	
    	Robot.messageClient.publish("test", "drive: " + String.valueOf(moveAmount));
    	
    	Robot.drive.drive(-moveAmount, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("TargetDrive distance:" + distance);
        return (distance <= MAX_DISTANCE) &&
        		(distance >= MIN_DISTANCE);
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
