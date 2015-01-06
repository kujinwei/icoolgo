package com.icool.go.ui.board;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * Model of an intersection on a Go board.  Stores the x and y
 * coordinates and the color of the stone (can also be empty or
 * out-of-bounds).
 *
 */
public class Stone {
	public Coordinate c ;
	
//	public Block owner = null ;
	
	public char color;
	
	public Block newBlock ;
	public List<Block> deletedBlocks = new ArrayList<Block>() ;
	public List<Block> killedBlocks = new ArrayList<Block>() ;

	/**
	 * Construct a <code>Point</code> with the given coordinates and
	 * color.
	 * @param x	x coordinate
	 * @param y	y coordinate
	 * @param color	Color of the stone located on this point (can also be
	 * empty or out-of-bounds)
	 */
	public Stone(Coordinate c, char color){
		this.c = c;
		this.color = color;
		
		c.initNear() ;
	}

	

	/**
	 * Returns the color of the stone located on this point.
	 * @return color
	 */
	public char getColor(){
		return this.color;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + color;
//		result = prime * result + x;
//		result = prime * result + y;
//		return result;
//	}

	

}