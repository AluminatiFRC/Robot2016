package org.usfirst.frc.team5495.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *jtdcjkhkhaosuhdfRandomhafhuefuLettersvivhrihohfhcdjyf
 */
public class Shooter extends Subsystem {
	private CANTalon motorR;
	private CANTalon motorL;
	
	public static final int SHOOTER_PORT_R = 11;
	public static final int SHOOTER_PORT_L = 12;

	public Shooter() {
		 motorR = new CANTalon(SHOOTER_PORT_R);
		 motorL = new CANTalon(SHOOTER_PORT_L);
	}
	
	//METHOD
	public void spinUp() {
		motorR.set(1);
		motorL.set(-1);
		System.out.println("Spinnnning");
	}
	
	public void spinBack() {
		motorR.set(-1);
		motorL.set(1);
		System.out.println("Spinnnningback");
	}
	
	//METHOD
	public void spinDown() {
		motorR.set(0);
		motorL.set(0);
		System.out.println("Stopppppped");
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
