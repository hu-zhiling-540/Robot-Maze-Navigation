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
	private int AVOID_DISTANCE = 23; 		// in centimeters  
	private static final double cellD = 23;
	private boolean suppressed;
	private Explore explore;
	
	
	/**
	 * Constructor will take the robot as a parameter, 
	 * as well as the TouchSensor.
	 * @param robot
	 * @param frontBump
	 * @param curr 
	 * @param world 
	 */
	public Avoid(DifferentialPilot robot, TouchSensor frontBump, UltrasonicSensor usonic, Behavior explore) {
		
		/* NEW */
		suppressed = false;
		
		this.robot = robot;
		this.frontBump = frontBump;
		this.usonic = usonic;
		System.out.println("Avoid");
		this.explore = (Explore) explore;
	}

	
	@Override
	public boolean takeControl() { 
		return frontBump.isPressed() || usonic.getDistance() < AVOID_DISTANCE;
	}
	
	
	@Override
	public void action() {
		
		suppressed = false;		// set the flag to false
		
		explore.toCheck.remove(explore.curr);
		explore.curr.setValue(-1);
		explore.world.setVisited(explore.curr);
		explore.world.setObstacle(explore.curr);
		
	
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		// travels backwards by a cell
		robot.travel(-cellD,true); 
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		/* NEW; Not Sure */
		robot.stop();
	}
	
	@Override
	public void suppress() {
		// will stop this action when it is called:
		suppressed = true;
		/* Not Sure Comment this one out */
//		robot.stop(); 
	}
	
	
}