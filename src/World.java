import java.util.ArrayList;


public class World {
	
	public Cell[][] world;	//world grid
	public Cell start;		// start location
	public Cell goal;		// goal location
	
	public ArrayList<Cell> stack;
	public ArrayList<Cell> path;
	
	private int numRows;
	private int numCols;;
	

	public World(int numRows, int numCols, int[] start, int[] goal)	{
		
		this.numRows = numRows;
		this.numCols = numCols;
		
		// generates a numRows * numCols grid
		world = new Cell[numRows][numCols];
		populateWorld();
		
		this.start = world[start[0]][start[1]];		// set up starting cell's coordinates
		this.goal = world[goal[0]][goal[1]];		// set up goal cell's coordinates
		
		
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
	 */
	public void createAPath()	{
		
		stack = new ArrayList<Cell>();
		path = new ArrayList<Cell>();
		
		path.add(start);		// adds starting cell as the first step
		stack.add(0, start);	// adds starting cell
		dfs();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Cell> reverse()	{
		ArrayList<Cell> back = new ArrayList<Cell>();
		for (int i = path.size()-1; i >= 0; i--)
			back.add(path.get(i));
		return back;
	}
	
	
	/**
	 * Finds the cell in the maze and marks it visited
	 * @param cell
	 */
	public void setVisited(Cell cell)	{
		world[cell.row][cell.col].setVisited();
	}
	
	
	/**
	 * Depth First Search
	 */
	public void dfs()	{
		// pop the stack;
		Cell temp = stack.remove(0);
		
		// if it is the goal
		if (temp.row == goal.row && temp.col == goal.col)	{
			return;		// done!
		}
		
		else {
			
			ArrayList<Cell> nbrs = new ArrayList<Cell>();
			
			// adjacent top cell
			if (world[temp.row+1][temp.col].visited)
				nbrs.add(world[temp.row+1][temp.col]);
			
			// adjacent bottom cell
			if (world[temp.row-1][temp.col].visited)
				nbrs.add(world[temp.row-1][temp.col]);
			
			// adjacent left cell
			if (world[temp.row][temp.col-1].visited)
				nbrs.add(world[temp.row][temp.col-1]);
			
			// adjacent right cell
			if (world[temp.row][temp.col+1].visited)	
				nbrs.add(world[temp.row][temp.col+1]);
			
			if (nbrs.size()>0)	{
				Cell next = nbrs.get(0);
				path.add(next);
				stack.add(0, next);
			}

			dfs();		// recursive call
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
