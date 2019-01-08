package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;

public class Constants {

	static Compressor c = new Compressor(0);

	// INTAKE
	enum position {
		INTAKE, SCORE_HARD, SCORE_LOW, SCORE_MEDIUM, SCORE_SUPERPUNCH, DISABLE, DEJAM;
	}

	enum pnuematic {
		kForward, kReverse;
	}

	// ARM

	enum armPos {
		INTAKE, SWITCHSHOT, BACKSHOT, TEST, STARTINGCONFIG;
	}

	// right drive
	static TalonSRX rightFront = new TalonSRX(6);
	static VictorSPX rightMiddle = new VictorSPX(7);
	static VictorSPX rightBack = new VictorSPX(8);

	// left drive
	static TalonSRX leftFront = new TalonSRX(1);
	static VictorSPX leftMiddle = new VictorSPX(2);
	static VictorSPX leftBack = new VictorSPX(3);

	// cool stuff
	static VictorSPX blackIntake = new VictorSPX(5);
	static VictorSPX blueIntake = new VictorSPX(10);
	//
	static TalonSRX armLeft = new TalonSRX(9);
	static VictorSPX armRight = new VictorSPX(4);
	//
	static VictorSPX roller = new VictorSPX(11);
	//
	static DoubleSolenoid intakeCompress = new DoubleSolenoid(0, 6, 3);

	static DoubleSolenoid shootPneumatic0 = new DoubleSolenoid(1, 6, 7);
	static DoubleSolenoid climbPole = new DoubleSolenoid(0, 7, 5);// need to get values
	static DoubleSolenoid climbFeet = new DoubleSolenoid(1, 1, 0);// need to get values
	static DoubleSolenoid reDeployClimb = new DoubleSolenoid(1, 2, 3);// need to
	// get values

	static Solenoid intakeCompress2 = new Solenoid(1, 4);

	// static Solenoid test1 = new Solenoid(1, 0);
	// static Solenoid test2 = new Solenoid(1, 1);
	// static Solenoid test3 = new Solenoid(1, 2);
	// static Solenoid test4 = new Solenoid(1, 3);

	/**
	 * ENGAGED = REVERSE; DISENGAGED = FORWARD;
	 */
	static DoubleSolenoid PTO = new DoubleSolenoid(0, 0, 4);

	// eh

	static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	static AnalogInput sharp = new AnalogInput(0);
	static DigitalInput limitSwitch = new DigitalInput(9);

	static SensorCollection armSensor = new SensorCollection(Constants.armLeft);

	static SensorCollection rightEncSensor = new SensorCollection(Constants.rightFront);
	static SensorCollection leftEncSensor = new SensorCollection(Constants.leftFront);

	static XboxController xbox1 = new XboxController(5);
	static XboxController xbox2 = new XboxController(4);
	//
	static Joystick joystick1 = new Joystick(2);
	static Joystick driveWheel = new Joystick(3);

	static Joystick launchPad0 = new Joystick(0);
	static Joystick launchPad1 = new Joystick(1);
	// VARIABLES

	static boolean armUp = Constants.launchPad0.getRawButton(6);
	static boolean armDown = Constants.launchPad0.getRawButton(8);

	static double wheelDiameter = 6;
	static double wheelCircumference = (wheelDiameter) * Math.PI;

	static double intake = 0;
	static double switchShot = 47;
	static double scaleShot = 60;
	// static double backShot = 120;

	static boolean xboxYes = false;

}