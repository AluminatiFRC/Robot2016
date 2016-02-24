package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.OperatorInterface;
import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutomaticLoad extends Command {
	private static final double VOLTAGE_THRESHOLD = .1315;
	private static final int THRESHOLD_COUNT = 5;
	private int thresholdCounter;
	
    public AutomaticLoad() {
    	requires(Robot.loader);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.loader.load();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {   	
    	if (sensorTripped()){
    		thresholdCounter++;
    	}else{
    		thresholdCounter = 0;
    	}
    	
    	Robot.messageClient.publish("robot/loader/ultrasonic", String.valueOf(Robot.loader.getSensorVoltage()));
    	
    	return thresholdCounter >= THRESHOLD_COUNT;
    }

	private boolean sensorTripped() {
		return Robot.loader.getSensorVoltage() <= VOLTAGE_THRESHOLD;
	}

    // Called once after isFinished returns true
    protected void end() {
    	Robot.loader.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
}
