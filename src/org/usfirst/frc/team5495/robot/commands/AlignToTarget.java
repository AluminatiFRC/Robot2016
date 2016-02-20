package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.PollingMessageClient;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignToTarget extends CommandGroup {
	
    
    public AlignToTarget() {
    	//addSequential(new TargetRotate(client));
    	addSequential(new TargetDrive());
    }
}
