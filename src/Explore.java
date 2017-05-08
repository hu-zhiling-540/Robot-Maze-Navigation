import java.util.ArrayList;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*; 

/**
 * the Explores class implements Behavior, 
 * and travels around and detects obstacle
 *
 */
public class Explore implements Behavior {

	public DifferentialPilot robot;
	public World world;
	public Cell[][] maze;

	public Cell prev;
	public Cell curr;
	
	public ArrayList<Cell> toCheck;
	public static final double cellD = 23;
	
	private boolean suppressed = false;
	
	
	/**
	 * Constructor
	 * @param robot
	 */
	public Explore(DifferentialPilot robot, World world)	{
		
		toCheck = new ArrayList<Cell>();	// works as a stack for cells that to be checked next
		
		this.robot = robot;
		this.world = world;		// the world class 
		
		maze = world.world;		// the grid
		
		prev = world.start;
		curr = prev;
		
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		
		suppressed = false;		// set the flag to false
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		catch(InterruptedException ie) {}
		
		
		/* First, Check current cell */
		
		// if it is a starting cell
		if (curr.row == world.start.row && curr.col == world.start.col)
			world.setVisited(curr);
		
		// if it is identified as an obstacle (cell value = -1)
		if (curr.isObstacle())		{
			toCheck.remove(curr);
			prev.removeAPath();		// a path has been found as a dead end
			curr = prev;	// backtracking
		}
		
		// for next step
		// explore adjacent cells 
		checkAround(curr);
		
		// after checking adjacent cells, nothing in the stack
		if (toCheck.isEmpty() && curr.isDeadEnd())
			return;		// exit
		
		Cell temp = toCheck.remove(0);		// next step to be taken
		
		// have the chance to walk back to the previous cell
		if (temp.row == prev.row && temp.col == prev.col)
			prev = curr;	// override it to be the current cell
		
		// other cells
		else	{
			
			// if prev and curr are in the same row
			if (prev.row == curr.row)		{
				
				// temp is in the lower left corner
				if ((temp.row < prev.row && temp.col < prev.col) 
						||(temp.row > prev.row && temp.col > prev.col))	{	// or upper right corner
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, temp are not in the same row, we can go right
				else if (curr.row != temp.row)	{
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, temp are in the same row, no need to rotate
				
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
				// temp is in the upper right corner
				if ((temp.row > prev.row && temp.col > prev.col) 
						||(temp.row < prev.row && temp.col < prev.col))		{	// or lower left corner
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if prev, curr, temp are not in the same col, we can go left
				else if (curr.col != temp.col)	{
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				// if prev, curr, temp are in the same col, no need to rotate
				
				// travel in the designated direction
				robot.travel(cellD);
				try {
					Thread.yield();
					Thread.sleep(1000); // stops for a short time (one second)
				}
				
				catch(InterruptedException ie) {}
				
			}
		}
		
		world.setVisited(curr);		// sync with the maze in the world
		prev = curr;
		curr = temp;
		
	}
	
	
	/**
	 * Check four adjacent cells, and add to array if it satifies the conditionals
	 * @param cell
	 */
	public void checkAround(Cell cell)	{
		int counter = 0;
		
		// up
		if (!maze[cell.row+1][cell.col].isObstacle() && !maze[cell.row+1][cell.col].isDeadEnd() && !maze[cell.row+1][cell.col].visited)	{
			toCheck.add(0,maze[cell.row+1][cell.col]);
			counter ++;
			System.out.println("check up");
		}
		// down
		if (!maze[cell.row-1][cell.col].isObstacle() && !maze[cell.row-1][cell.col].isDeadEnd() && !maze[cell.row-1][cell.col].visited)	{
			toCheck.add(0,maze[cell.row-1][cell.col]);
			System.out.println("check down");
			counter ++;
		}
		// left
		if (!maze[cell.row][cell.col-1].isObstacle() && !maze[cell.row][cell.col-1].isDeadEnd() && !maze[cell.row][cell.col-1].visited)	{
			toCheck.add(0,maze[cell.row][cell.col-1]);
			System.out.println("check left");
			counter ++;
		}
		// right
		if (!maze[cell.row][cell.col+1].isObstacle() && !maze[cell.row][cell.col+1].isDeadEnd() && !maze[cell.row][cell.col+1].visited)	{
			toCheck.add(0,maze[cell.row][cell.col+1]);
			System.out.println("check right");
			counter ++;	
		}
		
		// if no possible routes emerging from it
		if(counter == 0)
			cell.setDeadEnd();
	}
	
	
	@Override
	public void suppress() {
//		suppressed = true;
		robot.stop();
	}

}
