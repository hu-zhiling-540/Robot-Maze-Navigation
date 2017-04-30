import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 

/**
 * the Wander class implements Behavior, 
 * and travels around and rotates randomly 
 *
 */
public class Explore implements Behavior{

	public DifferentialPilot robot;
	public World world;
	public Cell[][] maze;
	public Cell curr;
	public Cell prev;
	public ArrayList<Cell> toBeVisited;
	public ArrayList<Cell> path;
	
	
	/**
	 * constructor
	 * @param robot
	 */
	public Explore(DifferentialPilot robot, World world)	{
		toBeVisited = new ArrayList<Cell>();
		path = new ArrayList<Cell>();
		this.robot = robot;
		this.world = world;
		maze = this.world.world;
		curr = this.world.start;
		
		
		System.out.println("Explore");
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
//		int random = (int) (Math.random() * 3);		// creates a random number from 0 to 2
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		
		catch(InterruptedException ie) {}
		
		// has more than one path remaining
		if (curr.cellVal > 1 && !toBeVisited.contains(curr))
			toBeVisited.add(0, curr);
		// once identified as an obstacle
		else if (curr.cellVal == -1)	{
			path.remove(curr);
			toBeVisited.remove(curr);
			prev.removeAPath();
			curr = prev;	// backtracking
		}
		
			
//		if (random == 0)
//			robot.forward();
//		else if (random == 1)
//			robot.backward();
//		else	{
//			int ranAngle = (1+(int) (Math.random() * 3))*90;	// rotates at a random angle
//			robot.rotate(ranAngle);
//		}
		
	}
	public void checkAround(Cell cell)	{
		int row = cell.pos[0];
		int col = cell.pos[1];
		// up
		if (!maze[row+1][col].isObstacle())
			toBeVisited.add(0,maze[row+1][col]);
		// down
		if (!maze[row-1][col].isObstacle())
			toBeVisited.add(0,maze[row-1][col]);
		// left
		if (!maze[row][col-1].isObstacle())
			toBeVisited.add(0,maze[row][col-1]);
		// right
		if (!maze[row][col+1].isObstacle())
			toBeVisited.add(0,maze[row][col+1]);
		
	}
	@Override
	public void suppress() {
		robot.stop();	
	}

}
