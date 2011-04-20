package app;

/**
 * George Dittmar (c) Spring 2011
 * @author george
 *
 */
public class Square {
	int row;
	 int col;
	
	public Square(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public String toString(){
		return "("+col+","+row+")";
	}
}
