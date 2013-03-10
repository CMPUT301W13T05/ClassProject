package com.example.easycooking.controller;


import java.util.ArrayList;

import com.example.easycooking.model.Image;
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
public class CopyOfDatabaseManager {
	private static CopyOfDatabaseManager instance = null;

		private SQLiteDatabase db ;
		private DatabaseHelper dbHelper;
		
		protected CopyOfDatabaseManager(Context context){
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
		public static CopyOfDatabaseManager getInstance(Context context){
			if(instance == null){
				instance = new CopyOfDatabaseManager(context);
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
			cursor_r.moveToFirst();
			while(!cursor_r.isAfterLast()){
				Recipe recipe = rebuildRecipe(cursor_r);
				recipes.add(recipe);
				cursor_r.moveToNext();
			}
			cursor_r.close();
			return recipes;
		}
		public ArrayList<Image> loadImages(){
			ArrayList<Image> images = new ArrayList<Image>();
			Cursor cursor_p = db.query("picture", null, null, null, null, null, null);
			cursor_p.moveToFirst();
			while(!cursor_p.isAfterLast()){
			}
			cursor_p.close();
			return images;
		}
		public ArrayList<Ingredient> loadIngredient(){
			ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
			Cursor cursor_i = db.query("ingredient", null, null, null, null, null, null);
			cursor_i.moveToFirst();
			while(!cursor_i.isAfterLast()){
				Ingredient ingredient = rebuildIngredient(cursor_i);
				ingredients.add(ingredient);	
				cursor_i.moveToNext();
			}
			cursor_i.close();
			return ingredients;
		}
		public ArrayList<Step> loadStep(){
			ArrayList<Step> steps = new ArrayList<Step>();
			Cursor cursor_s = db.query("step", null, null, null, null, null, null);
			cursor_s.moveToFirst();
			while(!cursor_s.isAfterLast()){
				Step step = rebuildStep(cursor_s);
				steps.add(step);
				cursor_s.moveToNext();
			}
			cursor_s.close();
			return steps;
		}
		private Recipe rebuildRecipe(Cursor cursor){
			Recipe recipes = new Recipe(cursor.getString(0),cursor.getString(1),null,null);
			//recipes.setID(cursor.getString(0));
			//recipes.setName(cursor.getString(1));
			return recipes;
		}
		/*private Image rebuleImage(Cursor cursor){
			Image images = new Image();
			return images;
		}*/
		private Ingredient rebuildIngredient(Cursor cursor){
			Ingredient ingredients = new Ingredient(cursor.getString(0),cursor.getString(1));
			return ingredients;
		}
		private Step rebuildStep(Cursor cursor){
			Step steps = new Step(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
			return steps;
		}
		
		
}
