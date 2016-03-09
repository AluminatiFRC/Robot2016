package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.MotorFeedback;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	private CANTalon motorR;
	private CANTalon motorL;
	private PIDController motorRPID;
	private PIDController motorLPID;
	
	public static final int SHOOTER_PORT_R = 12;
	public static final int SHOOTER_PORT_L = 11;

	public Shooter() {
		 motorR = new CANTalon(SHOOTER_PORT_R);
		 motorL = new CANTalon(SHOOTER_PORT_L);
		 motorRPID = new PIDController(.3, 0, 0, new MotorFeedback(motorR), motorR);
		 motorRPID.enable();
		 motorLPID = new PIDController(.3, 0, 0, new MotorFeedback(motorL), motorL);
		 motorLPID.enable();
		 //motorR.changeControlMode(TalonControlMode.Voltage);
	}
	
	//METHOD
	public void spinForward() {
		motorRPID.setSetpoint(-1);
		motorLPID.setSetpoint(1);	
	}
	
	public void spinBack() {
		motorRPID.setSetpoint(1);
		motorLPID.setSetpoint(-1);
	}
	
	//METHOD
	public void stop() {
		motorRPID.setSetpoint(0);
		motorLPID.setSetpoint(0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
