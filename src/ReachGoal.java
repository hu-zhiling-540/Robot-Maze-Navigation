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
	
	private World world;
	private Cell[][] maze;
	private ArrayList<Cell> path;
	
	private static final double cellD = 23;
	private static final short[] note = {2349,115, 0,5, 1760,165, 0,35};
	
//	public Boolean goalReached = false;

	//	public Explore explore;
	
	
	
	private boolean suppressed = false;
	
	
	
	public ReachGoal(DifferentialPilot robot, LightSensor light, World world)		{
		this.robot = robot;
		this.light = light;
//		this.explore = explore;
		this.world = world;		// the world class 
		path = new ArrayList<Cell>();
		
		maze = world.world;		// the grid
	}
	
	
	@Override
	public boolean takeControl() {
		
//		light.getFloodlight() == Color.WHITE
		if(light.readValue() >= 45 ){ 
			System.out.println("WHITE!");
			return true;
		}
		return false;
	}

	
	@Override
	public void action() {
		
		suppressed = false;		// set the flag to false
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		play();
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		// rotate to face back
		robot.rotate(180);
		
		
		// create a path using DFS algorith
		world.createAPath();
		
		// reverse the path
		path = world.reverse();
		
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
		System.out.println("Moving next!!!!!!");
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
