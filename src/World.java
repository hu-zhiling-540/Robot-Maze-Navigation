import java.util.ArrayList;

/**
 * maze that holds the 2D array
 * and information on start and goal cells
 *  contains a lot of getter methods which return variables for the other classes
in the application to use,
 *
 */
public class World {
	
	public Cell[][] maze;	//maze grid
//	public Cell start;		// start location
//	public Cell goal;		// goal location
	
	public int[] start;		// start location
	public int[] goal;		// goal location
	
	public ArrayList<Cell> newPath;
	public ArrayList<Cell> path;
	public ArrayList<Cell> stack;
	
	private int numRows;
	private int numCols;;
	
	private boolean goalReached = false;
	
	private int curr_row;
	private int curr_col;
	
	private static final double cellD = 23;
	

	/**
	 * Constructor
	 * @param numRows
	 * @param numCols
	 * @param start
	 * @param goal
	 */
	public World(int numRows, int numCols, int[] start, int[] goal)	{
		
		this.numRows = numRows;
		this.numCols = numCols;
		
		// generates a numRows * numCols grid
		maze = new Cell[numRows][numCols];
		populatemaze();
		
		curr_row = start[0];
		curr_col = start[1];
		
		this.start = start;
		this.goal = goal;
		
		path = new ArrayList<Cell>();
		path.add(getCurrCell());		// (1,1)
		
//		this.start = maze[start[0]][start[1]];		// set up starting cell's coordinates
//		this.goal = maze[goal[0]][goal[1]];		// set up goal cell's coordinates
	}
	
	/**
	 * Populates the grid with cells,
	 * and marks cell value respectively
	 */
	public void populatemaze() {
		
		// populate the maze with Cells
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++)	{
				maze[i][j] = new Cell(i,j);
			}
		}
		
		// walls
		for (int i = 0; i < numRows; i++)	{
			maze[i][0].setObstacle();
			maze[i][numCols-1].setObstacle();
		}
		for (int j = 0; j < numCols; j++)	{
			maze[0][j].setObstacle();
			maze[numRows-1][j].setObstacle();
			
		}
		
		// corner cells
	   	maze[1][1].setValue(2);					// (1,1)
	   	maze[1][numCols-2].setValue(2);			// (1,8)
	   	maze[numRows-2][1].setValue(2);			// (5,1)
	   	maze[numRows-2][numCols-2].setValue(2);	// (5,8)
	   	
	   	// other border cells
		for (int i = 2; i < numRows-2; i++)	{
	   		maze[i][1].setValue(3);
	   		maze[i][numCols-2].setValue(3);
	   	}
		
	   	for (int j = 2; j < numCols-2; j++)	{
	   		maze[1][j].setValue(3);
	   		maze[numRows-2][j].setValue(3);
	   	}
	   	
		// inner cells
	   	for(int i = 2; i < numRows-2; i++)	{
    		for(int j = 2; j < numCols - 2; j++)	{
    			maze[i][j].setValue(4);
    		}
    	}
	}
	
	
	/**
	 * Checks if the cell is visited before
	 * @param col
	 * @param row
	 * @return
	 */
	public Boolean isVisited(int row, int col)	{
		if (maze[row][col].visited)
			return true;
		
		return false;
	}

	/**
	 * Identifies whether a cell is a legal move
	 * by checking the cell value
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isValidMove(int row, int col) {
		// cell value is -1 or =1
		if (maze[row][col].isObstacle() || maze[row][col].isDeadEnd())
			return false;
		return true;
	}

	
	/**
	 * Finds the cell in the maze and marks it visited
	 * @param cell
	 */
	public void obstacleDected()	{
		System.out.println("obstacle dected: " + curr_row + ", " + curr_col);
		char o = getCurrOrient();
		// sets the cell ahead of it to be an obstacle
		switch (o)	{
		case 'E':
			maze[curr_row][curr_col+1].setObstacle();
			obstacleAround(curr_row,curr_col+1);
			break;
		case 'W':
			maze[curr_row][curr_col-1].setObstacle();
			obstacleAround(curr_row,curr_col-1);
			break;
		case 'N':
			maze[curr_row+1][curr_col].setObstacle();
			obstacleAround(curr_row+1,curr_col);
			break;
		case 'S':
			maze[curr_row-1][curr_col].setObstacle();
			obstacleAround(curr_row-1,curr_col);
			break;
		case 'Y':
			System.out.println("null orient");
			break;
		}
		
	}

	/**
	 * An obstacle is detected. 
	 * All adjacent cells should remove a path.
	 * @param row
	 * @param col
	 */
	public void obstacleAround(int row, int col)	{
		
		maze[row+1][col].removeAPath();		// up
		maze[row-1][col].removeAPath();		// down
		maze[row][col-1].removeAPath();		// left
		maze[row][col+1].removeAPath();		// right
	 }
	
	
	/**
	 * Finds the cell in the maze and marks it visited
	 * @param cell
	 */
	public void setVisited(int row, int col)	{
		maze[row][col].setVisited();
	}

	
	/**
	 * Marks the current cell as an obstacle
	 */
	public void setCurrObstacle() {
		maze[curr_row][curr_col].setObstacle();
	}

	
	/**
	 * Returns the current cell
	 * @return
	 */
	public Cell getCurrCell()	{
		return maze[curr_row][curr_col];
	}

	/**
	 * Compares previous cell and current cell 
	 * to get an orientation
	 * @return
	 */
	public char getCurrOrient()	{
		Cell prev = getPrev();
		if (prev.row == curr_row)	{
			if (prev.col < curr_col)
				return 'E';		// facing east
			else if (prev.col > curr_col)
				return 'W';		// facing west
		}
		else if (prev.col == curr_col)	{
			if (prev.row < curr_row)
				return 'N';		// facing north
			else if (prev.col > curr_col)
				return 'S';		// facing south
		}
		return 'Y';		// should not return such
	}

	/**
	 * Getter for the previous cell
	 * @return
	 */
	public Cell getPrev()	{
		return path.get(path.size()-1);
	}

	/**
	 * Resets the current cell to the cell passed in
	 * by updating the coordinates
	 * @param cell
	 */
	public void updateCurrCell(Cell cell)	{
		curr_row = cell.row;
		curr_col = cell.col;
	}

	/**
	 * A method that will be called by Explore behavior
	 * to find a possible move and orientation for that move if exists
	 * @return
	 */
	public int commandForExplore()	{
		
		int command = -1;	// nothing found
		
		Cell next = nextCell();
		
		if (next != null)	{
			command = orientation(next);
			path.add(next);		// takes in next cell
			updateCurrCell(next);
		}
		
		// next == null; dead end
		else
			command = 1;	// backtrack to prev cell
		
		return command;
			
	}
	

	/**
	 * Finds the succeeding step to be taken
	 * @return
	 */
	public Cell nextCell()	{
		/* can be transformed to global */
		ArrayList<Cell> possibleMoves = checkAdjacent();
		Cell next = null;
		// if there is at least one route left
		if (!getCurrCell().isDeadEnd() || !possibleMoves.isEmpty())
			next = possibleMoves.remove(0);	// pop out the first cell
		
		// reach to a dead end, should backtrack
		else	{
			// set back to previous cell
			updateCurrCell(getPrev());
			// next will be null for this case
		}
		
		return next;
		
	}

	/**
	 * 
	 * It will check through adjacent neighbors to find all legal states 
	 * succeeding the current cell
	 */
	public ArrayList<Cell> checkAdjacent()	{
		
		ArrayList<Cell> nbrs = new ArrayList<Cell>();	// a list of neighbors
		int counter = 0;	// keeps track of number of emerging routes
			
		// up
		if (isValidMove(curr_row+1, curr_col)){
//		if (!maze[curr_row+1][curr_col].isObstacle() && !maze[curr_row+1][curr_col].isDeadEnd() && !maze[curr_row+1][curr_col].visited)	{
			nbrs.add(0,maze[curr_row+1][curr_col]);
			counter ++;
			System.out.println("check up");
		}
		// down
		if (isValidMove(curr_row-1, curr_col)){
//		if (!maze[curr_row-1][curr_col].isObstacle() && !maze[curr_row-1][curr_col].isDeadEnd() && !maze[curr_row-1][curr_col].visited)	{
			nbrs.add(0,maze[curr_row-1][curr_col]);
			System.out.println("check down");
			counter ++;
		}
		// left
		if (isValidMove(curr_row, curr_col-1)){
//		if (!maze[curr_row][curr_col-1].isObstacle() && !maze[curr_row][curr_col-1].isDeadEnd() && !maze[curr_row][curr_col-1].visited)	{
			nbrs.add(0,maze[curr_row][curr_col-1]);
			System.out.println("check left");
			counter ++;
		}
		// right
		if (isValidMove(curr_row, curr_col+1)){
//		if (!maze[curr_row][curr_col+1].isObstacle() && !maze[curr_row][curr_col+1].isDeadEnd() && !maze[curr_row][curr_col+1].visited)	{
			nbrs.add(0,maze[curr_row][curr_col+1]);
			System.out.println("check right");
			counter ++;	
		}
		
		// no possible routes emerging from it
		if(counter == 0)
			getCurrCell().setDeadEnd();	// visited and no routes left
		
		return nbrs;
	 }

	/**
	 * Identifies the orientation in order to make the next move
	 * @param next
	 * @return
	 */
	public int orientation(Cell next)	{
		/* NEEDS TO BE DISCUSSED */
		Cell temp = next;
		Cell prev = getPrev();
		
		// if prev and curr are in the same row
		if (prev.row == getCurrCell().row)		{
			
			// temp is in the lower left corner
			if ((temp.row < prev.row && temp.col < prev.col) 
					||(temp.row > prev.row && temp.col > prev.col))	{	// or upper right corner
				return -90;		// left
			}
			
			// if prev, curr, temp are not in the same row, we can go right
			else if (getCurrCell().row != temp.row)
				return 90;		// right
			
			// if prev, curr, temp are in the same row, no need to rotate
			else
				return 0;
		}
		
		// if prev and curr are in the same col
		else		{
			// temp is in the upper right corner
			if ((temp.row > prev.row && temp.col > prev.col) 
					||(temp.row < prev.row && temp.col < prev.col))		{	// or lower left corner
				return 90;		// right
			}
			
			// if prev, curr, temp are not in the same col, we can go left
			else if (getCurrCell().col != temp.col)	{
				return -90;		// left
			}
			// if prev, curr, temp are in the same col, no need to rotate
			else
				return 0;
		}
			
	}

	/**
		 * Sets start state
		 * @return 
		 */
		public void getDFSPath()	{
			
			newPath = new ArrayList<Cell>();
			stack = new ArrayList<Cell>();
			
	//		path.add(start);		// adds starting cell as the first step
			stack.add(maze[start[0]][start[1]]);	// adds starting cell
			
			dfs();
		}

	/**
	 * Reverses a path
	 * @return
	 */
	public ArrayList<Cell> getReversePath()	{
		ArrayList<Cell> back = new ArrayList<Cell>();
		for (int i = newPath.size()-1; i >= 0; i--)
			back.add(newPath.get(i));
		return back;
	}

	/**
	 * Depth First Search Algorithm
	 */
	public void dfs()	{
		
		// pop the stack;
		Cell temp = stack.remove(0);
		
		// if it is the goal
		if (temp.row == goal[0] && temp.col == goal[1])	{
			return;		// done!
		}
		
		else {
			
			ArrayList<Cell> nbrs = new ArrayList<Cell>();
			
			// adjacent top cell
			if (maze[temp.row+1][temp.col].visited && !maze[temp.row+1][temp.col].isObstacle())
				nbrs.add(maze[temp.row+1][temp.col]);
			
			// adjacent bottom cell
			if (maze[temp.row-1][temp.col].visited && !maze[temp.row-1][temp.col].isObstacle())
				nbrs.add(maze[temp.row-1][temp.col]);
			
			// adjacent left cell
			if (maze[temp.row][temp.col-1].visited && !maze[temp.row][temp.col-1].isObstacle())
				nbrs.add(maze[temp.row][temp.col-1]);
			
			// adjacent right cell
			if (maze[temp.row][temp.col+1].visited && !maze[temp.row][temp.col+1].isObstacle())	
				nbrs.add(maze[temp.row][temp.col+1]);
			
			if (nbrs.size()>0)	{
				Cell next = nbrs.get(0);
				newPath.add(next);
				stack.add(0, next);
			}
	
			dfs();		// recursive call
		}
	}
	
	
	
}
