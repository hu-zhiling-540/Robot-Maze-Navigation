
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
	
	/**
	 * Default Constructor
	 */
	public Cell()	{
		cellVal = 0;	// unvisited or unreachable
	}
	
	public Cell(int cellVal)	{
		this.cellVal = cellVal;
	}
}
