package org.usfirst.frc.team5495.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Loader extends Subsystem {
    private CANTalon motor;
    
    private static final int LOADER_PORT = 10;
    private static final double SPEED = 1;

    public Loader () {
    	motor = new CANTalon(LOADER_PORT);
    	motor.setPIDSourceType(PIDSourceType.kRate);
    	motor.setPID(0.01, 0, 0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void load() {
    	motor.pidWrite(SPEED);
    }
    
    public void unload() {
    	motor.pidWrite(-SPEED);
    }
    public void stop() {
    	motor.set(0);
    }
}

