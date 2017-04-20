import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 
import lejos.nxt.*;


/**
 * the Avoid class implements Behavior, 
 * and deals with obstacle avoidance 
 * @author Guest
 *
 */
public class Avoid implements Behavior{
	
	public DifferentialPilot robot;

	public TouchSensor frontBump;
	public boolean frontPressed;
	
	public static final int cellD = 30;
	
	
	/**
	 * constructor will take the robot as a parameter, 
	 * as well as the TouchSensor.
	 * @param robot
	 * @param frontBump
	 */
	public Avoid(DifferentialPilot robot, TouchSensor frontBump) {
		
		this.robot = robot;
		this.frontBump = frontBump;
		frontPressed = false;
		//System.out.println("Avoid");
	}
	
	@Override
	public boolean takeControl() {
		return frontBump.isPressed();
	}
	
	@Override
	public void action() {
		
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

}