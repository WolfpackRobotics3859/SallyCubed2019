//package org.usfirst.frc.team3859.robot;
//
//import org.usfirst.frc.team3859.robot.Constants.armPos;
//import org.usfirst.frc.team3859.robot.Constants.position;
//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class OI {
//	Drive drive = new Drive();
//	Intake intake = new Intake();
//	Arm arm = new Arm();
//	Climb climb = new Climb();
//
//	boolean ptoInit = false;
//	boolean cubeInit = false;
//
//	boolean toggle = true;
//	boolean intakeInterlock = true;
//	boolean ptoInterlock = true;
//	boolean armToggle = true;
//
//	boolean isMandibleCompressed = true;
//	boolean belt = false;
//	boolean armm = false;
//
//	boolean isPressed = false;
//	boolean isButtonPressed = false;
//	boolean isPTOEngaged = false;
//
//	double angleGoal = 0;
//
//	public void enable() {
//
//		double mandibleButton = Constants.xbox1.getTriggerAxis(Hand.kRight);
//		double armTrigger = Constants.xbox1.getTriggerAxis(Hand.kLeft);
//		double intakeDPad = Constants.xbox1.getPOV();
//		double armManualButton = Constants.xbox1.getY(Hand.kLeft);
//
//		boolean leftIntakeButton = Constants.xbox1.getBumper(Hand.kLeft);
//		boolean rightIntakeButton = Constants.xbox1.getBumper(Hand.kRight);
//		boolean armAButton = Constants.xbox1.getAButton();
//		boolean armBButton = Constants.xbox1.getBButton();
//		boolean armXButton = Constants.xbox1.getXButton();
//		boolean armYButton = Constants.xbox1.getYButton();
//		boolean climbBButton = Constants.xbox2.getBButton();
//
//		// TOGGLE BUTTONS
//		// if (toggle && Button) { // Only execute once per Button push
//		// toggle = false; // Prevents this section of code from being called again
//		// until the Button is released and re-pressed
//		// if (belt) { // Decide which way to set the motor this time through (or use
//		// this as a motor value instead)
//		// belt= false;
//		// conveyorMotor.set(1);
//		// } else {
//		// belt= true;
//		// conveyorMotor.set(0);
//		// }
//		// } else if(Button == FALSE) {
//		// toggle = true; // Button has been released, so this allows a re-press to
//		// activate the code above.
//		// }
//
//		// INTAKE MOTORS
//
//		// when LEFT bumper is pressed and dpad right is NOT pressed
//		if (isButtonPressed(leftIntakeButton) && !(isButtonPressed(intakeDPad, "right"))) {
//			intake.set(position.INTAKE); // both motors are intaking cube
//
//			// when LEFT bumper is pressed and dpad right IS pressed
//		} else if (isButtonPressed(leftIntakeButton) && isButtonPressed(intakeDPad, "right")) {
//			intake.set(position.DEJAM); // one motor intakes, one motor outtakes to dejam the cube
//
//			// when RIGHT bumper is pressed and dpad down is NOT pressed
//		} else if (isButtonPressed(rightIntakeButton) && !(isButtonPressed(intakeDPad, "down"))) {
//			intake.set(position.SCORE_MEDIUM); // outtakes cube WITHOUT the pneumatic punch
//
//			// when RIGHT bumper is pressed and dpad down IS pressed
//		} else if (isButtonPressed(rightIntakeButton) && isButtonPressed(intakeDPad, "down")) {
//			intake.set(position.SCORE_HARD); // outtakes cube WITH the pneumatic punch
//
//			// default state
//		} else {
//			intake.set(position.DISABLE); // no activity
//		}
//
//		// MANDIBLES - INTAKE PNEUMATIC
//
//		// Create interlock mechanism to force intake button to be released
//		// before running button code again
//		if (intakeInterlock && isButtonPressed(mandibleButton, .6) == true) { // Only execute once per Button push
//
//			intakeInterlock = false; // set interlock to false; will not run again until button is released
//
//			// if mandibles are compressed
//			if (isMandibleCompressed) {
//				releaseMandible();
//				isMandibleCompressed = false;
//
//				// if mandibles are NOT compressed
//			} else {
//				compressMandible();
//				isMandibleCompressed = true;
//			}
//
//			// If button is released
//		} else if (isButtonPressed(mandibleButton, .6) == false) {
//
//			intakeInterlock = true; // set interlock to true; allows the button code to run again
//
//		}
//
//		// ARM
//
//		if (isButtonPressed(armTrigger, .6)) {
//
//			if (isButtonPressed(armBButton)) {
//
//				SmartDashboard.putNumber("Angle", Constants.intake); // setting arm goal on smartdashboard to 0 degrees
//
//			} else if (isButtonPressed(armYButton)) {
//
//				SmartDashboard.putNumber("Angle", Constants.switchShot); // setting arm goal on smartdashboard to 45
//																			// degrees
//
//			} else if (isButtonPressed(armXButton)) {
//
//				SmartDashboard.putNumber("Angle", Constants.backShot); // setting arm goal on smartdashboard to 90
//																		// degrees
//
//			}
//			angleGoal = SmartDashboard.getNumber("Angle", 0); // getting the angle goal from the smartdashboard
//			arm.set(angleGoal); // setting the arm position to the angle goal
//
//		} else {
//
//			double voltage = .14; // holding voltage for arm so that it stays in position
//			double voltageChange = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048)
//					* voltage; // math to have the holding voltage change based on the angle of the arm
//
//			// setting the arm based on the xbox controller Y value and the holding voltage
//			// change
//			Constants.armLeft.set(ControlMode.PercentOutput, (-(armManualButton) * .5) + voltageChange);
//
//			// outputting the position of the arm to the SmartDashboard
//			SmartDashboard.putNumber("Position", arm.getAngle());
//		}
//
//		// CLIMB
//
//		// for documentation check at mandibles intake
//		if (ptoInterlock && isButtonPressed(climbBButton)) {
//			ptoInterlock = false;
//			if (isPTOEngaged) {
//				isPTOEngaged = false;
//				climb.isPTOEngaged(false);
//				SmartDashboard.putBoolean("PTO Engaged?", false);
//			} else {
//				isPTOEngaged = true;
//				climb.isPTOEngaged(true);
//				SmartDashboard.putBoolean("PTO Engaged?", true);
//			}
//		} else if (!isButtonPressed(climbBButton)) {
//			ptoInterlock = true;
//		}
//
//		// XBOX DRIVE
//		// Constants.leftFront.set(ControlMode.PercentOutput,
//		// Constants.xbox2.getY(Hand.kLeft));
//		// Constants.rightFront.set(ControlMode.PercentOutput,
//		// Constants.xbox2.getY(Hand.kRight));
//
//		// WHEEL DRIVE
//		if (climb.isEngaged == false) {
//			if (Constants.joystick1.getRawButton(2)) {// turn in place button X
//
//				drive.spinspot(.5);
//
//			} else if (Constants.joystick1.getRawButton(4)) {// adjust button
//
//				drive.spinspot(.25);
//
//			} else if (Constants.joystick1.getRawButton(5)) {// adjust button
//
//				drive.spinspot(-.25);
//
//			} else if (Constants.joystick1.getRawButton(1)) {// TURBO NGHHHHHHH
//
//				drive.move(-1, Constants.driveWheel.getX());
//
//			} else if (Constants.joystick1.getRawButton(3)) {// slowwwwwwwwwwww
//
//				drive.move(-0.3, Constants.driveWheel.getX());
//
//			}
//
//			else {
//				drive.move(Constants.joystick1.getY(), Constants.driveWheel.getX());
//
//			}
//
//		} else if (climb.isEngaged == true) {
//			Constants.rightFront.set(ControlMode.PercentOutput, Constants.joystick1.getY());
//			Constants.leftFront.set(ControlMode.PercentOutput, Constants.joystick1.getY());
//		}
//	}
//
//	// function to compress the intake mandibles
//	private void compressMandible() {
//		Constants.intakeCompress.set(Value.kForward);
//	}
//
//	// function to release the intake mandibles
//	private void releaseMandible() {
//		Constants.intakeCompress.set(Value.kReverse);
//
//	}
//
//	private boolean isButtonPressed(boolean state) {
//		if (state == true)
//			return true;
//		else
//			return false;
//	}
//
//	private boolean isButtonPressed(double state, double max) {
//		if (state >= max)
//			return true;
//		else
//			return false;
//	}
//
//	private boolean isButtonPressed(double state, String direction) {
//		direction = direction.toLowerCase();
//		switch (direction) {
//		case "left":
//			if (state == 270) {
//				isButtonPressed = true;
//			} else {
//				isButtonPressed = false;
//			}
//			break;
//		case "right":
//			if (state == 90) {
//				isButtonPressed = true;
//			} else {
//				isButtonPressed = false;
//			}
//			break;
//		case "down":
//			if (state == 180) {
//				isButtonPressed = true;
//			} else {
//				isButtonPressed = false;
//			}
//			break;
//		case "up":
//			if (state == 0) {
//				isButtonPressed = true;
//			} else {
//				isButtonPressed = false;
//			}
//			break;
//		}
//		return isButtonPressed;
//	}
//
//}
package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.position;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	Drive drive = new Drive();
	Intake intake = new Intake(); 
	Arm arm = new Arm();
	Climb climb = new Climb();

	boolean ptoInit = false;
	boolean cubeInit = false;

	boolean toggle = true;
	boolean intakeInterlock = true;
	boolean ptoInterlock = true;
	boolean deployInterlock = true;
	boolean redeployInterlock = true;
	boolean armToggle = true;
	boolean feetInterlock = true;

	boolean belt = false;
	boolean armm = false;

	boolean isPressed = false;
	boolean isButtonPressed = false;
	boolean isPTOEngaged = false;
	boolean isReDeployed = false;
	boolean isClimbFeet = false;
	boolean isClimbDeploy = true;
	boolean intakeYes = true;

	double armAngleGoal = 0;

	double voltage = .14;

	int mandibleButton;
	double armTrigger;
	double intakeDPad;
	double armManualButton;

	boolean dejamButton;
	boolean intakeButton;
	boolean shootButton;
	boolean shootMediumButton;
	boolean shootHardButton;
	boolean shootSuperPunchButton;

	boolean armAButton;
	boolean armBButton;
	boolean armXButton;
	boolean armYButton;
	boolean climbBButton;

	boolean armUp;
	boolean armDown;
	boolean switchArmButton;
	boolean intakeArmButton;
	boolean scaleArmButton;
	boolean backArmButton;

	boolean climbDeployButton;
	boolean climbPoleButton1;
	boolean rightClimbReDeploy;
	boolean engagePTO;
	boolean disengagePTO;
	boolean climbFeet1;
	boolean climbFeet2;

	boolean intakeCompress;
	boolean intakeUncompress;
	boolean intakeNeutral;
	boolean armDownOverride;
	String intakeCompressState = "";

	double xboxPOV;

	public void enable() {
		boolean slowDriveButton = Constants.joystick1.getRawButton(3);
		boolean quickTurnButton = Constants.joystick1.getRawButton(2);
		// boolean turboButton = Constants.joystick1.getRawButton(1);
		if (Constants.xboxYes == true) {
			mandibleButton = Constants.xbox1.getPOV();
			armTrigger = Constants.xbox1.getTriggerAxis(Hand.kLeft);
			intakeDPad = Constants.xbox1.getPOV();
			armManualButton = Constants.xbox1.getY(Hand.kLeft);
			intakeButton = Constants.xbox1.getBumper(Hand.kLeft);
			shootButton = Constants.xbox1.getBumper(Hand.kRight);
			armAButton = Constants.xbox1.getAButton();
			armBButton = Constants.xbox1.getBButton();
			armXButton = Constants.xbox1.getXButton();
			armYButton = Constants.xbox1.getYButton();
			climbBButton = Constants.xbox2.getBButton();
		} else if (Constants.xboxYes == false) {
			intakeButton = Constants.launchPad0.getRawButton(7);
			dejamButton = Constants.launchPad0.getRawButton(5);
			shootMediumButton = Constants.launchPad1.getRawButton(5);
			shootHardButton = Constants.launchPad1.getRawButton(4);
			shootSuperPunchButton = Constants.launchPad1.getRawButton(3);

			intakeArmButton = Constants.launchPad1.getRawButton(8);
			switchArmButton = Constants.launchPad0.getRawButton(3);
			scaleArmButton = Constants.launchPad1.getRawButton(6);
			// backArmButton = Constants.launchPad1.getRawButton(6);

			climbDeployButton = Constants.launchPad1.getRawButton(16);
			// climbPoleButton1 = Constants.launchPad0.getRawButton(9);
			rightClimbReDeploy = Constants.launchPad0.getRawButton(9);
			engagePTO = Constants.launchPad1.getRawButton(11);

			intakeCompress = Constants.launchPad1.getRawButton(10);
			intakeUncompress = Constants.launchPad1.getRawButton(9);
			intakeNeutral = Constants.launchPad0.getRawButton(6);

			climbFeet1 = Constants.launchPad1.getRawButton(12);

			armDownOverride = Constants.launchPad0.getRawButton(8);
		}

		//
		// // TOGGLE BUTTONS
		// // if (toggle && Button) { // Only execute once per Button push
		// // toggle = false; // Prevents this section of code from being called again
		// // until the Button is released and re-pressed
		// // if (belt) { // Decide which way to set the motor this time through (or use
		// // this as a motor value instead)
		// // belt= false;
		// // conveyorMotor.set(1);
		// // } else {
		// // belt= true;
		// // conveyorMotor.set(0);
		// // }
		// // } else if(Button == FALSE) {
		// // toggle = true; // Button has been released, so this allows a re-press to
		// // activate the code above.
		// // }
		//

		if (intakeButton && !(dejamButton)) {

			intake.set(position.INTAKE); // both motors are intaking cube

			// when LEFT bumper is pressed and dpad right IS pressed
		} else if (dejamButton && intakeButton) {
			intake.set(position.DEJAM); // one motor intakes, one motor outtakes to dejam
			// the cube

			// when RIGHT bumper is pressed and dpad down is NOT pressed
		} else if (shootMediumButton) {
			intake.set(position.SCORE_MEDIUM); // outtakes cube WITHOUT the pneumatic
			// punch

			// when RIGHT bumper is pressed and dpad down IS pressed
		} else if (shootHardButton) {
			intake.set(position.SCORE_HARD); // outtakes cube WITH the pneumatic punch

			// super punch
		} else if (shootSuperPunchButton) {
			intake.set(position.SCORE_SUPERPUNCH);

			// default state
		} else {
			intake.set(position.DISABLE); // no activity
		}

		// intake pnuematics compress/decompress/neutral

		if (intakeCompress) {
			intakeCompressState = "compressed";
		} else if (intakeUncompress) {
			intakeCompressState = "uncompressed";
		} else if (intakeNeutral) {
			intakeCompressState = "neutral";
		}

		SmartDashboard.putString("Intake Compress State", intakeCompressState);

		if (intake.intakeYeaah == false) {
			intake.setIntakeState(intakeCompressState);
		}

		if (ptoInterlock && (isButtonPressed(engagePTO))) {
			ptoInterlock = false;
			if (isPTOEngaged) {
				isPTOEngaged = false;
				climb.isPTOEngaged(true);
				SmartDashboard.putBoolean("PTO Engaged?", true);
			} else {
				isPTOEngaged = true;
				climb.isPTOEngaged(false);
				SmartDashboard.putBoolean("PTO Engaged?", false);
			}
		} else if (!(isButtonPressed(engagePTO))) {
			ptoInterlock = true;
		}

		if (redeployInterlock && isButtonPressed(rightClimbReDeploy)) {
			redeployInterlock = false;
			if (isReDeployed) {
				isReDeployed = false;
				Constants.reDeployClimb.set(Value.kForward);
				SmartDashboard.putBoolean("Redeploy Correct?", true);
			} else {
				isReDeployed = true;
				Constants.reDeployClimb.set(Value.kReverse);
				SmartDashboard.putBoolean("Redeploy Correct?", false);
			}
		} else if (!(isButtonPressed(rightClimbReDeploy))) {
			redeployInterlock = true;
		}
		//
		if (deployInterlock && climbDeployButton) {
			deployInterlock = false;
			if (isClimbDeploy) {
				isClimbDeploy = false;
				Constants.climbPole.set(Value.kForward);
			} else {
				isClimbDeploy = true;
				Constants.climbPole.set(Value.kReverse);
			}
		} else if (!(climbDeployButton)) {
			deployInterlock = true;
		}
		//
		if (feetInterlock && (isButtonPressed(climbFeet1) || isButtonPressed(climbFeet2))) {
			feetInterlock = false;
			if (isClimbFeet) {
				isClimbFeet = false;
				Constants.climbFeet.set(Value.kForward);
			} else {
				isClimbFeet = true;
				Constants.climbFeet.set(Value.kReverse);
			}
		} else if (!(isButtonPressed(climbFeet1) || isButtonPressed(climbFeet2))) {
			feetInterlock = true;
		}

		// ARM

		// scale shot is between 46 - 42

		arm.setUp();

		if (isButtonPressed(intakeArmButton)) {

			SmartDashboard.putNumber("Goal Angle", Constants.intake); // setting arm goal on
			// smartdashboard to 0
			// degrees

		} else if (isButtonPressed(switchArmButton)) {

			SmartDashboard.putNumber("Goal Angle", Constants.switchShot); // setting arm goal
			// on smartdashboard to 45
			// degree

		} else if (isButtonPressed(scaleArmButton)) {

			SmartDashboard.putNumber("Goal Angle", Constants.scaleShot); // setting arm goal
			// on smartdashboard to 90
			// degree

			//
			// ***********************************************************************************************************************
		}
		//
		// ***********************************************************************************************************************

		armAngleGoal = SmartDashboard.getNumber("Goal Angle", 0); // getting the angle
		// goal from the smartdashboard

		// if (isButtonPressed(armDownOverride)) {
		//
		// } else {
		arm.set(armAngleGoal); // setting the arm position to the angle goal
		// }
		// if (intakeArmButton) {
		// Constants.test1.set(true);
		// } else if (switchArmButton) {
		// Constants.test2.set(true);
		// } else if (scaleArmButton) {
		// Constants.test3.set(true);
		// } else if (backArmButton) {
		// Constants.test4.set(true);
		// } else {
		// Constants.test1.set(false);
		// Constants.test2.set(false);
		// Constants.test3.set(false);
		// Constants.test4.set(false);
		// }

		//
		// // // XBOX DRIVE
		// Constants.leftFront.set(ControlMode.PercentOutput,
		// (Constants.xbox2.getY(Hand.kLeft) * .5));
		// Constants.rightFront.set(ControlMode.PercentOutput,
		// (-Constants.xbox2.getY(Hand.kRight) * .5));

		// // WHEEL DRIVE
		//
		if (quickTurnButton) {
			drive.spinspot(Constants.joystick1.getX() * -.45);
		} else if (slowDriveButton) {
			drive.sloww(Constants.joystick1.getY(), Constants.driveWheel.getX());
		} else {
			drive.move(Constants.joystick1.getY() * 2, Constants.driveWheel.getX());

		}

		// drive.xboxDrive();
	}

	//
	boolean isButtonPressed(boolean state) {
		if (state == true)
			return true;
		else
			return false;
	}

	boolean isButtonPressed(double state, double max) {
		if (state >= max)
			return true;
		else
			return false;
	}

	boolean isButtonPressed(double state, String direction) {
		direction = direction.toLowerCase();
		switch (direction) {
		case "left":
			if (state == 270)
				isButtonPressed = true;
			else
				isButtonPressed = false;

			break;
		case "right":
			if (state == 90) {
				isButtonPressed = true;
			} else {
				isButtonPressed = false;
			}
			break;
		case "down":
			if (state == 180) {
				isButtonPressed = true;
			} else {
				isButtonPressed = false;
			}
			break;
		case "up":
			if (state == 0)
				isButtonPressed = true;
			else
				isButtonPressed = false;

			break;
		}
		return isButtonPressed;
	}
}
