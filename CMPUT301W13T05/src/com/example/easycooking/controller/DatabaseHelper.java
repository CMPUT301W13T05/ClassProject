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
				"rid CHAR PRIMARY KEY," +
				"name TEXT," +
				"download_upload_own INTEGER)"); 
		db.execSQL("insert into localrecipe values('ingredientsonhand',null,999)");
		db.execSQL("create table picture(" +
				"pid CHAR PRIMARY KEY," +
				"rid CHAR," +
				"image_uri CHAR(100)," +
				"FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
		db.execSQL("create table step(" +
				"sid INTEGER PRIMARY KEY AUTOINCREMENT," +
				"rid CHAR," +
				"steps TEXT," +
				"FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
		db.execSQL("create table ingredient(" +
				"iid INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," +
				"rid CHAR," +
				"amount CHAR, " +
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

