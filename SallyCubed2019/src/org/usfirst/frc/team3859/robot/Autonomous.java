//package org.usfirst.frc.team3859.robot;
//
//import org.usfirst.frc.team3859.robot.Constants.armPos;
//import org.usfirst.frc.team3859.robot.Constants.position;
//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class Autonomous {
//	Timer autoTimer = new Timer();
//	OI oi = new OI();
//	private boolean init = false;
//	double order_ = 1;
//	double armOrder_ = 1;
//	double goal;
//	boolean finished = false;
//	PIDcontrol turnPID = new PIDcontrol(0, 0, 0);
//	PIDcontrol drivePID = new  PIDcontrol(0, 0, 0);
//
//	// function to set the drives to 0 and disable the intake
//	private void disable() {
//		Constants.leftFront.set(ControlMode.PercentOutput, 0);
//		Constants.rightFront.set(ControlMode.PercentOutput, 0);
//		oi.intake.set(position.DISABLE);
//
//	}
//
//	// function to drive forward with PID until you reach a goal encoder distance
//	/**
//	 * @param encDistance
//	 * @param order
//	 */
//	public void drive(double encDistance, double order) {
//		double P = 0.012;
//		double I = 0.00001;
//		double D = 0;
//		if (init == false) {
//			encDistance = encDistance + 17;
//			init = true;
//		}
//		if (order == order_) {
//			double error = encDistance - oi.drive.getRightEncDistance();
//			if (error >= 2) {
//				drivePID.setPID(P, I, D);
//				Constants.rightFront.set(ControlMode.PercentOutput, drivePID.calculate(error));
//				Constants.leftFront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
//			} else if (error < 2) {
//				Constants.leftFront.set(ControlMode.PercentOutput, 0);
//				Constants.rightFront.set(ControlMode.PercentOutput, 0);
//				order_++;
//				init = false;
//				oi.drive.rightEncInit = false;
//			}
//		}
//	}
//
//	// function to turn with PID until you reach a goal gyro angle
//	/**
//	 * PID TURN
//	 * 
//	 * @param direction
//	 * @param goal
//	 * @param order
//	 */
//	public void turnToAngle(double goal, double order) {
//		double P = 0.005;
//		double I = 0.0000045;
//		double D = 0;
//		if (init == false) {
//			// Constants.gyro.reset();
//			init = true;
//		}
//		// double angle = Constants.gyro.getAngle();
//		double angle = 0;
//
//		turnPID.setPID(P, I, D);
//		if (goal == 45) {
//			goal = 50;
//		}
//		double error = Math.abs(goal) - Math.abs(angle);
//		if (order == order_) {
//			if (error > .5) {
//				if (Math.signum(goal) == -1) {
//					Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
//					Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
//
//				} else if (Math.signum(goal) == 1) {
//
//					Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
//					Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
//				}
//			} else if (error <= .5) {
//				Constants.rightFront.set(ControlMode.PercentOutput, 0);
//				Constants.leftFront.set(ControlMode.PercentOutput, 0);
//				order_++;
//				init = false;
//			}
//		}
//
//	}
//
//	// sets the arm to a certain position
//	void armSet(Constants.armPos state, double armOrder) {
//		if (init == false) {
//			autoTimer.reset();
//			init = true;
//		}
//		if (armOrder == armOrder_) {
//			switch (state) {
//			case INTAKE:
//				// Constants.intakeCompress.set(Value.kReverse);
//				SmartDashboard.putNumber("Angle", Constants.intake);
//				break;
//			case SWITCHSHOT:
//				// Constants.intakeCompress.set(Value.kForward);
//				SmartDashboard.putNumber("Angle", Constants.switchShot);
//				break;
//			case BACKSHOT:
//				// Constants.intakeCompress.set(Value.kForward);
//				SmartDashboard.putNumber("Angle", Constants.backShot);
//				break;
//			}
//			double angle = SmartDashboard.getNumber("Angle", 0);
//			oi.arm.set(angle);
//		}
//
//	}
//
//	// sets the intake to a certain state
//	/**
//	 *
//	 *
//	 * @param Constants.position
//	 *            state
//	 * @param order
//	 */
//	void cubeStuff(Constants.position state, double order) {
//		if (init == false) {
//			autoTimer.reset();
//			init = true;
//		}
//		if (order == order_) {
//			if (oi.arm.done == true) {
//				if (autoTimer.get() < 3) {
//					oi.intake.set(state);
//				} else if (autoTimer.get() >= 3) {
//					oi.intake.set(position.DISABLE);
//					order_++;
//					armOrder_++;
//					init = false;
//				}
//			} else {
//				oi.intake.set(position.DISABLE);
//			}
//		}
//
//	}
//
//	// janky pid
//	/**
//	 * 
//	 * @param current
//	 * @param goal
//	 * @param speed
//	 * @return output
//	 */
//	public double jankyPID(double current, double goal, double speed) {
//		double progress = Math.abs(current) / Math.abs(goal);
//		double output = speed
//				* (1 + (4.7 * progress) - (19.65 * Math.pow(progress, 2)) + (23.76715 * Math.pow(progress, 3))
//						- (11.9661 * Math.pow(progress, 4)) + (2.216634 * Math.pow(progress, 5)));
//		return output;
//	}
//
//	// does nothing for a certain amount of time
//	/**
//	 * 
//	 * 
//	 * @param time
//	 * @param order
//	 */
//	public void nothing(double time, double order) {
//		if (init == false) {
//			autoTimer.reset();
//			init = true;
//		}
//		if (order == order_) {
//			if (autoTimer.get() < time) {
//				disable();
//			} else if (autoTimer.get() >= time) {
//				order_++;
//				init = false;
//				// SmartDashboard.putNumber("Auto Current Order", order);
//			}
//		}
//	}
//	// sets the arm into starting config using limit switch
//
//	public void startConfig() {
//		if (finished == false) {
//			if (Constants.limitSwitch.get() == false) {
//				Constants.armLeft.set(ControlMode.PercentOutput, -.2);
//			} else if (Constants.limitSwitch.get() == true) {
//				Constants.armLeft.set(ControlMode.PercentOutput, 0);
//				Constants.armSensor.setQuadraturePosition(0, 200);
//				finished = true;
//			}
//		}
//	}
//
//	boolean init1 = false;
//	boolean initiate = false;
//
//	// does the auto states
//	public void autoset(String autoMode, String autoChoice, char side1, char side2) {
//		if (initiate == false) {
//			autoTimer.start();
//			initiate = true;
//		} else if (initiate == true) {
//			oi.drive.setUp(false);
//			if (autoChoice == "deliver") {
//				switch (autoMode) {
//				// on the right side
//				case "Right":
//
//					// if switch is on the right side
//					if (side1 == 'R') {
//						nothing(4, 1);
//						drive(144, 2);
//						turnToAngle(-90, 3);
//						drive(31.2, 4);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 5);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 6);
//						drive(31.2, 7);
//						turnToAngle(90, 8);
//						drive(24, 9);
//						turnToAngle(-90, 10);
//
//						// other
//						// nothing(4, 1);
//						// drive(81.72, 2);
//						// turnToAngle(-45, 2);
//						// nothing(.5, 3);
//						// timedrive(1, speed, 4);
//						// cubeDeliver(.5, 5);
//						// timedrive(1, -speed, 6);
//						// turnToAngle("left", 90, .3, 7);
//						// timedrive(3, speed, 1);
//						// turnToAngle("right", 90, .3, 2);
//
//						// if switch is on the left side
//					} else if (side1 == 'L') {
//						drive(168, 1);
//						turnToAngle(-90, 2);
//						drive(233.04, 3);
//						turnToAngle(-90, 4);
//						drive(43.44, 5);
//						turnToAngle(-90, 6);
//						drive(24, 7);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 8);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 9);
//
//						// timedrive(5, speed, 1);
//						// turnToAngle("right", 90, .3, 2);
//						// timedrive(7, speed, 3);
//						// turnToAngle("right", 90, .3, 4);
//						// timedrive(1.1, speed, 5);
//						// turnToAngle("right", 90, .3, 6);
//						// timedrive(1, speed, 7);
//					}
//
//					break;
//				case "Left":
//					// on the left side
//
//					// if switch is on the right side
//					if (side1 == 'L') {
//						nothing(4, 1);
//						drive(144, 2);
//						turnToAngle(90, 3);
//						drive(31.2, 4);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 5);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 6);
//						drive(31.2, 7);
//						turnToAngle(-90, 8);
//						drive(24, 9);
//						turnToAngle(90, 10);
//
//					} else if (side1 == 'R') {
//						nothing(4, 1);
//						drive(144, 2);
//						turnToAngle(-90, 3);
//						drive(31.2, 4);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 6);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 7);
//						drive(31.2, 8);
//						turnToAngle(90, 9);
//						drive(24, 10);
//						turnToAngle(-90, 11);
//					}
//					break;
//				case "Middle":
//					// in the middle
//
//					// if switch is on the left side
//					if (side1 == 'L') {
//						drive(34.68, 1);
//						turnToAngle(-45, 2);
//						drive(56.16, 3);
//						turnToAngle(45, 4);
//						drive(16.44, 5);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 6);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 7);
//					} else if (side1 == 'R') {
//						drive(34.68, 1);
//						turnToAngle(45, 2);
//						drive(30.12, 3);
//						turnToAngle(-45, 4);
//						drive(16.44, 5);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 6);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 7);
//					}
//
//					break;
//				}
//			} else if (autoChoice == "cross") {
//				drive(66, 1);
//			} else if (autoChoice == "test") {
//				// Constants.PTO.set(Value.kForward);
//				// if (Constants.c.getClosedLoopControl() == false) {
//				// drive(33,1);
//				// armSet(armPos.INTAKE,1);
//				// cubeStuff(position.INTAKE, 2);
//				// drive(4,2);
//				// drive(-33,3);
//				startConfig();
//				if (finished == true) {
//					if (side1 == 'R') {
//						drive(20, 1);
//						turnToAngle(45, 2);
//						drive(40, 3);
//						turnToAngle(-45, 4);
//						nothing(1, 5);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 6);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 7);
//					} else if (side1 == 'L') {
//						drive(20, 1);
//						turnToAngle(-45, 2);
//						drive(40, 3);
//						turnToAngle(45, 4);
//						nothing(1, 5);
//						armSet(armPos.SWITCHSHOT, 1);
//						cubeStuff(position.SCORE_HARD, 6);
//						armSet(armPos.INTAKE, 2);
//						cubeStuff(position.DISABLE, 7);
//					}
//				}
//				//
//				// }
//				// }
//				// double turnAngle = SmartDashboard.getNumber("Turn Angle", 0);
//				// turnToAngle("right", turnAngle, 1);
//
//			}
//		}
//	}
//}
package org.usfirst.frc.team3859.robot;

import java.awt.Color;

import org.usfirst.frc.team3859.robot.Constants.armPos;
import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Timer autoTimer = new Timer();
	Timer autonomousTimer = new Timer();
	Timer startConfigTimer = new Timer();
	Timer pathfinderTimer = new Timer();
	OI oi = new OI();
	// Random rand = new Random();
	private boolean init = false, cubeDone = false, crossInit = false;
	double order_ = 1;
	double armOrder_ = 1;
	double goal;
	int index = 0;
	// double switchnum = rand.nextInt(3);
	boolean finished = false;
	PIDcontrol turnPID = new PIDcontrol(0, 0, 0);
	PIDcontrol drivePID = new PIDcontrol(0, 0, 0);

	private void disable() {
		Constants.leftFront.set(ControlMode.PercentOutput, 0);
		Constants.rightFront.set(ControlMode.PercentOutput, 0);
		oi.intake.set(position.DISABLE);

	}

	public double distanceCalc(double distance) {
		double newDistance = distance * 27;
		newDistance /= 18.85;

		return newDistance;
	}

	public void timeDrive(double time, double order, double speed) {

		if (order == order_) {
			if (init == false) {
				autonomousTimer.start();
				autonomousTimer.reset();
				init = true;
			}
			if (autonomousTimer.get() <= time) {
				Constants.rightFront.set(ControlMode.PercentOutput, speed);
				Constants.leftFront.set(ControlMode.PercentOutput, -speed);

			} else if (autonomousTimer.get() > time) {
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
			}
		}
	}

	/**
	 * @param encDistance
	 * @param order
	 */
	public void drive(double encDistance, double order) {
		double P = 0.005;
		double I = 0.00001;
		double D = 0;
		if (init == false) {
			oi.drive.resetLeftEnc();
			encDistance = distanceCalc(encDistance);
			autoTimer.reset();
			init = true;
		}
		SmartDashboard.putNumber("Left Enc Distance", oi.drive.getLeftEncDistance());
		if (order == order_) {
			double error = encDistance - oi.drive.getLeftEncDistance();
			if (Math.abs(error) > 5) {
				drivePID.setPID(P, I, D);
				double speed = drivePID.calculate(error);
				SmartDashboard.putNumber("Drive Error", error);

				// if (order == 1) {
				// if (autoTimer.get() <= .6) {
				// Constants.rightFront.set(ControlMode.PercentOutput, 0);
				// Constants.leftFront.set(ControlMode.PercentOutput, 0);
				// } else if (autoTimer.get() > .6) {
				// Constants.rightFront.set(ControlMode.PercentOutput,
				// drivePID.calculate(error));
				// Constants.leftFront.set(ControlMode.PercentOutput, -drivePID.calculate(error)
				// * .73);
				// }
				// } else {
				Constants.rightFront.set(ControlMode.PercentOutput, speed);
				Constants.leftFront.set(ControlMode.PercentOutput, -speed);
				// }
			} else if (Math.abs(error) <= 5) {
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				oi.drive.rightEncInit = false;
				oi.drive.leftEncInit = false;
			}
		}
	}

	/**
	 * @param encDistance
	 * @param order
	 */
	public void drive(double encDistance, double order, double timeOutSec) {
		double P = 0.005;
		double I = 0.00001;
		double D = 0;
		if (init == false) {
			Constants.leftEncSensor.setQuadraturePosition(0, 200);
			autoTimer.reset();
			init = true;
		}
		encDistance = distanceCalc(encDistance);
		double error = encDistance - oi.drive.getLeftEncDistance();
		SmartDashboard.putNumber("Left Enc Distance", oi.drive.getLeftEncDistance());
		if (order == order_) {
			if (Math.abs(error) > 5 || autoTimer.get() < timeOutSec) {
				drivePID.setPID(P, I, D);
				double speed = drivePID.calculate(error);
				SmartDashboard.putNumber("Drive Error", error);

				Constants.rightFront.set(ControlMode.PercentOutput, speed);
				Constants.leftFront.set(ControlMode.PercentOutput, -speed);
				// }
			} else if (Math.abs(error) <= 5 || autoTimer.get() >= timeOutSec) {
				SmartDashboard.putBoolean("Right  Enc Init", oi.drive.rightEncInit);
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				oi.drive.rightEncInit = false;
				oi.drive.leftEncInit = false;
			}
		}
	}

	/**
	 * @param encDistance
	 * @param order
	 */
	public void drive(double encDistance, double order, double P, double I, double D) {
		if (init == false) {
			oi.drive.resetLeftEnc();
			autoTimer.reset();
			encDistance = distanceCalc(encDistance);
			init = true;
		}
		SmartDashboard.putNumber("Left Enc Distance", oi.drive.getLeftEncDistance());
		if (order == order_) {
			double error = encDistance - oi.drive.getLeftEncDistance();
			if (Math.abs(error) > 5) {
				drivePID.setPID(P, I, D);
				SmartDashboard.putNumber("Drive Error", error);

				double speed = drivePID.calculate(error);
				// double newSpeed = .2;
				// Constants.rightFront.set(ControlMode.PercentOutput, newSpeed);
				// Constants.leftFront.set(ControlMode.PercentOutput, -newSpeed);
				Constants.rightFront.set(ControlMode.PercentOutput, speed);
				Constants.leftFront.set(ControlMode.PercentOutput, -speed);

			} else if (Math.abs(error) <= 5) {
				SmartDashboard.putBoolean("Right  Enc Init", oi.drive.rightEncInit);
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				oi.drive.rightEncInit = false;
				oi.drive.leftEncInit = false;
			}
		}
	}

	// /**
	// * @param encDistance
	// * @param order
	// */
	// public void drive(double encDistance, double timeOutSec,double order) {
	// double P = 0.005;
	// double I = 0.00001;
	// double D = 0;
	// if (init == false) {
	// Constants.leftEncSensor.setQuadraturePosition(0, 200);
	// autoTimer.reset();
	// init = true;
	// }
	// encDistance = distanceCalc(encDistance);
	// double error = encDistance - oi.drive.getLeftEncDistance();
	// if (order == order_) {
	// if (Math.abs(error) > 5) {
	// drivePID.setPID(P, I, D);
	// SmartDashboard.putBoolean("Right Enc Init", oi.drive.rightEncInit);
	// SmartDashboard.putNumber("Drive Error", error);
	//
	// // if (order == 1) {
	// // if (autoTimer.get() <= .6) {
	// // Constants.rightFront.set(ControlMode.PercentOutput, 0);
	// // Constants.leftFront.set(ControlMode.PercentOutput, 0);
	// // } else if (autoTimer.get() > .6) {
	// // Constants.rightFront.set(ControlMode.PercentOutput,
	// // drivePID.calculate(error));
	// // Constants.leftFront.set(ControlMode.PercentOutput,
	// -drivePID.calculate(error)
	// // * .73);
	// // }
	// // } else {
	// Constants.rightFront.set(ControlMode.PercentOutput,
	// drivePID.calculate(error));
	// Constants.leftFront.set(ControlMode.PercentOutput,
	// -drivePID.calculate(error));
	// // }
	// } else if (Math.abs(error) <= 5) {
	// SmartDashboard.putBoolean("Right Enc Init", oi.drive.rightEncInit);
	// Constants.leftFront.set(ControlMode.PercentOutput, 0);
	// Constants.rightFront.set(ControlMode.PercentOutput, 0);
	// order_++;
	// init = false;
	// oi.drive.rightEncInit = false;
	// }else if(autoTimer.get() >= timeOutSec) {
	// SmartDashboard.putBoolean("Right Enc Init", oi.drive.rightEncInit);
	// Constants.leftFront.set(ControlMode.PercentOutput, 0);
	// Constants.rightFront.set(ControlMode.PercentOutput, 0);
	// order_++;
	// init = false;
	// oi.drive.rightEncInit = false;
	// }
	// }
	// }

	/**
	 * PID TURN
	 * 
	 * @param direction
	 * @param goal
	 * @param order
	 */
	public void turnToAngle(double goal, double order) {
		// double P = 0.0052;
		double P = 0.003;
		double I = 0.00002;
		double D = 0.05;
		if (order == order_) {
			if (init == false) {
				Constants.gyro.reset();
				// if (goal <= 50) {
				// goal = goal + 5;
				// } else if (goal >= -50) {
				// goal = goal - 5;
				// }
				init = true;
			}
			double angle = Constants.gyro.getAngle();

			turnPID.setPID(P, I, D);
			double error = Math.abs(goal) - Math.abs(angle);
			SmartDashboard.putNumber("Turn Error", error);
			if (Math.abs(error) > 2.5) {
				if (Math.signum(goal) == -1) {
					// Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error)
					// * .9);
					Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));

				} else if (Math.signum(goal) == 1) {
					// Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error)
					// * .9);
					Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
				}
			} else if (Math.abs(error) <= 2.5) {
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
			}
		}

	}

	/**
	 * PID TURN
	 * 
	 * @param direction
	 * @param goal
	 * @param order
	 */
	public void turnToAngle(double goal) {
		// double P = 0.0052;
		double P = 0.003;
		double I = 0.00002;
		double D = 0.05;
		if (init == false) {
			Constants.gyro.reset();
			// if (goal <= 50) {
			// goal = goal + 5;
			// } else if (goal >= -50) {
			// goal = goal - 5;
			// }
			init = true;
		}
		double angle = Constants.gyro.getAngle();

		turnPID.setPID(P, I, D);
		double error = Math.abs(goal) - Math.abs(angle);
		SmartDashboard.putNumber("Turn Error", error);
		if (Math.abs(error) > 2.5) {
			if (Math.signum(goal) == -1) {
				// Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error)
				// * .9);
				Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
				Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));

			} else if (Math.signum(goal) == 1) {
				// Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error)
				// * .9);
				Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
				Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
			}
		} else if (Math.abs(error) <= 2.5) {
			Constants.rightFront.set(ControlMode.PercentOutput, 0);
			Constants.leftFront.set(ControlMode.PercentOutput, 0);
			init = false;
		}
	}

	/**
	 * 
	 * @param goal
	 * @param order
	 * @param P
	 * @param I
	 * @param D
	 */
	public void turnToAngle(double goal, double order, double P, double I, double D) {
		if (order == order_) {
			if (init == false) {
				Constants.gyro.reset();
				init = true;
			}
			double angle = Constants.gyro.getAngle();

			turnPID.setPID(P, I, D);
			double error = Math.abs(goal) - Math.abs(angle);
			SmartDashboard.putNumber("Turn Error", error);
			if (Math.abs(error) > 2.5) {
				if (Math.signum(goal) == -1) {
					// Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error)
					// * .9);
					Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));

				} else if (Math.signum(goal) == 1) {
					// Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error)
					// * .9);
					Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
				}
			} else if (Math.abs(error) <= 2.5) {
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
			}
		}

	}

	void armSet(Constants.armPos state, double armOrder) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (armOrder == armOrder_) {
			switch (state) {
			case INTAKE:
				// Constants.intakeCompress.set(Value.kReverse);
				SmartDashboard.putNumber("Angle", Constants.intake);
				break;
			case SWITCHSHOT:
				Constants.intakeCompress.set(Value.kForward);
				SmartDashboard.putNumber("Angle", Constants.switchShot);
				break;

			}
			double angle = SmartDashboard.getNumber("Angle", 0);
			// if (autoTimer.get() >= 3) {
			// oi.arm.done = true;
			// } else {
			// oi.arm.done = false;
			// }
			oi.arm.set(angle);
		}

	}

	/**
	 * @param state
	 * @param order
	 */
	void cubeStuff(Constants.position state, double order) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			// if (oi.arm.done == true) {
			if (autoTimer.get() < 1) {
				// SmartDashboard.putData("Cube state", );
				if (state == position.INTAKE) {
					oi.intake.setBlackIntake(0.4);
					oi.intake.setBlueIntake(0.4);
					oi.intake.setIntakeState("compressed");
				} else {
					oi.intake.set(state);
				}
			} else if (autoTimer.get() >= 1) {
				oi.intake.set(position.DISABLE);
				order_++;
				armOrder_++;
				cubeDone = true;
				init = false;

				// }
			}
			// else {
			// oi.intake.set(position.DISABLE);
			// }
		}

	}

	/**
	 * @param state
	 * @param order
	 */
	void cubeStuff(Constants.position state, double order, String last) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			SmartDashboard.putNumber("Auto Timer", autoTimer.get());
			// if (oi.arm.done == true) {
			if (autoTimer.get() < 2) {
				// SmartDashboard.putData("Cube state", );
				oi.intake.set(state);
			} else if (autoTimer.get() >= 2) {
				oi.arm.setUp();
				if (autoTimer.get() <= 2.5) {
					oi.intake.set(position.DISABLE);
				} else if (autoTimer.get() > 2.5 && autoTimer.get() <= 2.8) {
					Constants.armLeft.set(ControlMode.PercentOutput, .06);
				} else if (autoTimer.get() > 2.8) {
					Constants.armLeft.set(ControlMode.PercentOutput, 0);
					order_++;
					armOrder_++;
					init = false;
				}

				// }
			}
			// else {
			// oi.intake.set(position.DISABLE);
			// }
		} else {
			oi.intake.set(position.DISABLE);
		}

	}

	/**
	 * 
	 * @param current
	 * @param goal
	 * @param speed
	 * @return output
	 */
	public double jankyPID(double current, double goal, double speed) {
		double progress = Math.abs(current) / Math.abs(goal);
		double output = speed
				* (1 + (4.7 * progress) - (19.65 * Math.pow(progress, 2)) + (23.76715 * Math.pow(progress, 3))
						- (11.9661 * Math.pow(progress, 4)) + (2.216634 * Math.pow(progress, 5)));
		return output;
	}

	/**
	 * 
	 * 
	 * @param time
	 * @param order
	 */
	public void nothing(double time, double order) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < time) {
				disable();
			} else if (autoTimer.get() >= time) {
				order_++;
				init = false;
			}
		}
	}

	public void startConfig() {
		if (finished == false) {
			if (init == false) {
				startConfigTimer.start();
				startConfigTimer.reset();
				init = true;
			}
			oi.intake.setIntakeState("compressed");
			// FOR PLAY BOT
			// if (Constants.limitSwitch.get() == false) {
			// Constants.armLeft.set(ControlMode.PercentOutput, -.2);
			// } else if (Constants.limitSwitch.get() == true) {
			// Constants.armLeft.set(ControlMode.PercentOutput, 0);
			// Constants.armSensor.setQuadraturePosition(0, 200);
			// finished = true;
			// }

			// FOR PRACTICE BOT

			if (Constants.limitSwitch.get() == false) {
				if (startConfigTimer.get() <= 3) {
					oi.arm.set(0);
				} else if (startConfigTimer.get() > 3) {
					Constants.armLeft.set(ControlMode.PercentOutput, 0);
				}

			} else if (Constants.limitSwitch.get() == true) {
				Constants.armLeft.set(ControlMode.PercentOutput, 0);
				Constants.armSensor.setQuadraturePosition(0, 200);
				init = false;
				finished = true;
			}

		}
	}

	boolean init1 = false;
	boolean initiate = false;

	public void autoset(String autoMode, String autoChoice, char side1, char side2) {
		if (initiate == false) {
			autoTimer.start();
			pathfinderTimer.start();
			initiate = true;
		} else if (initiate == true) {
			oi.drive.setUp(true);
			SmartDashboard.putString("AutoChoice", autoChoice);
			SmartDashboard.putNumber("Order", order_);
			if (autoChoice == "deliver") {
				switch (autoMode) {
				case "Left":
					startConfig();
					if (side1 == 'L') {
						timeDrive(2.3, 1, .3);
						turnToAngle(90, 2);
						timeDrive(.4, 3, .35);
						cubeStuff(position.SCORE_MEDIUM, 4);
						if (finished == true) {
							armSet(armPos.SWITCHSHOT, 1);
						}
					} else if (side1 == 'R') {
						timeDrive(2.3, 1, .3);
					}
					break;
				case "Middle":
					startConfig();

					if (side1 == 'L') {
						timeDrive(.4, 1, .3);
						turnToAngle(-90, 2);
						timeDrive(1.4, 3, .3);
						turnToAngle(80, 4);
						timeDrive(1.7, 5, .3);
						cubeStuff(position.SCORE_MEDIUM, 6);
						if (finished == true) {
							armSet(armPos.SWITCHSHOT, 1);
						}

					} else if (side1 == 'R') {
						timeDrive(.4, 1, .3);
						turnToAngle(90, 2);
						timeDrive(1.8, 3, .3);
						turnToAngle(-75, 4);
						timeDrive(1.9, 5, .3);
						cubeStuff(position.SCORE_MEDIUM, 6);
						if (finished == true) {
							armSet(armPos.SWITCHSHOT, 1);
						}
					}
					//
					break;
				case "Right":
					startConfig();
					if (side1 == 'R') {
						timeDrive(2.5, 1, .3);
						turnToAngle(-90, 2);
						timeDrive(.52, 3, .35);
						cubeStuff(position.SCORE_MEDIUM, 4);
						if (finished == true) {
							armSet(armPos.SWITCHSHOT, 1);
						}
					} else if (side1 == 'L') {
						timeDrive(2.3, 1, .3);
					}
					break;

				}
			} else if (autoChoice == "cross") {
				startConfig();
				SmartDashboard.putNumber("Right Enc", oi.drive.getRightEncDistance());
				if (autoTimer.get() < 1) {
					Constants.leftFront.set(ControlMode.PercentOutput, 1);
					Constants.rightFront.set(ControlMode.PercentOutput, 1);
				} else if (autoTimer.get() >= 1) {
					Constants.leftFront.set(ControlMode.PercentOutput, 0);
					Constants.rightFront.set(ControlMode.PercentOutput, 0);
				}
			} else if (autoChoice == "test") {

				double fieldWidth = 27;
				double switchLength = 12.792;
				double switchWidth = 4.67;

				// double arenaSize[][] = { { 0, 0 }, { 0, 27 }, { 54, 27 }, { 54, 0 }, { 0, 0 }
				// };

				// double[][] CheesyPath = new double[][] { { 0, fieldWidth / 2 }, { 2, 16 }, {
				// 3, 21 }, { 4, 21 }, };

				// final FalconPathPlanner cheesyPath = new FalconPathPlanner(CheesyPath);

				// double[][] leftSwitch = new double[][] {
				// { 14 + (switchWidth / 2), (fieldWidth / 2) - (switchLength / 2) }, // bottom
				// // right
				// // point
				// { 14 + (switchWidth / 2), (fieldWidth / 2) + (switchLength / 2) }, // top
				// right point
				// { 14 - (switchWidth / 2), (fieldWidth / 2) + (switchLength / 2) }, // top
				// left point
				// { 14 - (switchWidth / 2), (fieldWidth / 2) - (switchLength / 2) }, // bottom
				// left point
				// { 14 + (switchWidth / 2), (fieldWidth / 2) - (switchLength / 2) }, }; //
				// bottom right point (to
				// // connect
				// // it)

				double[][] autoPathCoordinates = new double[][] { { 0, fieldWidth / 2 }, { 1, fieldWidth / 2 + .25 },
						{ 3, fieldWidth / 2 + .5 }, { 4.5, fieldWidth / 2 + 1.25 }, { 7, fieldWidth / 2 + 3.25 },
						{ 9, fieldWidth / 2 + 4 }, { 11, fieldWidth / 2 + 4 } };
				//
				// final FalconLinePlot pathPlot = new FalconLinePlot(new double[][] { { 0.0,
				// 0.0 } });
				//
				// pathPlot.yGridOn();
				// pathPlot.xGridOn();
				//
				// pathPlot.setYTic(0, 27, 2);
				// pathPlot.setYLabel("Y (feet)");
				//
				// pathPlot.setXTic(0, 54, 2);
				// pathPlot.setXLabel("X (feet)");
				//
				// pathPlot.setTitle(
				// "Top Down View of FRC Field (54ft x 27ft) \n shows global position of robot
				// path, along with left and right wheel trajectories");

				final FalconPathPlanner autoPath = new FalconPathPlanner(autoPathCoordinates);

				double totalTime = 5;
				double timeStep = .01; //
				double robotTrackWidth = 2.3; // could be 2.1

				double maxVel = 50;

				autoPath.calculate(totalTime, timeStep, robotTrackWidth);
				// cheesyPath.calculate(totalTime, timeStep, robotTrackWidth);

				// pathPlot.addData(arenaSize, Color.BLACK);
				// pathPlot.addData(leftSwitch, Color.BLACK);
				// pathPlot.addData(autoPathCoordinates, Color.BLUE);
				// pathPlot.addData(CheesyPath, Color.ORANGE);
				//
				// final FalconLinePlot velocityPlot = new FalconLinePlot(new double[][] { {
				// 0.0, 0.0 } });
				// //
				// velocityPlot.xGridOn();
				// velocityPlot.yGridOn();
				// //
				// velocityPlot.setXTic(0, 10, 1);
				// velocityPlot.setXLabel("X (seconds)");
				//
				// velocityPlot.setYTic(0, 50, 5);
				// velocityPlot.setYLabel("Y (velocity(ft/s)");
				//
				// velocityPlot.addData(autoPath.smoothLeftVelocity, Color.magenta);
				// velocityPlot.addData(autoPath.smoothRightVelocity, Color.cyan);

				// velocityPlot.addData(leftVel, Color.magenta);
				// velocityPlot.addData(rightVel, Color.cyan);

				if (autoTimer.get() <= totalTime) {

					double leftVel = autoPath.smoothLeftVelocity[index][1] / maxVel;
					double rightVel = autoPath.smoothRightVelocity[index][1] / maxVel;

					// System.out.println("left length: " + autoPath.smoothLeftVelocity.length);
					// System.out.println("right length: " + autoPath.smoothRightVelocity.length);

					oi.drive.leftSet(leftVel);
					oi.drive.rightSet(rightVel);

					SmartDashboard.putNumber("Index", index);
					SmartDashboard.putNumber("Left Vel", leftVel);
					SmartDashboard.putNumber("Right Vel", rightVel);
					SmartDashboard.putNumber("Time", pathfinderTimer.get());
				}
				index++;

			} else if (autoChoice == "the big one...") {
				startConfig();
				if (side1 == 'L') {
					drive(20, 1);
					turnToAngle(-90, 2);
					drive(40, 3);
					turnToAngle(75, 4);
					drive(63, 5);
					if (finished == true) {
						armSet(armPos.SWITCHSHOT, 1);
						cubeStuff(position.SCORE_LOW, 6);
						armSet(armPos.INTAKE, 2);
						drive(-60, 8);
						turnToAngle(75, 9);
						drive(50, 10);
						cubeStuff(position.INTAKE, 11);
						drive(-50, 12);
						turnToAngle(-75, 13);
						drive(60, 14);
						armSet(armPos.SWITCHSHOT, 3);
						cubeStuff(position.SCORE_LOW, 6);
						drive(-60, 14);
						turnToAngle(75, 15);
						drive(50, 16);
						armSet(armPos.INTAKE, 4);
						cubeStuff(position.INTAKE, 17);
					}
				} else if (side1 == 'R') {
					drive(20, 1);
					turnToAngle(90, 2);
					drive(40, 3);
					turnToAngle(-75, 4);
					drive(63, 5);
					if (finished == true) {
						armSet(armPos.SWITCHSHOT, 1);
						cubeStuff(position.SCORE_LOW, 6);
						armSet(armPos.INTAKE, 2);
						drive(-60, 8);
						turnToAngle(-75, 9);
						drive(50, 10);
						cubeStuff(position.INTAKE, 11);
						drive(-50, 12);
						turnToAngle(75, 13);
						drive(60, 14);
						armSet(armPos.SWITCHSHOT, 3);
						cubeStuff(position.SCORE_LOW, 6);
						drive(-60, 14);
						turnToAngle(-75, 15);
						drive(50, 16);
						armSet(armPos.INTAKE, 4);
						cubeStuff(position.INTAKE, 17);
					}
				}

			}
		}

	}
}
