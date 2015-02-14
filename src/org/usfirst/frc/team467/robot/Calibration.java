/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team467.robot;

/**
 * @author Team467 This class contains only static variables and functions, and
 * simply acts as a container for all the calibration code.
 */
public class Calibration
{
    // Creates objects

    private static Drive drive;
    private static DataStorage data;

    // Incremented angle used for calibrating wheels
    private static double calibrationAngle = 0.0;
    
    /**
     * Initialize calibration code
     */
    public static void init()
    {
        // makes the objects
        drive = Drive.getInstance();
        data = DataStorage.getInstance();
    }

    /**
     * Updates steering calibration
     *
     * @param motorId The id of the motor to calibrate
     */
    public static void updateSteeringCalibrate(int motorId)
    {
        calibrationAngle = getCalibrationAngle(calibrationAngle);
        
        if (calibrationAngle > Math.PI)
        {
            calibrationAngle -= 2.0 * Math.PI;
        }
        if (calibrationAngle < -Math.PI)
        {
            calibrationAngle += 2.0 * Math.PI;
        }

        // Drive specified steering motor with no speed to allow only steering        
        drive.individualSteeringDrive(calibrationAngle, 0, motorId);

        // Write and set new center if trigger is pressed
        if (DriverStation467.getInstance().getCalibrateConfirmSelection())
        {
            double currentAngle = drive.getSteeringAngle(motorId);

            // Write data to robot
            data.putDouble(RobotMap.STEERING_KEYS[motorId], currentAngle);
            data.save();

            // Set new steering center
            drive.setSteeringCenter(motorId, currentAngle);

            // Reset calibration angle
            calibrationAngle = 0.0;
        }
    }
    
    /**
     * Gets the wheel selected by the stick.
     * @param prevSelectedWheel previously selected wheel
     * @return int val of which wheel to select
     */
    public static int getWheel(int prevSelectedWheel)
    {
    	Joystick467 joystick = DriverStation467.getInstance().getCalibrationJoystick();
        double stickAngle = joystick.getStickAngle();
        
        // Select motor being calibrated
        if (joystick.getStickDistance() > 0.5)
        {
            if (stickAngle < 0)
            {
                if (stickAngle < -Math.PI/2)
                {
                    return RobotMap.BACK_LEFT;
                }
                else
                {
                    return RobotMap.FRONT_LEFT;
                }
            }
            else
            {
                if (stickAngle > 0)
                {
                    if (stickAngle > Math.PI/2)
                    {
                        return RobotMap.BACK_RIGHT;
                    }
                    else
                    {
                        return RobotMap.FRONT_RIGHT;
                    }
                }
            }
        }
        // No new selected, return previous wheel selected
        return prevSelectedWheel;
    }
    
    /**
     * Gets the angle to set the calibrating wheel.
     * @param prevCalibrationAngle previous angle to update
     * @return angle for setting the angle
     */
    private static double getCalibrationAngle(double prevCalibrationAngle)
    {
        
        // If slow pressed on stick is pressed, slow down wheel calibration.
        double rateMultiplier = (DriverStation467.getInstance().getCalibrateSlowTurn()) ? getCalibrationSlowTurnRate() : 1;

        // Drive motor based on twist angle
        // Increase wheel angle by a small amount based on joystick twist
        prevCalibrationAngle += (DriverStation467.getInstance().getCalibrationJoystick().getTwist() / 100.0) * rateMultiplier;
        
        return prevCalibrationAngle;        
    }
    
    /**
     * rate of turn slow down modifier
     * @return 
     */
    private static double getCalibrationSlowTurnRate()
    {
        return 0.4;
    }
    
    // This is a static variable to define the wheel being calibrated.
    private static int calibrateWheelSelect = 0;

    /**
     * Update steering calibration control
     */
    public static void updateCalibrate()
    {
        calibrateWheelSelect = Calibration.getWheel(calibrateWheelSelect);
        updateSteeringCalibrate(calibrateWheelSelect);
    }
    
}
