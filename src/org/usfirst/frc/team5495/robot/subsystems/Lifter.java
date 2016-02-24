package org.usfirst.frc.team5495.robot.subsystems;

import org.usfirst.frc.team5495.robot.Robot;
import org.usfirst.frc.team5495.robot.commands.LifterControl;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class Lifter extends Subsystem {
    private TalonSRX motor;
    private static final int LIFTER_PORT = 4;
    
    private static final double MAX_POT_VALUE = .68;
    private static final double MIN_POT_VALUE = .10;
    private static final double MAX_ANGLE = 160;
    private static final double MIN_ANGLE = 0;
    
    private PIDController motorPID;
    private Potentiometer pot;
    private double targetAngle = 80;
    
    public Lifter() {
    	motor = new TalonSRX(LIFTER_PORT);
    	motor.setInverted(true);
    	pot = new AnalogPotentiometer(0);
    	motorPID = new PIDController(.8, 0, 0, pot, motor);
    	motorPID.setOutputRange(-1, 1);
    	motorPID.setInputRange(MIN_POT_VALUE,  MAX_POT_VALUE);
    	motorPID.enable();
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new LifterControl());       
    }
    
    public void setPosition(double angle) {
//    	Robot.messageClient.publish("robot/lifter/pot/value", String.valueOf(pot.get()));
//    	Robot.messageClient.publish("robot/lifter/pot/targetvalue", String.valueOf(motorPID.getSetpoint()));
//    	Robot.messageClient.publish("robot/lifter/motor/targetangle", String.valueOf(targetAngle));
//    	Robot.messageClient.publish("robot/lifter/motor/output", String.valueOf(motorPID.get()));
    	
    	targetAngle = angle;
    	
    	targetAngle = Math.max(MIN_ANGLE, targetAngle);
    	targetAngle = Math.min(targetAngle, MAX_ANGLE);
    	
    	motorPID.setSetpoint(angleToPot(targetAngle));
    }

    public void adjustPosition(double angleDelta) {
    	setPosition(targetAngle + angleDelta);
    }
    
    public double angleToPot(double value){
    	//Normalize angle
    	value -= MIN_ANGLE;
    	value /= MAX_ANGLE - MIN_ANGLE;
    	
    	//Denormalize into pot values
    	value *= MAX_POT_VALUE - MIN_POT_VALUE;
    	value += MIN_POT_VALUE;
    	
    	return value;
    }
}

