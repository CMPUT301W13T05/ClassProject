package com.example.easycooking.controller;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper inheritances from SQLiteOpenHelper. 
 * SQLiteOpenHelper is a helper class to manage database creation and version management
 * Databasehelper create Four tables:{localrecipe, picture, step, ingredient}
 * localrecipe table contains recipes' id, name, the information of the recipe is shared, download, upload, or self created
 * picture table contains images' id, belong to which recipe id, and the base64 image
 * step table contains steps' id, belong to which recipe id, and the steps string
 * ingredient contains ingredient's id, belong to which recipe id, the ingredient name, and the amount of this ingredient 
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
		db.execSQL("create table cachedrecipe(rid CHAR PROMARY KEY,path CHAR, FOREIGN KEY(rid) REFERENCES localrecipe(rid))");
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

