package com.example.easycooking.controller;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper is a helper class to manage database creation and version management
 * @author HongZu
 */
public class DatabaseHelper extends SQLiteOpenHelper { 
	private static final int VERSION = 1;
	private static final String DATABASE = "ecipes.db";
	/**
	 * 
	 * @param context
	 * @param name
	 * @param cursorFactory
	 * @param version
	 */
	public DatabaseHelper(Context context) 
	  { 
	     super(context, DATABASE, null, VERSION);     
	     } 
	
	//This function is used for first creation of the database
	@Override
	public void onCreate(SQLiteDatabase db) {     
	    // TODO 
		System.out.println("create a database");
		//execSQL to execute sql statement
		db.execSQL("create table localrecipe(" +
				"rid STRING PRIMARY KEY" +
				"name TEXT," +
				"downloaded_upload_own INTEGER)"); 
		db.execSQL("create table picture(" +
				"pid STRING PRIMARY KEY," +
				"rid STRING," +
				"image blob," +
				"FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
		db.execSQL("create table step(" +
				"sid STRING PRIMARY KEY AUTOINCREMENT," +
				"recipe_step STRING," +
				"rid STRING," +
				"steps TEXT," +
				"FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
		db.execSQL("create table ingredient(" +
				"iid INTEGER PRIMAY KEY AUTOINCREMENT" +
				"name TEXT," +
				"rid STRING," +
				"amount STRING, " +
				"FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
	}     
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {     
	    // TODO
		System.out.println("upgrade the database");
		db.execSQL("DROP TABLE IF EXISTS localrecipe");
		db.execSQL("DROP TABLE IF EXISTS picture");
		db.execSQL("DROP TABLE IF EXISTS step");
		db.execSQL("DROP TABLE IF EXISTS ingredient");
		onCreate(db);
	}          
}

