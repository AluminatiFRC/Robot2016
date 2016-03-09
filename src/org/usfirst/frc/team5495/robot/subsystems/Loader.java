package org.usfirst.frc.team5495.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Loader extends Subsystem {
    private CANTalon motor;
    private AnalogInput ballSensor;
    
    private static final int LOADER_PORT = 10;
    private static final int SENSOR_PORT = 3;
    private static final double SPEED = 1;

    public Loader () {
    	motor = new CANTalon(LOADER_PORT);
    	motor.setPIDSourceType(PIDSourceType.kRate);
    	motor.setPID(0.1, 0, 0);
    	ballSensor = new AnalogInput(SENSOR_PORT);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getSensorVoltage() {
    	return ballSensor.getAverageVoltage();
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

