package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.commands.TeleOpDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private RobotDrive drive;
	private Talon frontLeft;
	private Talon frontRight;
	private Talon rearLeft;
	private Talon rearRight;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private static final int DRIVE_PORT_FRONT_LEFT = 0;
	private static final int DRIVE_PORT_REAR_LEFT = 1;
	private static final int DRIVE_PORT_FRONT_RIGHT = 2;
	private static final int DRIVE_PORT_REAR_RIGHT = 3;
	
	public DriveTrain(){
		frontLeft = new Talon(DRIVE_PORT_FRONT_LEFT);
		rearLeft = new Talon(DRIVE_PORT_REAR_LEFT);
		frontRight = new Talon(DRIVE_PORT_FRONT_RIGHT);
		rearRight = new Talon(DRIVE_PORT_REAR_RIGHT);
		
		drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
		
		double p = 1.0;
		double i = 0;
		double d = 0;
		
		leftEncoder = new Encoder(DRIVE_PORT_FRONT_LEFT, DRIVE_PORT_REAR_LEFT);
		leftEncoder.setDistancePerPulse((8*Math.PI)/360);
		rightEncoder = new Encoder(DRIVE_PORT_FRONT_RIGHT, DRIVE_PORT_REAR_RIGHT);
		rightEncoder.setDistancePerPulse((8*Math.PI)/360);
	
	
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new TeleOpDrive());
    }
    
    public double leftDistance() {
    	return leftEncoder.getDistance();
    }
    
    public double rightDistance() {
    	return rightEncoder.getDistance();
    }
    
    public void resetDistance() {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public void drive(double moveValue, double rotateValue){
    	Robot.messageClient.publish("test", "right encoder: " + rightEncoder.get() + " left encoder: " + leftEncoder.get());    	
    	drive.arcadeDrive(moveValue, rotateValue);
    }
}

	