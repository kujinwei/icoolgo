package com.icool.go.sgf;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.icool.go.ui.QipuItem;

/**
 * The databases are stored by default in a subfolder of your app folder in the internal storage, i.e.:
  /data/data/YOUR_APP/databases/YOUR_DATABASAE.db
 * @author kujunguo
 *
 */
public class QipuMgr extends SQLiteOpenHelper {

	public final static String CLASS_NAME = "QipuMgr";
	private final static String DATABASE_NAME = "sgf_db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "QIPU";
	public final static String FIELD_ID = "_id";
	public final static String FIELD_TITLE = "sec_Title";

	
	public QipuMgr(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public QipuMgr(Context context , String dbName) {
		super(context, dbName, null, DATABASE_VERSION);
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		String sql = "Create table " + TABLE_NAME + "(" + FIELD_ID
//				+ " integer primary key autoincrement," + FIELD_TITLE
//				+ " text );";
//		System.out.println("DB is created!");
//		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String loadQipu(int id) {
		String content = null ;
		SQLiteDatabase db = this.getReadableDatabase();
		String[] from = {"CONTENT"} ;
		String where = "rowid=" + id ;
		Cursor cursor = db.query(TABLE_NAME, from, where, null, null, null, null);
		if (cursor.moveToNext() ) {
			content = cursor.getString(0) ;
		}
		
		return content ;
	}

	public List<QipuItem> listAllQipu() {
		List<QipuItem> qipuList = new ArrayList<QipuItem>() ;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[]{"rowid, B_NAME , W_NAME , RESULT"}, null, null, null, null,
				"PLAY_DATE  desc");
		while (cursor.moveToNext()) {
//			Log.i(CLASS_NAME , cursor.getInt(0) + ":" + cursor.getString(1)) ; 
			
			QipuItem item = new QipuItem(cursor.getString(1) + cursor.getString(2) + cursor.getString(3)) ;
			
			item.qipuId = cursor.getInt(0) ;
			qipuList.add(item) ;
			
		}
		return qipuList;
	}

	public long insert(String Title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TITLE, Title);
		System.out.println("insert to the DB");
		long row = db.insert(TABLE_NAME, null, cv);
		System.out.println("row = " + row);
		return row;
	}

	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}

	public void update(int id, String Title) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TITLE, Title);
		db.update(TABLE_NAME, cv, where, whereValue);
	}

}