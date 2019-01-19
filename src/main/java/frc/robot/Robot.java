/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static final String leftAuto = "Left Auto";
	private static final String rightAuto = "Right Auto";
	private static final String voidAuto = "Void Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	public static Drive drive = new Drive();

	@Override
	public void robotInit() {
		m_chooser.addDefault("Void Auto", voidAuto);
		m_chooser.addObject("Right Auto", rightAuto);
		m_chooser.addObject("My Auto", leftAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}


	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case leftAuto:
				
				break;
			case rightAuto:
				
			default:
				// Put default auto code here
				break;
		}
	}


	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Left Velocity", drive.leftFront.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Right Velocity", drive.rightFront.getSelectedSensorVelocity());
		drive.xboxDrive();
	}


	@Override
	public void testPeriodic() {
	}
}
