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
    private PollingMessageClient client;
	private JSONParser parser;
	private double offset;

	public TargetRotate(PollingMessageClient client) {
    	this.client = client;
    	parser = new JSONParser();

    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	JSONObject obj = client.getJsonObject("5495.targetting");
    	
    	boolean hasTarget = (Boolean)obj.get("hasTarget");
    	if (!hasTarget){
    		Robot.drive.drive(0, 0);
    		return;
    	}
    	offset = (Double) obj.get("horizDelta");
    	

    	double rotation = Math.max(offset, -.4);
    	rotation = Math.min(rotation, .4);
    	
    	Robot.drive.drive(0, rotation * -0.8);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(offset) < .07;
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
