import java.util.ArrayList;

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
		
		world = new Cell[numRows][numCols];
		populateWorld();
		
		this.start = world[start[0]][start[1]];
		this.goal = world[goal[0]][goal[1]];
		
		stack = new ArrayList<Cell>();
		path = new ArrayList<Cell>();
	}
	
	
	/**
	 * Fills the world grid with cells
	 */
	public void populateWorld() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++)	{
				world[i][j] = new Cell(i,j);
			}
		}
		
		// corner cells
	   	world[0][0].setValue(2);					// (0,0)
	   	world[0][numCols-1].setValue(2);			// (0,7)
	   	world[numRows-1][0].setValue(2);			// (4,0)
	   	world[numRows-1][numCols-1].setValue(2);	// (4,7)
	   	
		// inner cells
//	   	For i = 1 to 3
//	   			For j = 1 to 6
	   	for(int i = 1; i < numRows - 1; i++)	{
    		for(int j = 1; j < numCols - 1; j++)	{
    			world[i][j].setValue(4);
    		}
    	}
	   	
	   	// other border cells
//	   	i = 0/4, loop j = 1 to 6
//	   			j = 0/7, loop i = 1 to 3
	   	for (int j = 1; j < numCols - 1; j++)	{
	   		world[0][j].setValue(3);
	   		world[numRows-1][j].setValue(3);
	   	}
	   	for (int i = 1; i < numRows -1; i++)	{
	   		world[i][0].setValue(3);
	   		world[i][numCols-1].setValue(3);
	   	}
	   	
	   	
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
	public void printWorld(Cell[][] arr)	{
		String s = "";
		for (int i = 0; i < arr[0].length; i ++)	{
			for (int j = 0; j < arr.length; j ++)	{
				s += arr[j][i].cellVal + "\t";
			}
			s += "\n";
		}
		System.out.println(s);
	}
	
	
	
	
}
