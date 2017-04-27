import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Authors: Zhiling Hu and Sabrina Accime 
 * Prof. Lisa Ballestors
 * Artificial Intelligence
 * Lab04
 * April 9, 2017
 */
public class Robot {
	
	protected static final int TRAVEL_DIST = 20;
	
	protected static NXTRegulatedMotor leftMotor = Motor.C;
	protected static NXTRegulatedMotor rightMotor = Motor.A;
	
	// creates a touch sensor object and a light sensor object, and attach them to the relative port
	protected static TouchSensor frontBump = new TouchSensor(SensorPort.S2);	// touch sensor in the front
	protected static LightSensor light = new LightSensor(SensorPort.S3);

	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String [] args) { 
    	
    	int[] start = {0,0};
    	int[] goal = {5,8};
    	World maze = new World(5, 8, start, goal);
    	
    	DifferentialPilot robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);   
    	
    	robot.setTravelSpeed(TRAVEL_DIST);
    	Behavior Explore = new Explore(robot, maze);     
    	Behavior Avoid = new Avoid(robot, frontBump);
        Behavior Goal = new ReachGoal(robot, true);
        
        // array of behaviors will be passed to arbitrator
        Behavior [] bArr = {Explore, Avoid, Goal}; 
        
        //creates the arbitrator 
        Arbitrator arby = new Arbitrator(bArr); 
 
        arby.start(); 
    } 
} 