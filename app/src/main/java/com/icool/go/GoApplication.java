package com.icool.go;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.icool.go.sgf.QipuMgr;
import com.icool.go.ui.QipuItem;

//import android.util.Log;

public class GoApplication extends Application {

	protected static GoApplication instance = null ;
	
	public final static String CLASS_NAME = "GoApplication";
//	public static String DB_PATH = "/data/data/com.icool.go.resources/databases/" ;
    public static String DB_PATH = "/data/data/com.icool.go.icoolgo/databases/" ;
	public static String DB_NAME = "qipu.db";

	Context context ;	
	QipuMgr db = null ;
	
	public GoApplication() {
		// registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
		// @Override
		// public void onActivityCreated(Activity activity,Bundle
		// savedInstanceState) {
		// Log.d(CLASS_NAME, "Activity created: " +
		// activity.getLocalClassName());
		//
		// }
		// @Override
		// public void onActivityStarted(Activity activity) {
		// Log.d(CLASS_NAME, "Activity started: " +
		// activity.getLocalClassName());
		// }
		// @Override
		// public void onActivityResumed(Activity activity) {
		// Log.d(CLASS_NAME, "Activity resumed: " +
		// activity.getLocalClassName());
		// }
		// @Override
		// public void onActivitySaveInstanceState(Activity activity,Bundle
		// outState) {
		// Log.d(CLASS_NAME, "Activity saved instance state: " +
		// activity.getLocalClassName());
		// }
		// @Override
		// public void onActivityPaused(Activity activity) {
		// Log.d(CLASS_NAME, "Activity paused: " +
		// activity.getLocalClassName());
		// }
		// @Override
		// public void onActivityStopped(Activity activity) {
		// Log.d(CLASS_NAME, "Activity stopped: " +
		// activity.getLocalClassName());
		// }
		// @Override
		// public void onActivityDestroyed(Activity activity) {
		// Log.d(CLASS_NAME, "Activity destroyed: " +
		// activity.getLocalClassName());
		// }
		// });
	}
	
	public GoApplication getInstance(){
		return instance ;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();
		try {			
			init() ;
			String path = context.getFilesDir().getPath() ;
			Log.i(CLASS_NAME, "path = " + path) ;
			db = new QipuMgr(context , DB_NAME) ;
			
		} catch (Exception e) {
			Log.e(CLASS_NAME , "onCreate failed" , e) ;
		}
		
		instance = this ;
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private void copyDataBase() throws IOException {
		
		InputStream myInput = context.getAssets().open(DB_NAME);
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		// Open the empty db as the output stream
        File f = new File(DB_PATH) ;
        if (!f.exists()) {
            f.mkdirs() ;
        }else{
            Log.i(CLASS_NAME , "path exist!") ;
        }
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
        Log.i(CLASS_NAME , "Copied the database db file successfully!") ;
	}
	
	public void init() {
		try {
			copyDataBase() ;
		} catch (Exception e) {
			Log.e(CLASS_NAME , "init failed" , e) ;
		}
		
	}
	
	public List<QipuItem> loadAllQipu() {
		try {			
			
//			SQLiteDatabase sqlitedb = db.getWritableDatabase(); 
////			db.onCreate(sqlitedb);
//			String path = sqlitedb.getPath() ;
//			Log.i(CLASS_NAME , "Path=" + path);

			return db.listAllQipu() ;
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null ;
	}
	
	public String loadQipuData(int id) {
		return db.loadQipu(id) ;
	}
}
