import java.util.ArrayList;

//import lejos.nxt.LightSensor;
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
//	public Cell next;
	
	public ArrayList<Cell> toCheck;
	public ArrayList<Cell> path;
	public static final int cellD = 30;
	
	public boolean reachGoal;
	
	/**
	 * constructor
	 * @param robot
	 */
	public Explore(DifferentialPilot robot, World world)	{
		toCheck = new ArrayList<Cell>();
		path = new ArrayList<Cell>();
		this.robot = robot;
		this.world = world;
		maze = this.world.world;
		
		curr = this.world.start;
		prev = this.world.start;
		
		
		System.out.println("Explore");
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {

		if (reachGoal)	{
			// if never visited or a real obstacle, mark the cell as obstacle
			// set start point
		}
		
		try {
			Thread.yield();
			Thread.sleep(1000); // stops for a short time (one second)
		}
		
		catch(InterruptedException ie) {}
		
		// if it is a starting cell
		if (curr.pos == world.start.pos)	{
			curr.setVisited();
			path.add(curr);
//			checkAround(curr);
		}
		else	{
//			// when we reach to this cell, we must have come from its adjacent cell
//			// thus a path has been explored
//			curr.removeAPath();
			
			// check about the current cell
			// at least one path hasn't been tried on curr cell (i.e not a dead end) 
			if (curr.cellVal > 1)
				toCheck.add(0, prev);	// put previous cell back 
			
			// once identified as an obstacle (cell value = -1)
			else if (curr.isObstacle())	{
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
		// compare positing to match direction
		
		
		// goes back to previous cell
		if (temp.pos == prev.pos)	{
			prev = curr;	// override it to be the current cell
//			robot.rotate(180);
//			robot.travel(cellD);
		}
		else	{
			// if prev and curr are in the same row
			if (prev.pos[0] == curr.pos[0])		{
				// temp is in the lower left corner
				if ((temp.pos[0] < prev.pos[0] && temp.pos[1] < prev.pos[1]) 
						||(temp.pos[0] > prev.pos[0] && temp.pos[1] > prev.pos[1]))	{	// upper right corner
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if not in the same direction
				else if (curr.pos[0] != temp.pos[0])	{
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
				if ((temp.pos[0] > prev.pos[0] && temp.pos[1] > prev.pos[1]) 
						||(temp.pos[0] < prev.pos[0] && temp.pos[1] < prev.pos[1]))		{// upper left corner
					robot.rotateLeft();
					try {
						Thread.yield();
						Thread.sleep(1000); // stops for a short time (one second)
					}
					
					catch(InterruptedException ie) {}
				}
				
				// if not in the same direction
				else if (curr.pos[1] != temp.pos[1])	{
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
		
		prev = curr;
		curr = temp;
	}
	
	public void checkAround(Cell cell)	{
		int row = cell.pos[0];
		int col = cell.pos[1];
		int counter = 0;
		
		// up
		if (!maze[row+1][col].isObstacle() && !maze[row+1][col].isDeadEnd())	{
			toCheck.add(0,maze[row+1][col]);
			counter ++;
		}
		// down
		if (!maze[row-1][col].isObstacle() && !maze[row-1][col].isDeadEnd())	{
			toCheck.add(0,maze[row-1][col]);
			counter ++;
		}
		// left
		if (!maze[row][col-1].isObstacle() && !maze[row][col-1].isDeadEnd())	{
			toCheck.add(0,maze[row][col-1]);
			counter ++;
		}
		// right
		if (!maze[row][col+1].isObstacle() && !maze[row][col+1].isDeadEnd())	{
			toCheck.add(0,maze[row][col+1]);
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
