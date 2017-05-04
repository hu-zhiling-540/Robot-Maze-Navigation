import java.util.ArrayList;

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

	public Cell prev;
	public Cell curr;
	
	public ArrayList<Cell> toCheck;
	public ArrayList<Cell> path;
	public static final int cellD = 30;
	
	public boolean reachGoal;
	
	
	/**
	 * constructor
	 * @param robot
	 */
	public Explore(DifferentialPilot robot, World world)	{
		
		toCheck = new ArrayList<Cell>();	// works as a stack for cells that to be checked next
		path = new ArrayList<Cell>();		// records the path of walking through the maze
		
		this.robot = robot;
		this.world = world;		// the world class 
		
		maze = world.world;		// the grid
		
		curr = world.start;
		prev = world.start;
		
		
		System.out.println("Explore");
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {

		// if goal is reached
		if (reachGoal)	{
			
			// if never visited or a real obstacle, mark the cell as obstacle
			// set start point
			
			// create a path using DFS algorith
			world.createAPath();
			
			// reverse the path
			path = world.reverse();
			
			prev = path.get(0);
			curr = path.remove(0);		// goal cell
			
			// walk the robot back to the starting cell
			
			Cell temp = path.remove(0);		// next step to be taken
			
			while (!path.isEmpty())	{
				
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
					
					// if not in the same direction
					else if (curr.row != temp.row)	{
						robot.rotateRight();
						try {
							Thread.yield();
							Thread.sleep(1000); // stops for a short time (one second)
						}
						
						catch(InterruptedException ie) {}
					}
					
					// if in the same direction, no need to rotate
				}
				
				// if prev and curr are in the same col
				else		{
					// temp is in the lower right corner
					if ((temp.row > prev.row && temp.col > prev.col) 
							||(temp.row < prev.row && temp.col < prev.col))		{	// or upper left corner
						robot.rotateLeft();
						try {
							Thread.yield();
							Thread.sleep(1000); // stops for a short time (one second)
						}
						
						catch(InterruptedException ie) {}
					}
					
					// if not in the same direction
					else if (curr.col != temp.col)	{
						robot.rotateRight();
						try {
							Thread.yield();
							Thread.sleep(1000); // stops for a short time (one second)
						}
						
						catch(InterruptedException ie) {}
					}
					
					// if in the same direction, no need to rotate

				}
				
				// travel in the designated direction
				robot.travel(cellD);
				try {
					Thread.yield();
					Thread.sleep(1000); // stops for a short time (one second)
				}
				
				catch(InterruptedException ie) {}
				
				prev = curr;
				curr = temp;
			}
		}
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		
		catch(InterruptedException ie) {}
		
		// if it is a starting cell
		if (curr.row == world.start.row && curr.col == world.start.col)	{
			world.setVisited(curr);
			path.add(maze[curr.row][curr.col]);
//			checkAround(curr);
		}
		else	{
//			// when we reach to this cell, we must have come from its adjacent cell
//			// thus a path has been explored
//			curr.removeAPath();
			
			// check about the current cell
			// at least one path hasn't been tried on curr cell (i.e not a dead end) 
//			if (curr.cellVal > 1)
//				toCheck.add(0, prev);	// put previous cell back 
			
			// once identified as an obstacle (cell value = -1)
			if (curr.isObstacle())	{
				toCheck.remove(curr);
				prev.removeAPath();		// a path has been found as a dead end
				curr = prev;	// backtracking
			}
			
			// has more than one path remaining && not in a path
			if(curr.cellVal > 1 && !path.contains(curr))
				path.add(curr);
		}
		
		// for next step
		// explore adjacent cells 
		checkAround(curr);
		
		// after checking adjacent cells, nothing in the stack
		if (toCheck.isEmpty() && curr.isDeadEnd())
			return;		// exit
		
		Cell temp = toCheck.remove(0);		// next step to be taken
		
		// have the chance to walk back to the previous cell
		if (temp.row == prev.row && temp.col == prev.col)	{
			prev = curr;	// override it to be the current cell
//			robot.rotate(180);
//			robot.travel(cellD);
		}
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
				
				// if not in the same direction
				else if (curr.row != temp.row)	{
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if in the same direction, no need to rotate
			}
			
			// if prev and curr are in the same col
			else		{
				// temp is in the lower right corner
				if ((temp.row > prev.row && temp.col > prev.col) 
						||(temp.row < prev.row && temp.col < prev.col))		{	// or upper left corner
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if not in the same direction
				else if (curr.col != temp.col)	{
					robot.rotateRight();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if in the same direction, no need to rotate

			}
			
			// travel in the designated direction
			robot.travel(cellD);
			try {
				Thread.yield();
				Thread.sleep(1000); // stops for a short time (one second)
			}
			
			catch(InterruptedException ie) {}
		}
		
		world.setVisited(curr);		// sync with the maze in the world
		prev = curr;
		curr = temp;
	}
	
	
	public void checkAround(Cell cell)	{
		int counter = 0;
		
		// up
		if (!maze[cell.row+1][cell.col].isObstacle() && !maze[cell.row+1][cell.col].isDeadEnd())	{
			toCheck.add(0,maze[cell.row+1][cell.col]);
			counter ++;
		}
		// down
		if (!maze[cell.row-1][cell.col].isObstacle() && !maze[cell.row-1][cell.col].isDeadEnd())	{
			toCheck.add(0,maze[cell.row-1][cell.col]);
			counter ++;
		}
		// left
		if (!maze[cell.row][cell.col-1].isObstacle() && !maze[cell.row][cell.col-1].isDeadEnd())	{
			toCheck.add(0,maze[cell.row][cell.col-1]);
			counter ++;
		}
		// right
		if (!maze[cell.row][cell.col+1].isObstacle() && !maze[cell.row][cell.col+1].isDeadEnd())	{
			toCheck.add(0,maze[cell.row][cell.col+1]);
			counter ++;	
		}
		
		// if no possible routes emerging from it
		if(counter == 0)
			cell.setDeadEnd();
	}
	
	@Override
	public void suppress() {
		robot.stop();	
	}

}
