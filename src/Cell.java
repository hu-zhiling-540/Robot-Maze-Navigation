
public class Cell {
	
//	0 => node is unvisited or unreachable;
//	1 => node visited and all possible routes emerging from it have been tried
	
//	2 => node visited, but there remains an untested emerging route. 
//	This would happen if the robot reaches a T-junction. 
//	One path would need to be chosen, while one path would remain to be explored,
//	so mark the position with ‘2’ before moving to the next cell.
	
//	3 => corresponds to a position in which two emerging routes have been left unexplored. 
//	This would occur when you come to a 4-way junction. 
//	Pick the first path and leave the other two unexplored, so mark the cell with a 3.
	
	public int cellVal;
	public int[] pos = new int[2];	// [row][col]
	public int ManhattanD;
	
	/**
	 * Default Constructor
	 */
	public Cell()	{
		pos[0] = -1;
		pos[1] = -1;
		cellVal = 0;	// unvisited or unreachable
	}
	
	/**
	 * A constructor that takes in x, y coordinates
	 * @param row
	 * @param col
	 */
	public Cell(int row, int col)	{
		pos[0] = row;
		pos[1] = col;
		cellVal = 0;	// unvisited or unreachable
	}
	
	
	/**
	 * A constructor that takes in x, y coordinates, and cell value
	 * @param row
	 * @param col
	 * @param cellVal
	 */
	public Cell(int row, int col, int cellVal)	{
		pos[0] = row;
		pos[1] = col;
		this.cellVal = cellVal;
	}
	
	
	/**
	 * Assigns a value to the cell
	 * @param v
	 */
	public void setValue(int cellVal)	{
		this.cellVal = cellVal;
	}
	
	/**
	 * Assigns manhattanDistance for each cell
	 * @param distance
	 */
	public void setManhattanDist(int distance)	{
		this.ManhattanD = distance;
	}
	
	/**
	 * Prints out the position in a nice way for a certain cell
	 * @return
	 */
	public String printPos()	{
		String pos = "( " + this.pos[0] + ", " + this.pos[1] + " )";
		return pos;
	}
	
	
	
}
