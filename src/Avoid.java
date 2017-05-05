import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 
import lejos.nxt.*;


/**
 * the Avoid class implements Behavior, 
 * and deals with obstacle avoidance
 *
 */
public class Avoid implements Behavior{
	
	public DifferentialPilot robot;

	public TouchSensor frontBump;		// an instance of a touch sensor
	public UltrasonicSensor usonic; 	// an instance of an ultrasonic sensor
	public int MAX_DISTANCE = 25; 		// in centimeters  
	
	
	public boolean frontPressed;
	
	public Explore explore;
	
	public static final int cellD = 30;
	
	
	/**
	 * constructor will take the robot as a parameter, 
	 * as well as the TouchSensor.
	 * @param robot
	 * @param frontBump
	 */
	public Avoid(DifferentialPilot robot, TouchSensor frontBump, UltrasonicSensor usonic, Explore explore) {
		
		this.robot = robot;
		this.frontBump = frontBump;
		this.usonic = usonic; 
		frontPressed = false;
		System.out.println("Avoid");
		this.explore = explore;
	}

	
	@Override
	public boolean takeControl() { 
		return frontBump.isPressed() || usonic.getDistance() > MAX_DISTANCE;
	}
	
	
	@Override
	public void action() {
		
		explore.world.setVisited(explore.curr);
		explore.world.setObstacle(explore.curr);
		explore.toCheck.remove(explore.curr);
		System.out.println("obstacle detected: " + explore.curr.row + ", " + explore.curr.col);
		
		// not sure about this line
		robot.stop();
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}

		// travels backwards by a cell
		robot.travel(-cellD,true); 
//		
//		int random = (int) Math.random() * 10;	// creates a random integer either even or odd
//		if(random % 2 == 0)
//			robot.rotate(90);
//		else
//			robot.rotate(-90);	
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		// not sure about this line
		robot.stop(); 
	}
	
	@Override
	public void suppress() { 
		robot.stop(); 
	}
	
	
}