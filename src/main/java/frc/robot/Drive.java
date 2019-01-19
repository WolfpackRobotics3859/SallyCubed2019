package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drive {

	Joystick xbox1 = new Joystick(0);
	// FALCON PLOT STUFF


	int i = 0;
	// VELOCITY PID STUFF
	static int PIDSlot = 0;
	static double P = 0;
	static double I = 0;
	static double D = 0;
	static double F = 0;
	public int kTimeoutMs = 30;


	public double leftVelocity = 0;
	public double rightVelocity = 0;

	// right drive
	static TalonSRX rightFront = new TalonSRX(6);
	static VictorSPX rightMiddle = new VictorSPX(7);
	static VictorSPX rightBack = new VictorSPX(8);

	// left drive
	static TalonSRX leftFront = new TalonSRX(1);
	static VictorSPX leftMiddle = new VictorSPX(2);
	static VictorSPX leftBack = new VictorSPX(3);

	public void setUp() {

		// SET UP ENCODER FOR VELOCITY
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDSlot, kTimeoutMs);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDSlot, kTimeoutMs);

		// HAVE SLAVE MOTORS FOLLOW FRONT MOTOR
		leftMiddle.follow(leftFront);
		leftBack.follow(leftFront);

		rightMiddle.follow(rightFront);
		rightBack.follow(rightFront);

		// INVERT MOTORS SO THEY GO RIGHT WAY
		leftFront.setInverted(true);
		leftMiddle.setInverted(true);
		leftBack.setInverted(true);
		rightFront.setInverted(true);
		rightMiddle.setInverted(true);
		rightBack.setInverted(true);

		// PUT THEM ON BRAKE MODE
		leftFront.setNeutralMode(NeutralMode.Brake);
		leftMiddle.setNeutralMode(NeutralMode.Brake);
		leftBack.setNeutralMode(NeutralMode.Brake);
		rightFront.setNeutralMode(NeutralMode.Brake);
		rightMiddle.setNeutralMode(NeutralMode.Brake);
		rightBack.setNeutralMode(NeutralMode.Brake);

	}

	public void leftDriveSet(double speed) {
		leftFront.set(ControlMode.PercentOutput, speed);
	}

	public void rightDriveSet(double speed) {
		rightFront.set(ControlMode.PercentOutput, speed);
	}

	// VELOCITY PID STUFF

	public void leftSetPID(double kP, double kI, double kD, double kF) {
		leftFront.config_kF(PIDSlot, kF, kTimeoutMs);
		leftFront.config_kP(PIDSlot, kP, kTimeoutMs);
		leftFront.config_kI(PIDSlot, kI, kTimeoutMs);
		leftFront.config_kD(PIDSlot, kD, kTimeoutMs);
	}

	public void rightSetPID(double kP, double kI, double kD, double kF) {
		rightFront.config_kF(PIDSlot, kF, kTimeoutMs);
		rightFront.config_kP(PIDSlot, kP, kTimeoutMs);
		rightFront.config_kI(PIDSlot, kI, kTimeoutMs);
		rightFront.config_kD(PIDSlot, kD, kTimeoutMs);
	}

	public void autoDrive() {

		//leftSetPID(kP, kI, kD, kF);
		//rightSetPID(kP, kI, kD, kF);
		double fieldWidth = 27;
		double switchLength = 12.792;
		double switchWidth = 4.67;

		double[][] autoPathCoordinates = new double[][] { { 0, fieldWidth / 2 }, { 1, fieldWidth / 2 + .25 },
				{ 3, fieldWidth / 2 + .5 }, { 4.5, fieldWidth / 2 + 1.25 }, { 7, fieldWidth / 2 + 3.25 },
				{ 9, fieldWidth / 2 + 4 }, { 11, fieldWidth / 2 + 4 } };

		final FalconPathPlanner autoPath = new FalconPathPlanner(autoPathCoordinates);

		double totalTime = 5;
		double timeStep = .01; //
		double robotTrackWidth = 2.3; // could be 2.1

		double maxVel = 50;

		autoPath.calculate(totalTime, timeStep, robotTrackWidth);

		if(i <= autoPath.smoothLeftVelocity.length){
			leftVelocity = autoPath.smoothLeftVelocity[i][1];
			rightVelocity = autoPath.smoothRightVelocity[i][1];
		}else{
			leftVelocity = 0;
			rightVelocity = 0;
		}

		leftFront.set(ControlMode.Velocity,leftVelocity);
		rightFront.set(ControlMode.Velocity,rightVelocity);

	}

	public void xboxDrive(){
		setUp();

		leftDriveSet(xbox1.getY(Hand.kLeft));
		rightDriveSet(xbox1.getY(Hand.kRight));

	}
}
