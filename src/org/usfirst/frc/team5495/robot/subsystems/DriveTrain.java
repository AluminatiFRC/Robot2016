package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.commands.TeleOpDrive;

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
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new TeleOpDrive());
    }
    
    public void drive (double moveValue, double rotateValue){
    	drive.arcadeDrive(moveValue, rotateValue);
    }
}
