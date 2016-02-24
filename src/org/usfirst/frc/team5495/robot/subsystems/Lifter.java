package org.usfirst.frc.team5495.robot.subsystems;

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
    private PIDController motorPID;
    private Potentiometer pot;
    
    public Lifter() {
    	motor = new TalonSRX(LIFTER_PORT);
    	pot = new AnalogPotentiometer(4);
    	motorPID = new PIDController(.7, 0, 0, pot, motor);
    	motorPID.setOutputRange(-.5, .5);
    	motorPID.setInputRange(.25,  .75);
//    	motorPID.enable();
    }
    
    public void initDefaultCommand() {
    	setDefaultCommand(new LifterControl());       
    }
    
    public void setPosition(double angle) {
    	motorPID.setSetpoint(angle);
    }

    public void adjustPosition(double angleDelta) {
    	motorPID.setSetpoint(motorPID.getSetpoint() + angleDelta);
    }
}

