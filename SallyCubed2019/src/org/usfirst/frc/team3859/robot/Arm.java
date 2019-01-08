package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.armPos;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	// pid loop for the arm
	PIDcontrol armPID = new PIDcontrol(0, 0, 0);
	boolean init = false;
	boolean armInterlock = false;
	double error = 0;

	// whether the arm is at its postion or not(used for autonomous)
	boolean done = false;

	double P = 0;
	double I = 0;
	double D = 0;

	double voltage = .14;

	// sets up the arm motors to be in the correct states
	public void setUp() {
		// Constants.armLeft.set
		Constants.armRight.setInverted(true); // right arm must be inverted to match
		// left arm
		// Constants.armRight.follow(Constants.armLeft); // slaves the right arm to the
		// left arm
		Constants.armLeft.setSensorPhase(true); // sets sensor to output positive
		// values
	}

	// gets the position of the arm in terms of degreess
	double getPosition() {
		// calls arm set up code
		setUp();
		double position = ((Constants.armSensor.getQuadraturePosition() / 366) * 90) / 1024;
		SmartDashboard.putNumber("Arm Position", position);
		return position;
	}

	public double getError() {
		return error;
	}

	public void set(double goalAngle) {
		error = goalAngle + getPosition();

		double P = 0.33;
		double I = 0;
		double D = 0;
		armPID.setPID(P, I, D);
		double speed = armPID.calculate(error);
		if (error >= 0) {
			Constants.armLeft.set(ControlMode.PercentOutput, (P * -1 * error) / 12.);
			Constants.armRight.set(ControlMode.PercentOutput, (P * -1 * error) / 12.);
		} else if (error < 0) {
			Constants.armLeft.set(ControlMode.PercentOutput, (P * -.32 * error) / 12.);
			Constants.armRight.set(ControlMode.PercentOutput, (P * -.32 * error) / 12.);
		}

//		if (error >= 0) {
//			Constants.armLeft.set(ControlMode.PercentOutput, -speed);
//			Constants.armRight.set(ControlMode.PercentOutput, -speed);
//		} else if (error < 0) {
//			Constants.armLeft.set(ControlMode.PercentOutput, -speed * .32);
//			Constants.armRight.set(ControlMode.PercentOutput, -speed * .32);
//		}

		if (Math.abs(error) < 4) {
			done = true;
		} else if (Math.abs(error) >= 4) {
			done = false;
		}

	}

}
