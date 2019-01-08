package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Timer autoTimer = new Timer();
	Autonomous auto = new Autonomous(); // declare Autonomous object to be used for Autonomous Mode

	// String automode;
	// String autoSelected;

	char switchSide; // the alliance switch side coming from the field
	char scaleSide; // the alliance scale side coming from the field

	boolean init = false; // used to run custom initialization once

	SendableChooser<String> robotStartPosition = new SendableChooser<String>(); // used to set robot start position

	SendableChooser<String> autoChoice = new SendableChooser<String>(); // autonomous choice selection

	Wolfpack pathfinder = new Wolfpack();
	// UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);

	// Strings used as auto.autoset() selector
	final String middleAuto = "Middle";
	final String leftAuto = "Left";
	final String rightAuto = "Right";
	final String turnPID = "PID turn";
	String gameData;

	// public int percent = TalonSRX.ControlMode.PercentOutput();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		// choices displayed on the smartdashboard for autoChoice
		autoChoice.addDefault("Deliver", "deliver");
		autoChoice.addObject("Cross", "cross");
		autoChoice.addObject("Test", "test");
		// autoChoice.addObject("the big one...", "the big one...");

		// choices displayed on the smartdashboard for robotStartPosition
		robotStartPosition.addDefault("Default", "default");
		robotStartPosition.addObject("Middle ", middleAuto);
		robotStartPosition.addObject("Left", leftAuto);
		robotStartPosition.addObject("Right", rightAuto);

		// displaying the robotStartPosition onto the smartdashboard
		SmartDashboard.putData("AutoMode", robotStartPosition);

		// displaying the autoChoice onto the smartdashboard
		SmartDashboard.putData("Auto Choice", autoChoice);

		// starting the compressor until it reaches a certain limit
		Constants.c.setClosedLoopControl(true);

		// displaying the camera view on the smartdashboard
		CameraServer.getInstance().startAutomaticCapture(0);

		SmartDashboard.putNumber("P", 0);
		SmartDashboard.putNumber("I", 0);
		SmartDashboard.putNumber("D", 0);
	}

	@Override
	public void autonomousInit() {

		/*
		 * sets up the drive talons and victors(e.g. slaving victors to talons and
		 * inverting talons/victors) and putting them in brake mode
		 */
		auto.oi.drive.setUp(true);

		// resets the gyro current angle
		Constants.gyro.reset();

		gameData = DriverStation.getInstance().getGameSpecificMessage();
		switchSide = gameData.charAt(0);
		scaleSide = gameData.charAt(1);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		// auto.oi.arm.set(armPos.INTAKE);

		// using the sharp to determine whether or not we have a cube in our intake
		if (Constants.sharp.getValue() > 1500) {
			SmartDashboard.putBoolean("Cube Present?", true);
		} else {
			SmartDashboard.putBoolean("Cube Present?", false);
		}
		SmartDashboard.putBoolean("Limit Switch", Constants.limitSwitch.get());

		// outputting the position of the arm in degrees
		SmartDashboard.putNumber("Arm Position", -auto.oi.arm.getPosition());

		// getting alliance switch and scale positions to use for auto.autoSet()
		// SmartDashboard.putString("Game Data", gameData);

		// call auto routine and pass: robotStartPosition, autoChoice, switchSide,
		// scaleSide
		auto.autoset(robotStartPosition.getSelected(), autoChoice.getSelected(), switchSide, scaleSide);

		// outputs current state of the limit switch
		SmartDashboard.putBoolean("Limit Switch", Constants.limitSwitch.get());

		// outputs the current right drive encoder in inches
		SmartDashboard.putNumber("Right Distance", auto.oi.drive.getRightEncDistance());
	}

	// @Override
	// public void teleopInit(){
	//
	//
	// }

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		// int startAngle = 57;
		// startAngle = ((startAngle * 240) / 90) * 1024;

		// custom teleop initialization. this runs once
		if (init == false) {
			// Constants.armSensor.setQuadraturePosition(-242045, 200);
			// Constants.armSensor.setQuadraturePosition(0, 200);

			/*
			 * sets up the drive talons and victors(e.g. slaving victors to talons and
			 * inverting talons/victors) and putting them in coast mode
			 */
			auto.oi.drive.setUp(false);

			/*
			 * sets up the arm motors with inverts, slaves and setting the sensor input
			 * direction(i.e. if it goes up, the value outputed is negative / if it goes
			 * down, the value outputed is positive and vice versa)
			 */
			auto.oi.arm.setUp();

			// switch off custom init
			// makes sure this if statement only gets called once.
			init = true;
		}

		// enables teleop functionality
		auto.oi.enable();

		// determines whether or not the limit switch is triggered and sets the current
		// arm sensor to 0
		if (Constants.limitSwitch.get() == true) {
			Constants.armSensor.setQuadraturePosition(0, 200);
		}

		// outputs various values from the robot to smartdashboard like: arm position,
		// right drive encoder
		// in inches, left drive encoder in inches, gyro angle, left drive
		// encoder in raw units, right drive encoder in raw units, and the current state
		// of the limit switch
		SmartDashboard.putNumber("Arm Position", auto.oi.arm.getPosition());
		SmartDashboard.putNumber("Right Enc", auto.oi.drive.getRightEncDistance());
		SmartDashboard.putNumber("Gyro Angle", Constants.gyro.getAngle());
		SmartDashboard.putBoolean("Limit Switch", Constants.limitSwitch.get());

		// SmartDashboard.putNumber("", value)
		SmartDashboard.putNumber("Right Drive Voltage", Constants.rightFront.getMotorOutputVoltage());
		SmartDashboard.putNumber("Left Drive Voltage", Constants.leftFront.getMotorOutputVoltage());

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
