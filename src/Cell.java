/**
 * Cell holds the instances for cell value, 
 * and coordinates for its location in the maze.
 * It will be used to populate the maze in World.
 */
public class Cell {
	
	public int cellVal;
	public int row;
	public int col;
	public boolean visited;
	
	
	/**
	 * A constructor that takes in x, y coordinates
	 * @param row
	 * @param col
	 */
	public Cell(int row, int col)	{
		this.row = row;
		this.col = col;
		cellVal = 0;	// unvisited or unreachable
		visited = false;
	}
	
	
	/**
	 * Assigns a value to the cell
	 * @param v
	 */
	public void setValue(int cellVal)	{
		this.cellVal = cellVal;
	}
	
	
	/**
	 * Sets the cell to be visited
	 */
	public void setVisited()	{
		visited = true;
	}
	
	
	/**
	 * Cuts off one path
	 */
	public void removeAPath()	{
		if (cellVal >= 1 )
			this.cellVal -=1;
		else
			return;
	}
	
	
	/**
	 * Will return true if the cell is an obstacle
	 * @return
	 */
	public boolean isObstacle()	{
		if (cellVal == -1)
			return true;
		return false;
	}
	
	/**
	 * Sets cell value to be -1
	 * @return
	 */
	public void setObstacle()	{
		cellVal = -1;
	}
	
	
	/**
	 * Will return true if the cell is visited 
	 * and all possible routes emerging from it have been tried
	 * @return
	 */
	public boolean isDeadEnd()	{
		if (cellVal == 1)
			return true;
		return false;
	}
	
	
	/**
	 * Sets the cell value to be one
	 */
	public void setDeadEnd()	{
		cellVal = 1;
	}
	
	
	/**
	 * Prints out the position in a nice way for a certain cell
	 * @return
	 */
	public String printPos()	{
		String pos = "( " + this.row + ", " + this.col + " )";
		return pos;
	}

	
	
	
	
}
