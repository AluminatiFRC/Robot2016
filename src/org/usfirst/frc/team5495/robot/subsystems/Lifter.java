package org.usfirst.frc.team5495.robot.subsystems;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lifter extends Subsystem {
    private TalonSRX motor;
    
//    private static final int LIFTER_PORT = 4;

    public Lifter() {
//    	motor = new TalonSRX(LIFTER_PORT);
    }
    
    public void initDefaultCommand() {
       
    }
    
    public void setPosition(double angle) {
    	//WIP, needs potentiometer
    }
}

