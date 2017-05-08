import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.nxt.ColorSensor.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;


/**
 * The goal class implements Behavior, 
 * plays a short sample music when reaching the goal,
 * generates the final path ,
 * and travels back to the starting cell
 *
 */
public class ReachGoal implements Behavior{

	public DifferentialPilot robot;
	private LightSensor light;
	private int init_light_value;
	private World world;
	private Cell[][] maze;
	private ArrayList<Cell> path;
	
	private static final double cellD = 23;
	private static final short[] note = {2349,115, 0,5, 1760,165, 0,35};
	
	private boolean suppressed = false;
	
	
	/**
	 * Constructor
	 * @param robot
	 * @param light
	 * @param world
	 */
	public ReachGoal(DifferentialPilot robot, LightSensor light, World world, int init_light_value)		{
		this.robot = robot;
		this.light = light;
		this.world = world;			// the world class 
		
		path = new ArrayList<Cell>();
		
		this.init_light_value = init_light_value;
		
		maze = world.maze;			// the grid
	}
	
	
	@Override
	public boolean takeControl() {
		/*light.getFloodlight() == light.WHITE NOTSURE */
		//if there is a significant value difference of light
		if((light.readValue()-init_light_value) > 10 || light.getFloodlight() == light.WHITE ||
				light.getFloodlight() == Color.WHITE){ 		// or a white cell is detected
			System.out.println("Reach Goal!");
			return true;
		}
		return false;
	}

	
	@Override
	public void action() {
		
		play();			// plays the music
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}

		
		rotateBack();	// new orientation facing starting cell
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		
		walkBack();		// walk back to the starting cell
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		System.exit(0);
	}
	
	
	/**
	 * Rotates back to face the starting cell
	 */
	public void rotateBack()	{
		char o = world.getCurrOrient();
		switch (o)	{
		case 'E':
			robot.rotateRight();
			break;
		case 'W':
			robot.rotateLeft();
			break;
		case 'N':
			robot.rotate(180);
			break;
		case 'S':
			break;
		}
	}
	
	
	/**
	 * Walks back to the starting cell
	 */
	public void walkBack()	{
		
		// create a path using DFS algorith
		world.getDFSPath();
		
		// reverse the path
		path = world.getReversePath();
		
		Cell prev = path.get(0);
		Cell curr = path.remove(0);		// goal cell
		
		// walk the robot back to the starting cell
		
		Cell next = path.remove(0);		// next step to be taken
		
		while (!path.isEmpty())	{
			
			if (prev.row == curr.row)		{
				
				// next is in the lower left corner
				if ((next.row < prev.row && next.col < prev.col) 
						||(next.row > prev.row && next.col > prev.col))	{	// or upper right corner
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, next are not in the same row, we can go right
				else if (curr.row != next.row)	{
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, next are in the same row, no need to rotate
				
				// travel in the designated direction
				robot.travel(cellD);
				try {
					Thread.yield();
					Thread.sleep(1000); // stops for a short time (one second)
				}
				
				catch(InterruptedException ie) {}
			}
			
			// if prev and curr are in the same col
			else		{
				// next is in the upper right corner
				if ((next.row > prev.row && next.col > prev.col) 
						||(next.row < prev.row && next.col < prev.col))		{	// or lower left corner
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, next are not in the same col, we can go left
				else if (curr.col != next.col)	{
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				// if prev, curr, next are in the same col, no need to rotate
				
				// travel in the designated direction
				robot.travel(cellD);
				try {
					Thread.yield();
					Thread.sleep(1000); // stops for a short time (one second)
				}
				
				catch(InterruptedException ie) {}
				
			}
		}
		prev = curr;
		curr = next;
	}
	
	
	/**
	 *Will play the tone stored in the note array
	 *	 
	 **/
	public void play() {
		for(int i=0; i<note.length; i+=2) {
			final short w = note[i+1];
			Sound.playTone(note[i], w);
			Sound.pause(w*10);
			if (suppressed)
				return; // exit this method if suppress is called
		}
	}
	
	
	@Override
	public void suppress() {
		robot.stop();	
		
	}

}
