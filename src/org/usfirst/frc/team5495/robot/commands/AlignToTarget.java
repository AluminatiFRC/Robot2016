package org.usfirst.frc.team5495.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignToTarget extends CommandGroup {
	
    
    public AlignToTarget() {
    	addSequential(new TargetRotate());
    	addSequential(new TargetDrive());
    	addSequential(new TargetRotate());
    	addSequential(new RumbleCommand());
    }
}
