import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 

/**
 * the Wander class implements Behavior, 
 * and travels around and rotates randomly 
 *
 */
public class Wander implements Behavior{

	public DifferentialPilot robot;
	
	
	/**
	 * constructor
	 * @param robot
	 */
	public Wander(DifferentialPilot robot)	{
		this.robot = robot;
		//System.out.println("Wander");
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		int random = (int) (Math.random() * 3);		// creates a random number from 0 to 2
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		
		catch(InterruptedException ie) {}
		
		if (random == 0)
			robot.forward();
		else if (random == 1)
			robot.backward();
		else	{
			int ranAngle = (1+(int) (Math.random() * 3))*90;	// rotates at a random angle
			robot.rotate(ranAngle);
		}
		
	}

	@Override
	public void suppress() {
		robot.stop();	
	}

}
