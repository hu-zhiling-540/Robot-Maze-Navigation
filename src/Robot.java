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
 * Final Lab
 * May 8, 2017
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
    	
    	int[] start = {1,1};
    	int[] goal = {5,8};
    	World maze = new World(7, 10, start, goal);
//    	maze.printWorld();
    	
    	DifferentialPilot robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);   
    	
    	robot.setTravelSpeed(TRAVEL_DIST);
    	Behavior explore = new Explore(robot, maze);     
    	Behavior avoid = new Avoid(robot, frontBump, (Explore) explore);	// should also take explore as parameters
        Behavior reachGoal = new ReachGoal(robot, light, (Explore) explore);		// should also take explore as parameters
        
        // array of behaviors will be passed to arbitrator
        Behavior [] bArr = {explore, avoid, reachGoal}; 
        
        //creates the arbitrator 
        Arbitrator arby = new Arbitrator(bArr); 
 
        arby.start(); 
    } 
} 