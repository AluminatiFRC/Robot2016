package org.usfirst.frc.team5495.robot.commands;

import org.usfirst.frc.team5495.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrawlMode extends Command {
	private boolean crawlMode;
	
    public CrawlMode(boolean crawl) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	crawlMode = crawl;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   	    Robot.drive.setCrawlEnabled(crawlMode);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}