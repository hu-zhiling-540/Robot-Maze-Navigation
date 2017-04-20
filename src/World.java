
public class World {
	
	public Cell[][] world;	//world grid
	public Cell start;		// start location
	public Cell goal;		// goal location
	

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
			for(int j = 0; j < world[i].length; j++)
				world[i][j] = new Cell(i,j);
		}
	}
}
