package org.usfirst.frc.team5495.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5495.PollingMessageClient;
import org.usfirst.frc.team5495.robot.commands.AlignToTarget;
import org.usfirst.frc.team5495.robot.commands.AutomaticLoad;
import org.usfirst.frc.team5495.robot.commands.CancelCommand;
import org.usfirst.frc.team5495.robot.commands.CrawlMode;
import org.usfirst.frc.team5495.robot.commands.TeleOpLoaderCommand;
import org.usfirst.frc.team5495.robot.commands.TeleOpShooterCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInterface {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    public static final int LOADER_STOP = 1;
	// Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	public Joystick driver;
	public Joystick operator;
	
	public OperatorInterface() {
		driver = new Joystick(0);
		operator = new Joystick(1);
		
		Button shooter = new JoystickButton(operator, 2); // Button B
		shooter.whileHeld(new TeleOpShooterCommand(true));
		
		Command loaderStartCommand = new AutomaticLoad();
		Button loader = new JoystickButton(operator, 1); // Button A
		loader.whenPressed(loaderStartCommand);
		loader.whenReleased(new CancelCommand(loaderStartCommand));
		
		Button loadManual = new JoystickButton(operator, 5); //Left Bumper
		loadManual.whileHeld(new TeleOpLoaderCommand(true));

		Button stopShooter = new JoystickButton(operator, 4); // Button X
		stopShooter.whileHeld(new TeleOpShooterCommand(false));
		
		Button operatorY = new JoystickButton(operator, 3); // Button Y
		operatorY.whileHeld(new TeleOpLoaderCommand(false));
		
		AlignToTarget alignCommand = new AlignToTarget();
		Button targetAlignEnable = new JoystickButton(driver, 7);
		targetAlignEnable.whenPressed(alignCommand);
		Button targetAlignDisable = new JoystickButton(driver, 8);
		targetAlignDisable.cancelWhenPressed(alignCommand);
		
		Button crawlEnable = new JoystickButton(driver, 2);
		crawlEnable.whenPressed(new CrawlMode(true));
		Button crawlDisable = new JoystickButton(driver, 11);
		crawlDisable.whenPressed(new CrawlMode(false));		
	}
}
