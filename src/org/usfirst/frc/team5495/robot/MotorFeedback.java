package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public class MotorFeedback implements PIDSource{
	private SpeedController motor;
	
	public MotorFeedback(SpeedController motor){
		this.motor = motor;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		return motor.get();
	}

}
