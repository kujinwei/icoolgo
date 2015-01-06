package com.icool.go.ui;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.icool.go.resources.R;
import com.icool.go.ui.board.Player;


/**
 * 
 * @author kujunguo
 *
 */
public class MainActivity extends Activity {

	
	LinearLayout rootLayout = null ;
	View indexLayout = null ;
	View boardLayout = null ;
	View qipuLayout = null ;

	public QipuListView qipuListView = null;
	public BoardView boardView = null ;
	public Player player = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		LayoutInflater inflater = getLayoutInflater();
		LayoutInflater inflater = LayoutInflater.from(this);
		indexLayout = inflater.inflate(R.layout.index, null);
		boardLayout = inflater.inflate(R.layout.board, null);	
		qipuLayout = inflater.inflate(R.layout.qipu, null);	
		setContentView(indexLayout);		
		
		Button btn3 = (Button)findViewById(R.id.myButton3);
		btn3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	loadQipuListLayout() ;
            }
        });
//		init();
	}

	@Override
	protected void onStop() {
		if (player != null) {
			player.stop();
		}
		
		super.onStop(); 
	}
    
    public int getFontHeight(float fontSize) {  
        Paint paint = new Paint();  
        paint.setTextSize(fontSize);  
        FontMetrics fm = paint.getFontMetrics();  
        return (int) Math.ceil(fm.descent - fm.top) + 2;  
    }      
	
	
	public int getScreenWidth() {
		WindowManager manage= getWindowManager();
		
//		getWindowManager().getDefaultDisplay().getSize(size) ;
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); 
//		int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); 
		
		
		return screenWidth ;
	}
	
	
	public void loadQipuListLayout() {		
		setContentView(qipuLayout); 
		qipuListView = (QipuListView) findViewById(R.id.qipuListView);
		qipuListView.mainPnl = this ;	
		qipuListView.init() ;
	}
	/**
	 * 
	 */
	public void loadBoardLayout() {
		
		setContentView(boardLayout); 
		boardView = (BoardView)findViewById(R.id.boardView) ;
		boardView.mainPnl = this ;
		boardView.init() ;
	    player = new Player(boardView) ;
//		tileView.setBackgroundColor(Color.rgb(255, 128, 64));
//		tileView.setBackgroundResource(R.id.shinkaya) ;
		
		ImageView go = (ImageView)findViewById(R.id.goImg) ;
		ImageView nextfast = (ImageView)findViewById(R.id.nextfastImg) ;
		ImageView stop = (ImageView)findViewById(R.id.stopImg) ;
		
		
		go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				boardView.goNext() ;
				boardView.invalidate() ;
			}
		});
		
		nextfast.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				player.start();
			}
		});
		
		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				player.stop();
			}
		});
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
