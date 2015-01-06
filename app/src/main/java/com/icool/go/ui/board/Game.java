package com.icool.go.ui.board;

import com.icool.go.ui.BoardView;



/**
 * 
 * Model of a Go game.  Validates moves, stores history.
 *
 */
public class Game  {
	// Point constants.  Must be chars of positive integers.
	public static final char WHITE = '0';
	public static final char BLACK = '1';
	public static final char EMPTY = '2';
	public static final char OUT_OF_BOUNDS = '3';

	// Rules constants.  Must be positive integers.
	public static final int POSITIONAL = 0;
	public static final int SITUATIONAL = 1;
	public static final int JAPANESE = 2;
	
	// History step direction constants.  Must be int.
	public static final int PREVIOUS = 0;
	public static final int NEXT = 1;
	public static final int FIRST = 2;
	public static final int LAST = 3;

	// Properties constants (to serve as keys for Bundles).  Must be Strings.
	public static final String CAPTURES_KEY = "cap";
	
	// Operations
	public static final int ADD = 1;
	public static final int SUBTRACT = -1;

	// Instance variables
	private int koRule;
	private boolean suicideRule;
	private BoardView board;
	private char nextTurn;
	
	private boolean running;

	// Static methods.
	public static char invertColor(char color){
		return color == WHITE ? BLACK : color == BLACK ? WHITE : OUT_OF_BOUNDS;
	}

	// Constructors.
	
}