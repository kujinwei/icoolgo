package com.icool.go.ui;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QipuAdapter extends ArrayAdapter<QipuItem>{

	Context ctx ;
	
	QipuItem rootNode ;
	QipuItem currNode ; 
	
	
	public QipuAdapter(Context context, int resource) {
		super(context, resource);
		this.ctx = context ;
		
	}
	
	public void init(List<QipuItem> qipuList) {
//		QipuItem item ;
//		rootNode = new QipuItem("root") ;
//		
//		item = new QipuItem("天才出世") ;
//		item.path = "section1" ;
//		rootNode.addChild(item) ;
//		
//		item = new QipuItem("旭日东升") ;
//		item.path = "section2" ;
//		rootNode.addChild(item) ;
//		
//		item = new QipuItem("番棋之王") ;
//		item.path = "section3" ;
//		rootNode.addChild(item) ;
//		
//		item = new QipuItem("昭和棋圣") ;
//		item.path = "section4" ;
//		rootNode.addChild(item) ;
		
		addAll(qipuList) ;
		
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * @param parentItem
	 */
	public void loadSubitems(QipuItem parentItem) {
		clear() ;
		try {
			
//			if (parentItem.isSgf()) {
//				return ;
//			}
//			ctx.getResources().getAssets().open(parentItem.path) ;
//			String[] files = ctx.getAssets().list(parentItem.path); 
//			QipuItem subItem ;
//			for (String file : files) {
//				System.out.println("file=" + file);
//				subItem = new QipuItem(file) ;
//				parentItem.addChild(subItem) ;
//				subItem.path = subItem.parent.path + "/" + file ;
				
//			}
//			addAll(parentItem.subItems) ;
			notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
		
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {  
		QipuItem item = getItem(position) ;
        TextView textView = new TextView(ctx); 
        textView.setTextSize(18) ;
        textView.setText(item.title) ;
        return textView ;
	}
	
	
	public void addAll(List<QipuItem> items) {
	    //If the platform supports it, use addAll, otherwise add in loop
//	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//	        super.addAll(items)
//	    }else{
//	        
//	    }
		for(QipuItem item: items){
            super.add(item);
        }
	}
	
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
