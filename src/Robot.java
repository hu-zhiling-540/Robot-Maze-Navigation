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
	
	static final int TRAVEL_DIST = 23;

	static NXTRegulatedMotor leftMotor = Motor.C;
	static NXTRegulatedMotor rightMotor = Motor.A;

	// creates instances for sensors and attach them to the relative port
	static TouchSensor frontBump = new TouchSensor(SensorPort.S2);
	static LightSensor light = new LightSensor(SensorPort.S1);
	static UltrasonicSensor usonic = new UltrasonicSensor(SensorPort.S4);

	// reads an initial light value to compare light values that will be read after
	static int init_light_value = light.readValue();
	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String [] args) { 
    	
    	int[] start = {1,1};	// starting position
    	int[] goal = {5,8};		// goal position
    	
    	// initialize a world that robot navigates by
    	World world = new World(7, 10, start, goal);
    	
    	DifferentialPilot robot = new DifferentialPilot(5.6f, 11.0f, Motor.A, Motor.C, true);   
    	
    	robot.setTravelSpeed(10);
    	
    	Behavior explore = new Explore(robot, world);     			// default behavior

    	Behavior avoid = new Avoid(robot, frontBump, usonic, world);		// based on inputs from the touch sensor and ultrasonic sensor
    	
    	// a third behavior which runs after the first two have been completed
    	Behavior reachGoal = new ReachGoal(robot, light, world, init_light_value);	// based on inputs from the light sensor
        
        // array of behaviors will be passed to arbitrator
        Behavior [] bArr = {explore, avoid, reachGoal}; 
        
        //creates the arbitrator 
        Arbitrator arby = new Arbitrator(bArr); 
 
        arby.start(); 
    } 
} 