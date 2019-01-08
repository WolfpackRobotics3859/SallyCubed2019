//package org.usfirst.frc.team3859.robot;
//
//import org.usfirst.frc.team3859.robot.Constants.position;
//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.GenericHID.RumbleType;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class Intake {
//	Timer cubeTimer = new Timer();
//	boolean scoreInit = false;
//	boolean init = false;
//	boolean dejamInit = false;
//
//	// sets motors for intake
//	public void setBlackIntake(double power) {
//		Constants.cubeLeft.set(ControlMode.PercentOutput, power);
//	}
//
//	public void setBlueIntake(double power) {
//		Constants.cubeRight.set(ControlMode.PercentOutput, power);
//	}
//
//	public void rollerSet(double power) {
//		Constants.roller.set(ControlMode.PercentOutput, power);
//	}
//
//	//pneumatics inside of intake to punch the cube out when scoring
//	public void isSuperPunchOut(boolean isOut) {
//		if (isOut == true) { // OUT
//
//			// sets left solenoids outward
////			Constants.superPunch0.set(true);
////			Constants.superPunch2.set(true);
////
////			// sets right solenoids outward
////			Constants.superPunch1.set(false);
////			Constants.superPunch3.set(false);
//			
//			Constants.shootPneumatic0.set(Value.kReverse);
//
//
//		} else if (isOut == false) { /// IN
//
//			// sets left solenoids inward
////			Constants.superPunch0.set(false);
////			Constants.superPunch2.set(false);
////
////			// sets right solenoids inward
////			Constants.superPunch1.set(true);
////			Constants.superPunch3.set(true);
//			
//			Constants.shootPneumatic0.set(Value.kForward);
//
//		}
//
//	}
//
//	public void set(position state) {
//		switch (state) {
//		//intaking cubes state
//		case INTAKE:
//			//only intakes if cube is not in the robot
//			if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
//				setBlackIntake(0);
//				setBlueIntake(0);
//				rollerSet(0);
//			} else {
//				setBlackIntake(-0.75);
//				setBlueIntake(0.75);
//				rollerSet(0.45);
//			}
//			break;
//			
//			//outputs the cube with super punch and at full speed 
//		case SCORE_HARD:
//			//resets timer for the super punch
//			if (scoreInit == false) {
//				cubeTimer.reset();
//				scoreInit = true;
//			}
//			//only activates super punch for .5 seconds
//			if (cubeTimer.get() < .5) {
//				isSuperPunchOut(true);
//			} else {
//				isSuperPunchOut(false);
//			}
//			
//			setBlackIntake(1);
//			setBlueIntake(-1);
//			rollerSet(0);
//			break;
//			
//			//outputs the cube at 90 percent speed without the super punch
//		case SCORE_MEDIUM:
//			setBlackIntake(.9);
//			setBlueIntake(-.9);
//			rollerSet(0);
//			isSuperPunchOut(false);
//			break;
//			
//			//stops all motors for intake and super punch is disabled
//		case DISABLE:
//			scoreInit = false;
//			dejamInit = false;
//			if (init == false) {
//				cubeTimer.start();
//				init = true;
//			}
//			setBlackIntake(0);
//			setBlueIntake(0);
//			rollerSet(0);
//			isSuperPunchOut(false);
//			break;
//			
//			//reverses one side of the intake to dejam the cube in our robot when stuck
//		case DEJAM:
//			if (dejamInit == false) {
//				cubeTimer.reset();
//				dejamInit = true;
//			}
//			if (cubeTimer.get() < .2) {
//				setBlackIntake(.45);
//				setBlueIntake(.6);
//				rollerSet(.4);
//			} else {
//				if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
//					setBlackIntake(0);
//					setBlueIntake(0);
//					rollerSet(0);
//				} else {
//					setBlackIntake(-0.75);
//					setBlueIntake(0.75);
//					rollerSet(0.55);
//					// if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
//					// Constants.xbox1.setRumble(RumbleType.kLeftRumble, 1);
//					// } else {
//					// Constants.xbox1.setRumble(RumbleType.kLeftRumble, 0);
//					// }
//
//				}
//			}
//			isSuperPunchOut(false);
//			break;
//		}
//	}
//}
package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	Timer cubeTimer = new Timer();
	// Timer scoreTimer = new Timer(); 032518 jack shintaku unnecessary
	boolean scoreInit = false;
	boolean init = false;
	boolean dejamInit = false;
	boolean isMandibleCompressed = true;
	boolean intakeYeaah;
	String currentSuperPunchStep = "open intake";

	// function to compress the intake mandibles
	public void setIntakeState(String intakeState) {
		if (intakeState == "compressed") {
			Constants.intakeCompress.set(Value.kForward);
			Constants.intakeCompress2.set(true);
		} else if (intakeState == "uncompressed") {
			Constants.intakeCompress.set(Value.kReverse);
			Constants.intakeCompress2.set(false);
		} else if (intakeState == "neutral") {
			Constants.intakeCompress.set(Value.kForward);
			Constants.intakeCompress2.set(false);
		}
	}

	public void setBlackIntake(double power) {
		Constants.blackIntake.set(ControlMode.PercentOutput, -power);
	}

	public void setBlueIntake(double power) {
		Constants.blueIntake.set(ControlMode.PercentOutput, power);
	}

	public void rollerSet(double power) {
		Constants.roller.set(ControlMode.PercentOutput, power);
	}

	public void isSuperPunchOut(boolean state) {
		if (state == true) { // OUT
			// Constants.superPunch0.set(true);
			// Constants.superPunch2.set(true);
			// Constants.superPunch1.set(false);
			// Constants.superPunch3.set(false);
			Constants.shootPneumatic0.set(Value.kReverse);
		} else if (state == false) { /// IN
			// Constants.superPunch0.set(false);
			// Constants.superPunch2.set(false);
			// Constants.superPunch1.set(true);
			// Constants.superPunch3.set(true);
			Constants.shootPneumatic0.set(Value.kForward);
		}

	}

	public void set(position state) {
		switch (state) {
		case INTAKE:
			cubeTimer.reset(); // 032518 jack shintaku may not be needed
			setBlackIntake(1);
			setBlueIntake(0.4);
			rollerSet(0.22);
			// isSuperPunchOut(false); 032518 jack shintaku no need
			// if (SmartDashboard.getBoolean("Cube Present?", false) == true) { 032518 jack
			// //shintaku sharp sesnor disabled
			// setBlackIntake(0);
			// setBlueIntake(0);
			// rollerSet(0);
			// } else {
			// setBlackIntake(-0.4);
			// setBlueIntake(0.4);
			// rollerSet(0.22);
			// if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
			// Constants.xbox1.setRumble(RumbleType.kLeftRumble, 1);
			// }
			// }
			break;

		case SCORE_MEDIUM: // low setting for button board
			setBlackIntake(-.45);
			setBlueIntake(-.45);
			// rollerSet(0); 032518 jack shintaku unnecessary
			// isSuperPunchOut(false);
			break;

		case SCORE_LOW: // this is used during autonomous
			setBlackIntake(-.3);
			setBlueIntake(-.3);
			// rollerSet(0); 032518 jack shintaku unnecessary
			// isSuperPunchOut(false);
			break;
		case SCORE_HARD: // medium setting for button board
			setBlackIntake(-1);
			setBlueIntake(-1);
			// rollerSet(0); 032518 jack shintaku unnecessary
			// isSuperPunchOut(false);
			break;

		case DISABLE:
			scoreInit = false;
			dejamInit = false;
			currentSuperPunchStep = "open intake";
			if (init == false) {
				cubeTimer.start();
				init = true;
			}
			setBlackIntake(0);
			setBlueIntake(0);
			rollerSet(0);
			isSuperPunchOut(false);
			intakeYeaah = false;
			// if (SmartDashboard.getBoolean("Cube Present?", true) == true) {
			// isSuperPunchOut(false);
			// } else {
			// isSuperPunchOut(true);
			// }
			break;
		case DEJAM:
			setBlackIntake(.4);
			setBlueIntake(-.45);
			// if (dejamInit == false) { 032518 jack shintaku unnecessary
			// cubeTimer.reset();
			// dejamInit = true;
			// }
			// if (cubeTimer.get() < .7) {
			// setBlackIntake(.4);
			// setBlueIntake(.45);
			// rollerSet(.15);
			// } else {
			// if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
			// setBlackIntake(0);
			// setBlueIntake(0);
			// rollerSet(0);
			// } else {
			// setBlackIntake(-0.4);
			// setBlueIntake(0.4);
			// rollerSet(0.15);
			// // if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
			// // Constants.xbox1.setRumble(RumbleType.kLeftRumble, 1);
			// // } else {
			// // Constants.xbox1.setRumble(RumbleType.kLeftRumble, 0);
			// // }
			//
			// }
			// }
			// isSuperPunchOut(false);
			break;

		}
	}
}