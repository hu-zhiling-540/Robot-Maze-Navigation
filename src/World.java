
public class World {
	
	public Cell[][] world;	//world grid
	public Cell start;		// start location
	public Cell goal;		// goal location
	
//	private static final cell_width = 

	public World(int numRows, int numCols, Cell start, Cell goal)	{
		this.start = start;
		this.goal = goal;
		this.world = new Cell[numRows][numCols];
		populateWorld();
	}
	
	/**
	 * Fills the world grid with cells
	 */
	public void populateWorld() {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[i].length; j++)	{
				world[i][j] = new Cell(i,j);
				// g(n) at first
				int dis = manhattanDist(world[i][j]);
				world[i][j].setManhattanDist(dis);
			}
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
	
	
	/**
	 * Calculates the Manhattan Distance bewteen goal and a certain cell
	 * @param cell
	 * @return
	 */
	public int manhattanDist(Cell cell)	{
		int row = cell.pos[0];
		int col = cell.pos[1];
		int absD = Math.abs(row - goal.pos[0]) + Math.abs(col - goal.pos[1]);
		return absD;
	}
	
	
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
