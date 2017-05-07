import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 
import lejos.nxt.*;


/**
 * the Avoid class implements Behavior, 
 * It will let robot reverse and turn 
 * when the robot strikes an object or detects one close by
 *
 */
public class Avoid implements Behavior{
	
	public DifferentialPilot robot;

	private TouchSensor frontBump;		// an instance of a touch sensor
	private UltrasonicSensor usonic; 	// an instance of an ultrasonic sensor
	private int MAX_DISTANCE = 23; 		// in centimeters  
	private static final double cellD = 23;
	private boolean suppressed = false;
	private World world;
	private Cell curr;
//	private Explore explore;
	
	
	/**
	 * constructor will take the robot as a parameter, 
	 * as well as the TouchSensor.
	 * @param robot
	 * @param frontBump
	 */
	public Avoid(DifferentialPilot robot, TouchSensor frontBump, UltrasonicSensor usonic, World world, Cell curr) {
		
		this.robot = robot;
		this.frontBump = frontBump;
		this.usonic = usonic; 
		this.world = world;
		System.out.println("Avoid");
//		this.explore = explore;
	}

	
	@Override
	public boolean takeControl() { 
		return frontBump.isPressed() || usonic.getDistance() > MAX_DISTANCE;
	}
	
	
	@Override
	public void action() {
		
//		explore.toCheck.remove(explore.curr);
//		explore.world.setVisited(explore.curr);
//		explore.world.setObstacle(explore.curr);
		System.out.println("obstacle detected: " + curr.row + ", " + curr.col);
		world.setObstacle(curr);
		
		suppressed = false;		// set the flag to false
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		// travels backwards by a cell
		robot.travel(-cellD,true); 
		
		try {
			Thread.yield();		// wait till turn is complete
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
	}
	
	@Override
	public void suppress() {
		// will stop this action when it is called:
		suppressed = true;
//		robot.stop(); 
	}
	
	
}