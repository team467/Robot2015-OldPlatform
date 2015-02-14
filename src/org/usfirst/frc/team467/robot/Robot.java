/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team467.robot;

import org.apache.log4j.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	private static final Logger LOGGER = Logger.getLogger(Robot.class);

	public static final boolean SINGLE_STICK_DRIVE = false;

    //Robot objects
    private DriverStation467 driverstation;

    private Drive drive;
    
    int session;
       
    /**
     * Time in milliseconds
     */
    double time;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
    	// Initialize logging framework.
    	Logging.init();
    	
        // Make robot objects
        driverstation = DriverStation467.getInstance();
        
        drive = Drive.getInstance();
        
        time = System.currentTimeMillis();
   
        Calibration.init();        
    }

    public void disabledInit()
    {
    	LOGGER.info("Robot disabled");
    	LOGGER.debug("Robot disabled");
    }

    /**
     * This function is run when autonomous control mode is first enabled
     */
    public void autonomousInit()
    {
    }

    /**
     * This function is run when operator control mode is first enabled
     */
    public void teleopInit()
    {
//    	NIVision.IMAQdxStartAcquisition(session);
    }

    /**
     * This function is run when test mode is first enabled
     */
    public void testInit()
    {
    }

    /**
     * This function is called periodically test mode
     */
    public void testPeriodic()
    {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        // Read driverstation inputs
        driverstation.readInputs();        
        
        if (driverstation.getCalibrate())
        {
        	// Calibrate Mode
            Calibration.updateCalibrate();
        }
        else 
        {
            // Drive Mode
            updateDrive();
        }
        
        
//        renderImage();
        
    }

    /**
     * called once per iteration to perform any necessary updates to the drive
     * system.
     */
    private void updateDrive()
    {
    	switch (driverstation.getDriveMode())
    	{
    		case REWIND:
    			drive.rewindDrive();
    			break;
    		
    		case REVOLVE:
    		{
    			Direction direction = Direction.LEFT;
    			if (driverstation.getDriveJoystick().buttonDown(6))
    			{
    				direction = Direction.RIGHT;
    			}
    			drive.revolveDrive(direction);
    		}
    		break;
    	
    		case STRAFE:
    		{
    			Direction direction = Direction.LEFT;
    			if (driverstation.getDriveJoystick().getPOV() < 180) 
    			{
    				direction = Direction.RIGHT;
    			}
    			drive.strafeDrive(direction);
    		}
    		break;
        
    		case TURN:
    			drive.turnDrive(-driverstation.getDriveJoystick().getTwist());
    			break;
        
    		case CRAB_FA:
    			drive.crabDrive(driverstation.getDriveJoystick().getStickAngle(), 
        				    driverstation.getDriveJoystick().getStickDistance(), 
        				    true /* field aligned */ );
    			break;
        
    		case CRAB_NO_FA:
    			drive.crabDrive(driverstation.getDriveJoystick().getStickAngle(),
        		  	      driverstation.getDriveJoystick().getStickDistance(), 
        		  	      false /* not field aligned */ );
    			break;
        
    		default:  //should never enter here
    			System.err.println("Button State not calculated correctly");
    			drive.crabDrive(driverstation.getDriveJoystick().getStickAngle(),
  		  	      driverstation.getDriveJoystick().getStickDistance(), 
  		  	      false /* not field aligned */ );
    			break;
    	}
    }
}
