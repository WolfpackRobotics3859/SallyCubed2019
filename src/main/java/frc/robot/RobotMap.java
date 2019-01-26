package frc.robot;

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
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  //Drive Motors
  public static int leftFrontMotorPort = 1;
  public static int leftMiddleMotorPort = 2;
  public static int leftBackMotorPort = 3;

  public static int rightFrontMotorPort = 6;
  public static int rightMiddleMotorPort = 7;
  public static int rightBackMotorPort = 8;

  //Intake Motors
  public static int leftIntakeMotorPort = 4;
  public static int rightIntakeMotorPort = 5;
  public static int intakeArmMotorPort = 11;

  //Intake Motors
  public static int leftShooterMotorPort = 12;
  public static int rightShooterMotorPort = 13;
  public static int ShooterArmMotorPort = 14;

  //Hatch Motors 
  public static int hatchMotorPort = 15;
  

  //Gyro
  public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  //Joysticks
  public static int driverWheelPort = 0;
  public static int driverJoystick = 1;
  
  public static int buttonBoard0 = 2;
  public static int buttonBoard1 = 3;

  //Enum States
  public static enum intakeMode {
    INTAKE,
    OUTTAKE
  }
  
  public static enum shooterMode {
    INTAKE,
    OUTTAKE
  }

  




}
