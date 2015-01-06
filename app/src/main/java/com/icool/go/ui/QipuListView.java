package com.icool.go.ui;

import java.io.InputStream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.icool.go.GoApplication;
import com.icool.go.resources.R;
import com.icool.go.ui.board.SgfData;
//import android.util.AttributeSet;

public class QipuListView extends ListView {
	
	public MainActivity mainPnl ;
	public GoApplication app ;
	
//	ArrayList<String> listItems = new ArrayList<String>();
	QipuAdapter qipuAdapter = null;
	
	Context ctx = null ;
	
	public QipuListView(Context context , AttributeSet attrs) {
		super(context , attrs);
		this.ctx = context ;		
		
		qipuAdapter = new QipuAdapter(context , android.R.layout.simple_list_item_1);
		setAdapter(qipuAdapter);	
		
	}
	
	public void init() {
		app = (GoApplication)mainPnl.getApplication() ;		
		qipuAdapter.init(app.loadAllQipu()) ;
		
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayQipu(position) ;
			}
		});
	}

	public void displayQipu(int pos) {
//		listItems.clear() ;
		try {
			QipuItem item = (QipuItem)getItemAtPosition(pos) ;
			
			mainPnl.loadBoardLayout() ;
			String content = app.loadQipuData(item.qipuId) ;
			mainPnl.boardView.loadQipuData(content) ;
			TextView txtView = (TextView)mainPnl.findViewById(R.id.qipuTitle) ;
			txtView.setText(item.title) ;
			
//			if (item.isSgf()) {
//				
//				InputStream ins = 
//						getResources().getAssets().open(item.path);
//				
//				mainPnl.loadBoardLayout() ;
//				SgfData sgf = mainPnl.boardView.loadSGF(ins) ;
//				String sfgDesc = sgf.header.get("PB") + sgf.header.get("PW") + sgf.header.get("RE") ;
//				TextView txtView = (TextView)mainPnl.findViewById(R.id.qipuTitle) ;
//				txtView.setText(sfgDesc) ;
//				
//			} else {
//				qipuAdapter.loadSubitems(item) ;
//				qipuAdapter.notifyDataSetChanged();
//			}
			
			
			
//			
//			// InputStreamReader inputReader = new InputStreamReader(
//			// getResources().openRawResource(R.raw.));
//			InputStreamReader inputReader = new InputStreamReader(
//					getResources().getAssets().open("001.sgf"));
//			BufferedReader bufReader = new BufferedReader(inputReader);
//			String line = "";
//			String Result = "";
//			while ((line = bufReader.readLine()) != null)
//				Result += line;
//			
//			System.out.println(Result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public void init(Context context) {
//
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context);
//		adapter.add("Andrea");
//		// adapter = new CustomAdapter(getActivity(), R.layout.row,
//		// myStringArray1);
//		setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//	}

}
