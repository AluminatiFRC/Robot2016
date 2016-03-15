package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.DualMotorPID;
import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.commands.TeleOpDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private RobotDrive drive;
	private Encoder leftEncoder;
	private Encoder rightEncoder;

	private PIDController leftPID;
	private PIDController rightPID;

	private static final int DRIVE_PORT_FRONT_LEFT = 0;
	private static final int DRIVE_PORT_REAR_LEFT = 1;
	private static final int DRIVE_PORT_FRONT_RIGHT = 2;
	private static final int DRIVE_PORT_REAR_RIGHT = 3;
	
	private boolean isCrawlEnabled = false;

	public DriveTrain() {
		VictorSP frontLeft = new VictorSP(DRIVE_PORT_FRONT_LEFT);
		VictorSP rearLeft = new VictorSP(DRIVE_PORT_REAR_LEFT);
		VictorSP frontRight = new VictorSP(DRIVE_PORT_FRONT_RIGHT);
		VictorSP rearRight = new VictorSP(DRIVE_PORT_REAR_RIGHT);
		
		drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

		leftEncoder = new Encoder(DRIVE_PORT_FRONT_LEFT, DRIVE_PORT_REAR_LEFT);
		leftEncoder.setDistancePerPulse((8 * Math.PI) / 360);
		leftEncoder.setPIDSourceType(PIDSourceType.kRate);
		rightEncoder = new Encoder(DRIVE_PORT_FRONT_RIGHT, DRIVE_PORT_REAR_RIGHT);
		rightEncoder.setDistancePerPulse((8 * Math.PI) / 360);
		rightEncoder.setPIDSourceType(PIDSourceType.kRate);

		///PID Stuff. It's not working, but leaving it here just in case
		
//		double p = .7;
//		double i = 0;
//		double d = 0;
//		
//		//Move the scaling to the PID controller, through the parameters
//		double MAX_SPEED = 120.0;
//		p /= MAX_SPEED;
//		i /= MAX_SPEED;
//		d /= MAX_SPEED;

		
//		leftPID = new PIDController(p, i, d, leftEncoder, new DualMotorPID(frontLeft, rearLeft));
//		leftPID.setOutputRange(-120, 120);
//		leftPID.setInputRange(-120, 120);
//		rightPID = new PIDController(p, i, d, rightEncoder, new DualMotorPID(frontRight, rearRight));
//		rightPID.setInputRange(-180, 180);
//		rightPID.setOutputRange(-.5, .5);
		
//		leftPID.enable();
//		rightPID.enable();
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

	public void drive(double moveValue, double rotateValue) {
		if (isCrawlEnabled) {
			double crawlScaling = Robot.messageClient.getProperty("drive/crawl/speed",.5);
			moveValue *= crawlScaling;
			rotateValue *= crawlScaling;
		}
		drive.arcadeDrive(moveValue, rotateValue);
	}
	
	public void setCrawlEnabled(boolean crawl) {
		isCrawlEnabled = crawl;
	}
	
//	public void drive(double moveValue, double rotateValue) {
//		//Copied from RobotDrive::arcadeDrive
//		double leftMotorSpeed, rightMotorSpeed;
//		
//		if (moveValue > 0.0) {
//			if (rotateValue > 0.0) {
//				leftMotorSpeed = moveValue - rotateValue;
//				rightMotorSpeed = Math.max(moveValue, rotateValue);
//			} else {
//				leftMotorSpeed = Math.max(moveValue, -rotateValue);
//				rightMotorSpeed = moveValue + rotateValue;
//			}
//		} else {
//			if (rotateValue > 0.0) {
//				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
//				rightMotorSpeed = moveValue + rotateValue;
//			} else {
//				leftMotorSpeed = moveValue - rotateValue;
//				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
//			}
//		}
//	
//		Robot.messageClient.publish("test", "left encoder:" + leftEncoder.getRate() + " " + leftEncoder.getPIDSourceType() + leftEncoder.getDistance());
//		Robot.messageClient.publish("test", "right encoder:" + rightEncoder.getRate());
//		Robot.messageClient.publish("test", "error:" + leftPID.getError() + " avg: " + leftPID.getAvgError());
//		
//		leftPID.setSetpoint(leftMotorSpeed * 120);
//		.setSetpoint(rightMotorSpeed);
//	}
}
