package org.usfirst.frc.team3859.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climb {
	//boolean for whether or not the PTO is engaged
	boolean isEngaged = false;
	
	//sets the PTO to either engaged or disengaged
	public void isPTOEngaged(boolean state) {
		if (state == true) {
			Constants.PTO.set(Value.kReverse);
			isEngaged = true;
		} else {
			Constants.PTO.set(Value.kForward);
			isEngaged = false;
		}

	}

}
