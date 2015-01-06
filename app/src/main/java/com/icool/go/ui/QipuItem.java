package com.icool.go.ui;

public class QipuItem {

	public int qipuId; 
	public String title ;
	public String path ;
//	public List<QipuItem> subItems ;
//	public QipuItem parent ;
	
//	public boolean isSgf = false ;
	
	public QipuItem(String title) {
		this.title = title ;
		
	}
	
	
	public boolean isSgf() {
		if (path.endsWith(".sgf")) {
			return true ;
		} else {
			return false ;
		}
	}
	
}
