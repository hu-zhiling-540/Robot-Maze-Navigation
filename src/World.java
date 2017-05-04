import java.util.ArrayList;
import java.util.List;


public class World {
	
	public Cell[][] world;	//world grid
	public Cell start;		// start location
	public Cell goal;		// goal location
	
	public ArrayList<Cell> stack;
	public ArrayList<Cell> path;
	
	private int numRows;
	private int numCols;;
	
//	private static final cell_width = 

	public World(int numRows, int numCols, int[] start, int[] goal)	{
		
		this.numRows = numRows;
		this.numCols = numCols;
		
		// generates a numRows * numCols grid
		world = new Cell[numRows][numCols];
		populateWorld();
		
		this.start = world[start[0]][start[1]];		// set up starting cell's coordinates
		this.goal = world[goal[0]][goal[1]];		// set up goal cell's coordinates
		
		stack = new ArrayList<Cell>();
		path = new ArrayList<Cell>();
	}
	
	
	/**
	 * Populates the world grid with cells
	 */
	public void populateWorld() {
		// populate the world with Cells
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++)	{
				world[i][j] = new Cell(i,j);
			}
		}
		
		// walls
		for (int i = 0; i < numRows; i++)	{
			world[i][0].setValue(-1);
			world[i][numCols-1].setValue(-1);
		}
		for (int j = 0; j < numCols; j++)	{
			world[0][j].setValue(-1);
			world[numRows-1][j].setValue(-1);
			
		}
		
		// corner cells
	   	world[1][1].setValue(2);					// (1,1)
	   	world[1][numCols-2].setValue(2);			// (1,8)
	   	world[numRows-2][1].setValue(2);			// (5,1)
	   	world[numRows-2][numCols-2].setValue(2);	// (5,8)
	   	
	   	// other border cells
		for (int i = 2; i < numRows-2; i++)	{
	   		world[i][1].setValue(3);
	   		world[i][numCols-2].setValue(3);
	   	}
	   	for (int j = 2; j < numCols-2; j++)	{
	   		world[1][j].setValue(3);
	   		world[numRows-2][j].setValue(3);
	   	}
	   	
		// inner cells
	   	for(int i = 2; i < numRows-2; i++)	{
    		for(int j = 2; j < numCols - 2; j++)	{
    			world[i][j].setValue(4);
    		}
    	}
	   	
	   	printWorld(world);	// print out the world for checking
	}
	
	
	/**
	 * Sets start state
	 * @param x
	 * @param y
	 */
	public void setStart(int x, int y)	{
		
		path.add(start);
		
		stack.add(0, start);
		
		System.out.println("Depth First Search: ");
		dfs();
		
//		printWorld();
	}
	
	/**
	 * Depth First Search
	 */
	public void dfs()	{
		// pop the stack;
		Cell temp = stack.remove(0);
		
		// if (item popped == final cell)
		if (temp.pos == goal.pos)	{
			System.out.println("Reach Goal!");
//			printPath();
			return;		// done!
		}
		
		else {
			
			ArrayList<Cell> nbrs = new ArrayList<Cell>();
			int row = temp.pos[0];
			int col = temp.pos[1];
			
			// adjacent top cell
			if (world[row+1][col].visited)
				nbrs.add(world[row+1][col]);
			
			// adjacent bottom cell
			if (world[row-1][col].visited)
				nbrs.add(world[row-1][col]);
			
			// adjacent left cell
			if (world[row][col-1].visited)
				nbrs.add(world[row][col-1]);
			
			// adjacent right cell
			if (world[row][col+1].visited)	
				nbrs.add(world[row][col+1]);
			
			if (nbrs.size()>0)	{
				Cell next = nbrs.get(0);
				
//				for (int i = 0; i < nbrs.size(); i++)	{
//					if (nbrs.get(i).value < next.value)
//						next = nbrs.get(i);
				
//				System.out.println("looking next"+ next.col + next.row + next.value);
				path.add(next);
				stack.add(0, next);
			}
				dfs();	
		}
	}
	
	
	
	/**
	 * Checks if the cell is visited before
	 * @param col
	 * @param row
	 * @return
	 */
	public Boolean isVisited(int col, int row)	{
		if (world[row][col].visited)
			return true;
		
		return false;
	}
	/**
	 * A method that reads in two sets of x and y coordinates:
	 * the first set of coordinates is the lower-left corner of the obstacle, 
	 * and the second set is the upper-right corner of the obstacle.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void buildObstacle(int x1, int y1, int x2, int y2)	{
		
		System.out.println("Building an obstacle.");
		int max_x = Math.max(x1, x2);
		int min_x = Math.min(x1, x2);
		int max_y = Math.max(y1, y2);
		int min_y = Math.min(y1, y2);
				
		for (int i = min_x; i <= max_x; i++)	{
			for (int j = min_y; j <= max_y; j++)	{
				world[i][j].setValue(-1);
			}
		}
	}
	
	
//	/**
//	 * Calculates the Manhattan Distance bewteen goal and a certain cell
//	 * @param cell
//	 * @return
//	 */
//	public int manhattanDist(Cell cell)	{
//		int row = cell.pos[0];
//		int col = cell.pos[1];
//		int absD = Math.abs(row - goal.pos[0]) + Math.abs(col - goal.pos[1]);
//		return absD;
//	}
//	
	
	/**
	 * Prints the world and the value each cell holds
	 */
	public void printWorld()	{
		String s = "";
		for (int i = 0; i < world.length; i ++)	{
			for (int j = 0; j < world[0].length; j ++)	{
				s += world[i][j].cellVal + "\t";
			}
			s += "\n";
		}
		System.out.println(s);
	}
	
	/**
	 * Prints the world and the value each cell holds
	 */
	public void printWorld(Cell[][] arr)	{
		String s = "";
		for (int i = 0; i < arr.length; i ++)	{
			for (int j = 0; j < arr[0].length; j ++)	{
				s += arr[i][j].cellVal + "\t";
			}
			s += "\n";
		}
		System.out.println(s);
	}
	
	
	
	
}
