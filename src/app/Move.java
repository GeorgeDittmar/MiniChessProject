package app;
/**
 * George Dittmar (c) Spring 2011
 * @author george
 *
 */
public class Move {
	
	 Square to;
	 Square from;

	public Move(Square to, Square from){
		this.to = to;
		this.from = from;
	}
	
	public String toString(){
		return "("+from.toString()+","+to.toString()+")";
	}
	
}
