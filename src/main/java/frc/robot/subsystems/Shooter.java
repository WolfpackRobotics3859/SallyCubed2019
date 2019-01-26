/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap.intakeMode;
import frc.robot.RobotMap;
import frc.robot.RobotMap.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
  static VictorSPX leftShootMotor = new VictorSPX(RobotMap.leftIntakeMotorPort);
  static VictorSPX rightShootMotor = new VictorSPX(RobotMap.rightIntakeMotorPort);

  public void set(intakeMode state){
    switch(state){
      case INTAKE:
        leftShootMotor.set(ControlMode.PercentOutput, .5);
        rightShootMotor.set(ControlMode.PercentOutput, .5);
      break;
      
      case OUTTAKE:
        leftShootMotor.set(ControlMode.PercentOutput, -.5);
        rightShootMotor.set(ControlMode.PercentOutput, -.5);
      break;




    }


  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
