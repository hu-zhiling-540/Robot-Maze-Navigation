import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Authors: Zhiling Hu and Sabrina Accime 
 * Prof. Lisa Ballestors
 * Artificial Intelligence
 * Final Lab
 * May 8, 2017
 */
public class Robot {
	
	protected static final int TRAVEL_DIST = 23;
	
	protected static NXTRegulatedMotor leftMotor = Motor.C;
	protected static NXTRegulatedMotor rightMotor = Motor.A;
	
	// creates a touch sensor object and a light sensor object, and attach them to the relative port
	protected static TouchSensor frontBump = new TouchSensor(SensorPort.S2);	// touch sensor in the front
	protected static LightSensor light = new LightSensor(SensorPort.S1);
	protected static UltrasonicSensor usonic = new UltrasonicSensor(SensorPort.S4);

	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String [] args) { 
    	
    	int[] start = {1,1};
    	int[] goal = {5,8};
    	
    	World world = new World(7, 10, start, goal);
    	
    	DifferentialPilot robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);   
    	
    	robot.setTravelSpeed(10);
    	
    	Behavior explore = new Explore(robot, world);     			// default behavior
//
//    	world = ((Explore) explore).getWorld();
//    	Cell curr = ((Explore) explore).curr;
//    	
    	Behavior avoid = new Avoid(robot, frontBump, usonic);		// based on inputs from the touch sensor and ultrasonic sensor
    	
    	// a third behavior which runs after the first two have been completed
    	Behavior reachGoal = new ReachGoal(robot, light, world);	// based on inputs from the light sensor
        
        // array of behaviors will be passed to arbitrator
        Behavior [] bArr = {explore, avoid, reachGoal}; 
        
        //creates the arbitrator 
        Arbitrator arby = new Arbitrator(bArr); 
 
        arby.start(); 
    } 
} 