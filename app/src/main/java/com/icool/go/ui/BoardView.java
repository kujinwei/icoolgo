package com.icool.go.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.icool.go.resources.R;
import com.icool.go.ui.board.Block;
import com.icool.go.ui.board.Coordinate;
import com.icool.go.ui.board.Game;
import com.icool.go.ui.board.SgfData;
import com.icool.go.ui.board.SgfReader;
import com.icool.go.ui.board.Stone;

public class BoardView extends View {

	private static final int ACode = (int) 'a';
	public final static int DEFAULT_SIZE = 19;
	
	public MainActivity mainPnl;
	Context context;

	public int nSize;

	public Canvas canvas;
	public Paint paint;

	int xOffset, yOffset;
	int boardWidth, cellWidth;

	
	Bitmap bgImg, blackImg, whiteImg;

	char currTurn = Game.BLACK;
	Bitmap currImg;

	List<Block> blocks = new ArrayList<Block>(); // current blocks on board
	List<Stone> stones = new ArrayList<Stone>();

	List<Coordinate> sgfCoordinates;
	int currStep = 0;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.nSize = DEFAULT_SIZE ;
		this.paint = new Paint();
		this.context = context;

		blackImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.black);
		whiteImg = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.white);

	}

	

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas ;
		
		draw();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int wh = Math.min(w, h);
		super.onSizeChanged(w, wh, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {

			float xf = event.getX();
			float yf = event.getY();

			onBoardClick(xf, yf);
			this.invalidate();
		}
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int m = Math.max(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(m, m);
	}

	private void drawBoardCircle(Canvas canvas, float x, float y, float size,
			Paint paint) {
		// canvas.drawCircle(stone_size / 2.0f + x * stone_size, stone_size /
		// 2.0f + y * stone_size, size, paint);
	}

	private Bitmap getScaledRes(float size, int resID) {
		Bitmap unscaled_bitmap = BitmapFactory.decodeResource(
				context.getResources(), resID);
		return Bitmap.createScaledBitmap(unscaled_bitmap, (int) size,
				(int) size, true);
	}

	public void toogle() {
		if (currTurn == Game.BLACK) {
			currImg = blackImg;
			currTurn = Game.WHITE;
		} else {
			currImg = whiteImg;
			currTurn = Game.BLACK;
		}
	}

	/**
	 * 
	 */
	public void init() {
		boardWidth = mainPnl.getScreenWidth() - 48;
		System.out.println("boardWidth = " + boardWidth);
		cellWidth = (int) (boardWidth / (nSize - 1));
		xOffset = 24;
		yOffset = 24;

	}

	public void onBoardClick(float xf, float yf) {

		int x = x2Coordinate(xf);
		int y = y2Coordinate(yf);

		System.out.println("x= " + x + ", y=" + y);
		Coordinate c = new Coordinate(x, y);
		char color = currTurn;

		toogle();
		Stone s = new Stone(c, color);
		putStone(s);
		// printBlocks() ;
		// update() ;
	}

	/**
	 * 
	 * @param g
	 * @param stone
	 */
	public void drawStone(Stone stone) {

		Coordinate c = stone.c;
		// System.out.println("color=" + stone.color);
		if (stone.color == Game.BLACK) {
			currImg = blackImg;
			paint.setColor(Color.BLACK);
		} else if (stone.color == Game.WHITE) {
			currImg = whiteImg;
			paint.setColor(Color.WHITE);
		}

		int x = c.x * cellWidth - cellWidth / 2 + xOffset;
		int y = c.y * cellWidth - cellWidth / 2 + yOffset;

		canvas.drawBitmap(currImg, (float) x, (float) y, paint);

	}

	public void reset() {

		currStep = 0;
		currTurn = Game.BLACK;

		blocks.clear();
		stones.clear();
		if (sgfCoordinates != null) {
			sgfCoordinates.clear();
			sgfCoordinates = null;
		}

		update();
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public SgfData loadSGF(String file) {
		SgfData sgf = null;
		try {
			FileInputStream fins = new FileInputStream(file);
			// if (fins == null) {
			// System.out.println("ins is NULL");
			// }
			sgf = SgfReader.loadSGF(fins);
			sgfCoordinates = sgf.coordinates;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sgf;
	}

	/**
	 * 
	 * @param ins
	 * @return
	 */
	public SgfData loadSGF(InputStream ins) {
		SgfData sgf = null;
		try {
			sgf = SgfReader.loadSGF(ins);
			sgfCoordinates = sgf.coordinates;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sgf;
	}
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	public SgfData loadQipuData(String content) {
		SgfData data = new SgfData();		

		String[] ds = content.split(";");
//		System.out.println("ds=" + ds.length);
		if (ds.length < 4) {
			return null;
		}
		for (String s : ds) {
			if (s == "")
				continue;

			String ns = s.toLowerCase();
			// System.out.println("ns=" + ns);
			if (ns.length() == 5
					&& (ns.charAt(0) == 'b' || ns.charAt(0) == 'w')) {
				char xChar = ns.charAt(2);
				char yChar = ns.charAt(3);

				int x = (int) xChar - ACode;
				int y = (int) yChar - ACode;
				// System.out.println("x=" + x + ",y=" + y);
				Coordinate c = new Coordinate(x, y);
				data.coordinates.add(c);
			}
		}
		
		sgfCoordinates = data.coordinates ;
		// System.out.println("cs=" + cs.size());
		return data;
	}
	
	

	/**
	 * 
	 * @param c
	 * @return
	 */
	public Block getBlockByCoodinate(Coordinate c) {
		Block b = null;

		for (Block block : blocks) {
			List<Coordinate> strings = block.strings;
			for (Coordinate coordinate : strings) {
				if (coordinate.equals(c)) {
					return block;
				}
			}
		}

		return b;
	}

	/**
	 * 
	 */
	public void goNext() {
		if (sgfCoordinates == null) {
			return;
		} else {

			Coordinate c = sgfCoordinates.get(currStep);
			if (c == null) {
				return;
			}
			Stone stone = new Stone(c, currTurn);
			System.out.println("Now c.x=" + c.x + ",c.y=" + c.y);
			putStone(stone);

			currStep++;

			toogle();
		}

	}

	public void goBefore() {

		Stone currStone = stones.get(stones.size() - 1);
		// System.out.println("currStone.newBlock.strings.size = " +
		// currStone.newBlock.strings.size());
		blocks.remove(currStone.newBlock);

		for (Block block : currStone.killedBlocks) {
//			System.out.println(block.strings.size());
			blocks.add(block);
		}

		for (Block block : currStone.deletedBlocks) {
			System.out.println(block.strings.size());
			blocks.add(block);
		}

		stones.remove(currStone);
		update();
		currStep--;

		toogle();

	}

	public void update() {
		draw();
	}

	/**
	 * If no air or it's in robbing, not allow to put stone on Coordinate
	 * 
	 * @param c
	 * @return
	 */
	public boolean checkIfAllowed(Coordinate c) {
		if (getBlockByCoodinate(c) == null) {
			return true;
		}

		return false;
	}

	/**
	 * put a stone on the board
	 * 
	 * @param stone
	 */
	public void putStone(Stone stone) {

		if (!checkIfAllowed(stone.c)) {
			return;
		}

		boolean bKilled = false;
		// detect all blocks near this stone
		List<Block> nearBlocks = new ArrayList<Block>();
		int myair = 4;
		for (Coordinate c : stone.c.near) {
			Block b = getBlockByCoodinate(c);
			if (b != null) {
				nearBlocks.add(b);
				myair--;
			}

		}

		for (Block block : nearBlocks) {
			if (block.bw == stone.color) {
				stone.deletedBlocks.add(block);

			} else {
				if (block.airCount == 1) {// kill this block
					stone.killedBlocks.add(block);
					blocks.remove(block);
					bKilled = true;
				} else {
					block.airCount--;
				}
			}
		}

		// each stone, a new block is created on the board
		Block newBlock = new Block(stone.color);
		for (Block block : stone.deletedBlocks) {
			newBlock.addBlock(block);
			// newBlock.airCount += block.airCount - 1 ;
			blocks.remove(block);
		}
		//
		newBlock.add(stone.c);
		blocks.add(newBlock);

		calcBlockAir(newBlock);
		stone.newBlock = newBlock;
		// calculate the air for all blocks
		if (bKilled) {
			for (Block block : blocks) {
				calcBlockAir(block);

			}
		}

		stones.add(stone);

		// Util.printHeapUsage() ;

	}

	public void calcBlockAir(Block b) {
		int air = 0;

		HashMap map = new HashMap();

		for (Coordinate c : b.strings) {
			Coordinate[] near = c.near;
			for (Coordinate coordinate : near) {
				if (coordinate == null) {
					continue;
				}
				if (getValue(coordinate.x, coordinate.y) == Game.EMPTY) {
					if (!map.containsKey(coordinate)) {
						map.put(coordinate, coordinate);
					}
				}
			}

		}
		air = map.keySet().size();
		b.airCount = air;
	}

	public void printBlocks() {
		System.out.println("======all blocks=======");
		for (Block block : blocks) {
			System.out.println(block.airCount);
			// List<Coordinate> strings = block.strings ;

		}
	}

	/**
	 * Return the number of vertical or horizontal lines in the board.
	 * 
	 * @return the number of vertical or horizontal lines in the board.
	 */
	public int getBoardSize() {
		return this.nSize;
	}

	public char getValue(int x, int y) {
		for (Block block : blocks) {
			List<Coordinate> strings = block.strings;
			for (Coordinate coordinate : strings) {
				if (coordinate.x == x && coordinate.y == y) {
					return block.bw;
				}
			}
		}

		return Game.EMPTY;
	}

	public void draw() {
		// super.paint(g) ;

		// setBackground(Color.WHITE) ;

		// canvas.drawImage(bgImg , 0 , 0, width, height , null) ;
		drawLineGrid();
		drawStar();

		drawBlocks();
		// drawFlag(g);
	}

	private void drawBlocks() {
		// System.out.println("draw blocks");
		Stone stone;
		for (Block block : blocks) {
			List<Coordinate> strings = block.strings;
			for (Coordinate coordinate : strings) {
				stone = new Stone(coordinate, block.bw);
				drawStone(stone);
			}
		}
	}

	private void drawFlag() {
		// g.setColor(Color.RED);
		// Coordinate c=board.getLastPosition();
		// if(c!=null){
		// g.drawRect(x2Screen(c.x)-3, y2Screen(c.y)-3
		// , x2Screen(c.x)+3f, y2Screen(c.y)+3f, paint);
		// }
	}

	public Coordinate[] createStar() {
		Coordinate[] cs = new Coordinate[9];

		int dao3 = nSize - 4;
		cs[0] = new Coordinate(3, 3);
		cs[1] = new Coordinate(dao3, 3);
		cs[2] = new Coordinate(3, dao3);
		cs[3] = new Coordinate(dao3, dao3);

		int zhong = nSize / 2;

		cs[4] = new Coordinate(3, zhong);
		cs[5] = new Coordinate(zhong, 3);
		cs[6] = new Coordinate(zhong, dao3);
		cs[7] = new Coordinate(dao3, zhong);

		cs[8] = new Coordinate(zhong, zhong);

		return cs;
	}

	private void drawStar() {

		paint.setColor(Color.BLACK);
		for (Coordinate c : createStar()) {
			if (c != null) {
				canvas.drawCircle(x2Screen(c.x), y2Screen(c.y), 4f, paint);
			}
		}
	}

	private void drawLineGrid() {
		paint.setColor(Color.BLACK);
		for (int i = 0; i < nSize; i++) {
			drawVLine(i);
			drawHLine(i);
		}
	}

	private void drawVLine(int i) {
		canvas.drawLine(x2Screen(i), y2Screen(0), x2Screen(i),
				y2Screen(nSize - 1), paint);
		//
		// char ch = (char)(ACode + i) ;
		// g.drawString(String.valueOf(ch), x2Screen(i), y2Screen(nSize - 1) +
		// 10) ;
	}

	private void drawHLine(int i) {
		canvas.drawLine(x2Screen(0), y2Screen(i), x2Screen(nSize - 1),
				y2Screen(i), paint);
		// char ch = (char)(ACode + i) ;
		// g.drawString(String.valueOf(ch), x2Screen(nSize - 1) + 10,
		// y2Screen(i)) ;
	}

	private int x2Screen(int x) {
		// System.out.println("boardWidth = " + boardWidth);
		return (int) (x * cellWidth + xOffset);
	}

	private int y2Screen(int y) {
		return (int) (y * cellWidth + yOffset);
	}

	private int x2Coordinate(float x) {
		return (int) Math.round((x - xOffset) / cellWidth);
	}

	private int y2Coordinate(float y) {
		return (int) Math.round((y - yOffset) / cellWidth);
	}


	
}
