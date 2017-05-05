import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.subsumption.*; 
import lejos.nxt.*;


/**
 * the Avoid class implements Behavior, 
 * and deals with obstacle avoidance 
 * @author Guest
 *
 */
public class Avoid implements Behavior, FeatureListener{
	
	public DifferentialPilot robot;

	public TouchSensor frontBump;// an instance of a touch sensor
	public UltrasonicSensor usonic;  // an instance of an ultrasonic sensor
	public int MAX_DISTANCE = 50; // in centimeters
	public int PERIOD = 500; // in milliseconds
	public FeatureDetector fd;  
	
	public boolean frontPressed;
	
	public Explore explore;
	
	public static final int cellD = 30;
	
	
	/**
	 * constructor will take the robot as a parameter, 
	 * as well as the TouchSensor.
	 * @param robot
	 * @param frontBump
	 */
	public Avoid(DifferentialPilot robot, TouchSensor frontBump, Explore explore) {
		
		this.robot = robot;
		this.frontBump = frontBump;
		frontPressed = false;
		//System.out.println("Avoid");
		this.explore = explore;
	}
//	
//	public Avoid(DifferentialPilot robot, UltrasonicSensor usonic) {
//		
//		this.robot = robot;
//		this.usonic = usonic;
//		//ObjectDetect listener = new ObjectDetect(); 
//		fd = new RangeFeatureDetector(usonic, MAX_DISTANCE, PERIOD); 
//		//System.out.println("Avoid");
//	}
	
	// perform a scan and retrieve data
	public void getDetectorData() { 
		Feature result = fd.scan(); 
		if(result != null) { 
			System.out.println("Range: " + result.getRangeReading().getRange()); ;
		}
	}	
	
	@Override
	public boolean takeControl() { return frontBump.isPressed(); } 
	
	@Override
	public void action() {
		
//		explore.curr.setVisited();
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}

		// travels backwards by a cell
		robot.travel(-cellD,true); 
		
		int random = (int) Math.random() * 10;	// creates a random integer either even or odd
		if(random % 2 == 0)
			robot.rotate(90);
		else
			robot.rotate(-90);	
	}
	
	@Override
	public void suppress() { 
		robot.stop(); 
	}
	
	@Override
	// featureLister code will be notified when an object is detected
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int) feature.getRangeReading().getRange(); 
		Sound.playTone(1200-(range *10), 100);
		System.out.println("Range: " + range);	
	}
}