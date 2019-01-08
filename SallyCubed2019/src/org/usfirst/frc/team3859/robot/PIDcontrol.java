package org.usfirst.frc.team3859.robot;

public class PIDcontrol {
	double kP, kI, kD, PrevError = 0, Error = 0, result, deltaError, sum = 0, minPower = 0, maxPower = 0;

	// constructor for PIDControl
	/**
	 * 
	 * @param P
	 * @param I
	 * @param D
	 */
	PIDcontrol(double P, double I, double D) {
		kP = P;
		kI = I;
		kD = D;
	}

	// SETS THE PID FOR THE PID LOOOOOOOOOOOOOP
	/**
	 * @param P
	 * @param I
	 * @param D
	 */
	public void setPID(double P, double I, double D) {
		kP = P;
		kI = I;
		kD = D;
	}

	public void setMinPower(double minPower) {
		this.minPower = minPower;

	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;

	}

	// calculates the output of the PID loop
	public double calculate(double error) {
		Error = error;
		sum += Error;
		deltaError = Error - PrevError;

		result = (kP * error) + (kI * sum) + (kD * deltaError);
		PrevError = error;

		// if (result >= Math.abs(this.maxPower)) {
		// result = this.maxPower;
		// } else if (result <= Math.abs(this.minPower)) {
		// result = this.minPower;
		// }

		return result;
	}

}
