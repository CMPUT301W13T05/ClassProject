package com.example.easycooking.controller;


import java.util.ArrayList;

import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

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
		
		//insert an recipe
		public void add_recipe(Recipe recipe ) {
			ContentValues values = new ContentValues();
			values.put("rid", recipe.getID());
			values.put("name",recipe.getName());
			values.put("download_upload_own", recipe.get_download_upload_own());
			db.insert("localrecipe", null, values);

		}
		public void add_step(Step step){
			ContentValues values = new ContentValues();
			values.put("recipe_step", step.get_recipe_step());
			values.put("rid", step.get_belong());
			values.put("steps", step.get_detail());
			db.insert("step", null, values);
		}
		public void add_ingrdient(Ingredient ingredient){
			//TODO
		}
		public void delete_recipe(String rid){	
			db.delete("localrecipe","rid =" + rid, null);
		}
		public ArrayList<Recipe> loadRecipes(){
			ArrayList<Recipe> recipes = new ArrayList<Recipe>();
			Cursor cursor_r = db.query("recipes", null, null, null, null, null, null);
			
			return recipes;
		}
		private Recipe make_recipe_object(Cursor cursor){
			Recipe recipes = new Recipe();
			recipes.setID(cursor.getString(0));
			recipes.setName(cursor.getString(1));
			return recipes;
		}
		
}
