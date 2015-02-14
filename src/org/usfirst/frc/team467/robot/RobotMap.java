/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team467.robot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Team467
 */
public class RobotMap
{
    // Robot Dimensions
	// Length is the short side, wide is the long side
    // Based on 2012 robot's geometry. Change for newer robots!
    public static final double LENGTH = 16.75; //inches btw the wheels
    public static final double WIDTH = 25.0; //inches btw the wheels
	
    // Steering motor ids in array (DO NOT ALTER)
    public static final int FRONT_LEFT = 0;
    public static final int FRONT_RIGHT = 1;
    public static final int BACK_LEFT = 2;
    public static final int BACK_RIGHT = 3;

    // Drive motors - Talon, digital sidecar
    public static final int FRONT_LEFT_MOTOR_CHANNEL = 4;
    public static final int FRONT_RIGHT_MOTOR_CHANNEL = 2;
    public static final int BACK_LEFT_MOTOR_CHANNEL = 6;
    public static final int BACK_RIGHT_MOTOR_CHANNEL = 0;

    // Steering motors - Talon, digital sidecar
    public static final int FRONT_LEFT_STEERING_MOTOR_CHANNEL = 5;
    public static final int FRONT_RIGHT_STEERING_MOTOR_CHANNEL = 3;
    public static final int BACK_LEFT_STEERING_MOTOR_CHANNEL = 7;
    public static final int BACK_RIGHT_STEERING_MOTOR_CHANNEL = 1;

    // Steering sensors - Analog bumper
    public static final int FRONT_LEFT_STEERING_SENSOR_CHANNEL = 2;
    public static final int FRONT_RIGHT_STEERING_SENSOR_CHANNEL = 1;
    public static final int BACK_LEFT_STEERING_SENSOR_CHANNEL = 3;
    public static final int BACK_RIGHT_STEERING_SENSOR_CHANNEL = 0;

    // Steering motor constant array
    public static final int[] STEERING_MOTOR_CHANNELS =
    {
        RobotMap.FRONT_LEFT_STEERING_MOTOR_CHANNEL,
        RobotMap.FRONT_RIGHT_STEERING_MOTOR_CHANNEL,
        RobotMap.BACK_LEFT_STEERING_MOTOR_CHANNEL,
        RobotMap.BACK_RIGHT_STEERING_MOTOR_CHANNEL
    };

    // Steering sensor constant array
    public static final int[] STEERING_SENSOR_CHANNELS =
    {
        RobotMap.FRONT_LEFT_STEERING_SENSOR_CHANNEL,
        RobotMap.FRONT_RIGHT_STEERING_SENSOR_CHANNEL,
        RobotMap.BACK_LEFT_STEERING_SENSOR_CHANNEL,
        RobotMap.BACK_RIGHT_STEERING_SENSOR_CHANNEL
    };

    // Data keys (names used when saving centers to robot)
    public static final String[] STEERING_KEYS = new String[]
    {
        "FrontLeft", "FrontRight", "BackLeft", "BackRight"
    };

    // Number of increments on the steering sensor
    public static final double STEERING_RANGE = 4047;    
    
    // PID array 
    public static final PID [] PIDvalues =
    {
        new PID (-0.003, 0.0, 0.0), // Front Left PID values
        new PID (-0.003, 0.0, 0.0), // Front Right PID values
        new PID (-0.003, 0.0, 0.0), // Back Left PID values
        new PID (-0.003, 0.0, 0.0), // Back Right PID values
    };
}
