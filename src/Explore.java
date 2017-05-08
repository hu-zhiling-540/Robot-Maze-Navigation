import java.util.ArrayList;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 

/**
 * the Explores class implements Behavior, 
 * and travels around and detects obstacle
 * If no other behaviors are in command,
 * this is always active and has no trigger 
 *
 */
public class Explore implements Behavior {

	public DifferentialPilot robot;
	public World world;

	private static final double cellD = -23;
	
//	private boolean suppressed = false;
	
	
	/**
	 * Constructor
	 * @param robot
	 */
	public Explore(DifferentialPilot robot, World world)	{
		
		this.robot = robot;
		this.world = world;		// the world class 
		
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	/*Notice that the method
	must not go into a never-ending loop, which will cause the arbitration
	process to no longer work. */
	
	@Override
	public void action() {
		
//		suppressed = false;		// set the flag to false
		
		int c = world.commandForExplore();
		commandDecode(c);
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
	}
	
	
	/**
	 * Decodes the command associated with rotation degree
	 * @param c
	 */
	public void commandDecode(int c)	{
		// current cell is a dead end, backtrack
		if (c == 1)	{
			System.out.println("back");
			robot.travel(-cellD);	//move backward for one cell distance
		}
		
		// left turn
		else if ( c == -90)		{
			robot.rotateLeft();
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			catch(InterruptedException ie) {}
			
			robot.travel(cellD);
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			catch(InterruptedException ie) {}
		}
		
		// right turn
		else if ( c == 90)	{
			robot.rotateRight();
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			catch(InterruptedException ie) {}
			
			robot.travel(cellD);
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			catch(InterruptedException ie) {}
		}
		
		// same direction, no need to rotate
		else	{
			robot.travel(cellD);
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			catch(InterruptedException ie) {}
		}	
	}
	
	/*
	Your implementation of this method will stop
	the current running action (stopping motors and so on).The method
	should not return until the running action has terminated (but it must
	return eventually for the arbitration process to work).
			*/
	@Override
	public void suppress() {
//		suppressed = true;
		robot.stop();
	}

}
