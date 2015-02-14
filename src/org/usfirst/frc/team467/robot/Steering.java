package org.usfirst.frc.team467.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Class to control steering mechanism on Team467 Robot
 * Uses WPI PID controller
 *
 * There are 2 adjustments that may be necessary in this code to adjust for electronics or 
 * mechanical issues. 
 * 
 * 1. If the steering motors are driving in the wrong direction (due to wiring or gearing changes)
 * 		- invert the sign of the steering PID - defined in RobotMap.java
 * 
 * 2. If the steering sensors are reading in the wrong direction
 * 	    - invert the value read from the sensor by changing the value returned from 
 * 		  getSensorValue() to be (RobotMap.STEERING_RANGE - steeringSensor.getAverageValue())
 *
 * @author Team467
 */
public class Steering
{
    // Sensor used to determine angle
    private AnalogInput steeringSensor;

    // PID Controller object
    private PIDController steeringPID;

    // Steering motor
    private Talon steeringMotor;

    // Center point of this steering motor. This is the value read from the sensor
    // when the wheels are in the normal forward position
    private double steeringCenter;

    /**
     * Class which deals with value used when checking PID (sensor value)
     */
    class SteeringPIDSource implements PIDSource
    {
        public double pidGet()
        {
            return (getSensorValue());
        }
    }

    /**
     * Constructor for steering subsystem
     *
     * @param pid - From the PIDvalues array
     * @param motor - motor channel
     * @param sensor - analog sensor channel
     * @param center - sensor reading when wheels point forward
     */
    Steering(PID pid, int motor, int sensor, double center)
    {
        // Make steering motor
        steeringMotor = new Talon(motor); 
        
        // Make steering sensor
        steeringSensor = new AnalogInput(sensor);

        // Set steering center
        steeringCenter = center;

        // Make PID Controller
        steeringPID = new PIDController(pid.p, pid.i, pid.d, new SteeringPIDSource(), steeringMotor);

        // Set PID Controller settings        
        steeringPID.setInputRange(0.0, RobotMap.STEERING_RANGE);
        steeringPID.setSetpoint(steeringCenter);
        steeringPID.setContinuous(true);
        steeringPID.enable();
    }

    /**
     * Get directly the value of the sensor
     *
     * @return The sensor value, read from 0 to RobotMap.STEERING_RANGE
     */
    public double getSensorValue()
    {
    	return (steeringSensor.getAverageValue());
    }
    
    /**
     * Enables the PID for the steering.
     */
    public void enablePID()
    {
        steeringPID.enable();
    }
    
    /**
     * Disables the PID for the steering.
     */
    public void disablePID()
    {
        steeringPID.disable();
        steeringMotor.set(0.0);
    }

    /**
     * @return - setpoint of the PID controller
     */
    public double getSetPoint()
    {
        return steeringPID.getSetpoint();
    }

    /**
     * Get the Talon motor of this steering object
     * @return
     */
    public Talon getMotor()
    {
        return steeringMotor;
    }

    /**
     * Get the sensor angle normalized to a -PI to +PI range
     * Implements the steering center point to give an angle accurate to the 
     * robot's alignment.
     *
     * @return - steering angle
     */
    public double getSteeringAngle()
    {
        double sensor = getSensorValue() - steeringCenter;

        if (sensor < (-RobotMap.STEERING_RANGE / (2 * Math.PI)))
        {
            sensor += RobotMap.STEERING_RANGE;
        }
        if (sensor > (RobotMap.STEERING_RANGE / (2 * Math.PI)))
        {
            sensor -= RobotMap.STEERING_RANGE;
        }        
        double output = (sensor) / (RobotMap.STEERING_RANGE / (2 * Math.PI));

        return output;
    }

    /**
     * Print steering parameters
     */
    public void printSteeringParameters()
    {
        System.out.print("Steering:");
        System.out.print(" P: " + steeringPID.getP());
        System.out.print(" I: " + steeringPID.getI());
        System.out.print(" D: " + steeringPID.getD());
        System.out.print(" M:" + steeringCenter);
        System.out.print(" V:" + getSensorValue());
        System.out.print(" S: " + steeringPID.getSetpoint());
        System.out.println();
    }
    
    /**
     * Set angle of front steering. A value of 0.0 corresponds to normally forward position.
     * @param angle - any value between -PI and +PI
     */
    public void setAngle(double angle)
    {	
        double setPoint;
        
        // normalize values to be in range of MIN_ROTATION to MAX_ROTATION
        while (angle < -Math.PI) 
        {
            angle += 2 * Math.PI;
        }
            
        while (angle > Math.PI) 
        {
            angle -= 2 * Math.PI;
        }
            
        // Calculate desired setpoint for PID based on known center position
        setPoint = steeringCenter + (angle/Math.PI * (RobotMap.STEERING_RANGE / 2));

        // Normalize setPoint into the 0 to RobotMap.STEERING_RANGE range
        if (setPoint < 0.0)
        {
            setPoint += RobotMap.STEERING_RANGE;
        }
        else if (setPoint >= RobotMap.STEERING_RANGE)
        {
            setPoint -= RobotMap.STEERING_RANGE;
        }

        steeringPID.setSetpoint(setPoint);
    }

    /**
     * Change the center point of this steering motor
     * @param center
     */
    public void setCenter(double center)
    {
        steeringCenter = center;
    }
}
