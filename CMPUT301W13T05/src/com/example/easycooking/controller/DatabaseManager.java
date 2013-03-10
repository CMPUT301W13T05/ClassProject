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
		//insert an step 
		public void add_step(Step step){
			ContentValues values = new ContentValues();
			values.put("recipe_step", step.get_recipe_step());
			values.put("rid", step.get_belong());
			values.put("steps", step.get_detail());
			db.insert("step", null, values);
		}
		//insert an ingredient
		public void add_ingrdient(Ingredient ingredient){
			//TODO
			ContentValues values = new ContentValues();
			values.put("name", ingredient.get_name());
			values.put("rid", ingredient.get_belongto());
			values.put("amount", ingredient.get_amount());
			db.insert("ingredient", null, values);
			}
		//update an ingredient
		public void update_recipe(Recipe recipe) {
			ContentValues values = new ContentValues();
			values.put("rid", recipe.getID());
			values.put("name",recipe.getName());
			values.put("download_upload_own",recipe.get_download_upload_own());
			db.update("localrecipe", values, "rid =" + recipe.getID(), null);
		}
		public void update_ingredient(Ingredient ingredient){
			ContentValues values = new ContentValues();
			values.put("name", ingredient.get_name());
			values.put("rid", ingredient.get_belongto());
			values.put("amount", ingredient.get_amount());
			db.update("ingredient", values, "rid ="+ ingredient.get_belongto()+"and name = "+ingredient.get_name(), null);
		}
		
		public void update_steps(Step step){
			ContentValues values = new ContentValues();
			values.put("recipe_step", step.get_recipe_step());
			values.put("rid", step.get_belong());
			values.put("steps", step.get_detail());
			db.update("step", values, "rid =" +step.get_belong()+"and recipe_step =" + step.get_recipe_step(), null);
		}			
		public void delete_steps(Step step){
			db.delete("step", "rid = "+step.get_belong(), null);
		}
		public void delete_ingredient(Ingredient ingredient){
			db.delete("ingredient", "rid ="+ingredient.get_belongto()+"and name ="+ ingredient.get_name(), null);
		}
		public void delete_recipe(Recipe recipe){	
			ArrayList<Ingredient> de_Ingredient = new ArrayList<Ingredient>();
			de_Ingredient = recipe.getIngredients();
			// delete ingredient belong to the given recipe from ingredient table
			int i;
			for (i = 0;i < de_Ingredient.size();i++){
				delete_ingredient(de_Ingredient.get(i));
			}
			ArrayList<Step> de_Step = new ArrayList<Step>();
			de_Step = recipe.getSteps();
			// delete steps belong to the given recipe from steps table
			for (i = 0;i < de_Step.size();i++){
				delete_steps(de_Step.get(i));
			}
			// delete recipe from recipe table
			db.delete("localrecipe","rid =" + recipe.getID(), null);
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
