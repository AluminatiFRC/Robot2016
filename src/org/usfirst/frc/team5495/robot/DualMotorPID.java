package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class DualMotorPID implements PIDOutput{
	private SpeedController motorA;
	private SpeedController motorB;
	
	private double integration;
	
	public DualMotorPID(SpeedController a, SpeedController b){
		motorA = a;
		motorB = b;
	}
		
	@Override
	public void pidWrite(double output) {
		integration += output;
		motorA.set(integration/240);
		motorB.set(integration/240);
	}
}
