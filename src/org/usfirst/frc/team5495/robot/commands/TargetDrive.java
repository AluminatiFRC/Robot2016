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
	private static final double TOLERANCE = 10;
	private Double distance;

	public TargetDrive() {
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

    	distance = ((Double) obj.get("targetDistance"));
    	
    	double movement = 0;
    	
    	if (distance > Robot.messageClient.getProperty("target/distance/max")){
    		movement = 1.0;
    	} else if (distance < Robot.messageClient.getProperty("target/distance/min")) {
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
        return (distance <= Robot.messageClient.getProperty("target/distance/max")) &&
        		(distance >= Robot.messageClient.getProperty("target/distance/min"));
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
