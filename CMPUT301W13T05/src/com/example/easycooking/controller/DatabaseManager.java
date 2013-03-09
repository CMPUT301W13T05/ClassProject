package com.example.easycooking.controller;


import com.example.easycooking.model.Recipe;

import android.view.Menu;
import android.content.ContentValues;  
import android.content.Context;
import android.database.Cursor;  
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;  
import android.view.View;  

/**
 * This class manages the database that contains the local recipes.
 * The local recipes contains download, upload recipes.
 * All interaction with local recipes much go through the DatabaseManager
 * @author HongZu
 *
 */
public class DatabaseManager {
	private static DatabaseManager instance = null;

		private SQLiteDatabase db ;
		private DatabaseHelper dbHelper;
		
		protected DatabaseManager(Context context){
			dbHelper = new DatabaseHelper(context);
		}  

		public void open() throws SQLException {
			db = dbHelper.getWritableDatabase();
		}
		public void close(){
			dbHelper.close();
		} 
		/**
		 * return instance of the database to user
		 * @param context
		 * @return
		 */
		public static DatabaseManager getInstance(Context context){
			if(instance == null){
				instance = new DatabaseManager(context);
			}
			return instance; 
		}
		
//		//Add recipe
//		public void add_recipe(Recipe recipe) {
//			ContentValues values = new ContentValues();
//			values.put("rid", rid);
//			values.put("name",name);
//			values.put("download", download);
//			values.put("upload", upload);
//			db.insert("localrecipe", null, values);
//
//		}

}
