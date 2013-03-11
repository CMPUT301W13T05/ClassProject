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
		public void drop(){
			dbHelper.onUpgrade(db, 1, 1);
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
		
		/**
		 * add_recipe(Recipe recipe) is a function 
		 * that insert a recipe object into database
		 * @param recipe
		 */
		public void add_recipe(Recipe recipe) {
			ContentValues values = new ContentValues();
			values.put("rid", recipe.getID());
			values.put("name",recipe.getName());
			values.put("download_upload_own", recipe.get_download_upload_own());
			db.insert("localrecipe", null, values);

		}
		/**
		 * add_image(Image image) is a function
		 * that insert a image object into databse
		 * @param image
		 */
		public void add_image(Image image) {
			ContentValues values = new ContentValues();
			values.put("pid", image.get_IMAGE_ID());
			values.put("rid", image.get_image_belongto());
			values.put("image_uri", image.get_imageUri());
			db.insert("picture", null, values);
		}
		/**
		 * add_step(Step step) is a function
		 * that insert a step object into database 
		 * @param step
		 */
		public void add_step(Step step){
			ContentValues values = new ContentValues();
			values.put("rid", step.get_belong());
			values.put("steps", step.get_detail());
			db.insert("step", null, values);
		}
		/**
		 * add_ingrdient(Ingredient ingredient) is a function
		 * that insert a ingredient function into database
		 * @param ingredient
		 */
		public void add_ingrdient(Ingredient ingredient){
			
			ContentValues values = new ContentValues();
			values.put("name", ingredient.get_name());
			values.put("rid", ingredient.get_belongto());
			values.put("amount", ingredient.get_amount());
			db.insert("ingredient", null, values);
			}
		/**
		 * update_recipe(Recipe recipe) can update the row
		 * contains the old recipe object in all tables
		 * @param recipe
		 */
		public void update_recipe(Recipe recipe) {
			ContentValues values = new ContentValues();
			values.put("rid", recipe.getID());
			values.put("name",recipe.getName());
			values.put("download_upload_own",recipe.get_download_upload_own());
			db.update("localrecipe", values, "rid ='" + recipe.getID()+"'", null);
		}
		/**
		 * update_ingredient(Ingredient ingredient) can update the row
		 * contains the old ingredients object in ingredient table
		 * @param ingredient
		 */
		public void update_ingredient(Ingredient ingredient){
			ContentValues values = new ContentValues();
			values.put("name", ingredient.get_name());
			values.put("rid", ingredient.get_belongto());
			values.put("amount", ingredient.get_amount());
			db.update("ingredient", values, "rid ='"+ ingredient.get_belongto()+"' and name = '"+ingredient.get_name()+"'", null);
		}
		/**
		 * update_steps(Step step) can update the row
		 * contains the old step object in step table
		 * @param step
		 */
		public void update_steps(Step step){
			ContentValues values = new ContentValues();
			values.put("rid", step.get_belong());
			values.put("steps", step.get_detail());
			db.update("step", values, "rid ='" +step.get_belong(), null);
		}	
		public void delete_images(Image image) {
			db.delete("picture", "rid = '"+image.get_image_belongto()+"'", null);
		}
		/**
		 * delete_steps(Step step) deletes the row 
		 * contains the current step object in the step table
		 * @param step
		 */
		public void delete_steps(Step step){
			db.delete("step", "rid = '"+step.get_belong()+"'", null);
		}
		/**
		 * delete_ingredient(Ingredient ingredient) deletes the row 
		 * contains the current ingredient object in ingredient table
		 * @param ingredient
		 */
		public void delete_ingredient(Ingredient ingredient){
			db.delete("ingredient", "rid ='"+ingredient.get_belongto()+"'and name ='"+ ingredient.get_name()+"'", null);
		}
		/**
		 * delete_recipe(Recipe recipe) deletes the row
		 * contains the current recipe object in all tables
		 * @param recipe
		 */
		public void delete_recipe(Recipe recipe){	
			ArrayList<Image> de_Image = new ArrayList<Image>();
			de_Image = recipe.getImages();
			for (int n = 0;n<de_Image.size();n++){
				delete_images(de_Image.get(n));
			}
			ArrayList<Ingredient> de_Ingredient = new ArrayList<Ingredient>();
			de_Ingredient = recipe.getIngredients();
			// delete ingredient belong to the given recipe from ingredient table
			int i;
			for (i = 0;i < de_Ingredient.size();i++){
				delete_ingredient(de_Ingredient.get(i));
			}
			//Step de_Step = new Step();
			Step de_Step = recipe.getSteps();
			delete_steps(de_Step);
			// delete recipe from recipe table
			db.delete("localrecipe","rid ='" + recipe.getID()+"'", null);
		}
		/**
		 * searchRecipes(String name, String rid is a function that search recipes from database
		 * by using recipe name and recipe ID as constraints
		 * @param name
		 * @return an ArrayList contains recipe objects
		 */
		public ArrayList<Recipe> searchRecipes(String var, int condition) {
			ArrayList<Recipe> recipes = new ArrayList<Recipe>();
			Cursor cursor_r;
			Cursor cursor_d;
			Cursor cursor_a;
			if(condition == -1) {cursor_r = db.query("localrecipe", null, null, null, null, null, null);}
			else if(condition == 0) {
				cursor_d = db.query("ingredient", null, "ingredient = '"+var+"'", null, null, null, null);
				cursor_r = db.query("localrecipe", null, "rid ='"+cursor_d.getString(2)+"'", null, null, null, null);
				cursor_d.close();
				}
			else if(condition == 1){cursor_r = db.query("localrecipe", null, "name ='"+var+"'", null, null, null, null);}
			else {	
				cursor_a = db.query("ingredient", null, "ingredient = '"+var+"'", null, null, null, null);
				cursor_r = db.query("localrecipe", null, "rid ='"+cursor_a.getString(2)+"'and name = '"+var+"'", null, null, null, null);
				cursor_a.close();
				}
			cursor_r.moveToFirst();
			while(!cursor_r.isAfterLast()){
				Recipe recipe = rebuildRecipe(cursor_r);
				recipes.add(recipe);
				cursor_r.moveToNext();
			}
			cursor_r.close();
			return recipes;
		}
		/**
		 * searchImages(String rid) is a function that search images with giving recipe ID from database
		 * @param rid
		 * @return an ArrayList contains image objects
		 */
		public ArrayList<Image> searchImages(String rid){
			ArrayList<Image> images = new ArrayList<Image>();
			Cursor cursor_p;
			if(rid==null) { cursor_p = db.query("picture", null, null, null, null, null, null);}
			else { cursor_p = db.query("picture", null, "rid = '"+rid+"'", null, null, null, null);}
			cursor_p.moveToFirst();
			while(!cursor_p.isAfterLast()){
				Image image = rebuildImage(cursor_p);
				images.add(image);	
				cursor_p.moveToNext();
			}
			cursor_p.close();
			return images;
		}
		/**
		 * searchIngredients(String name, String rid) is a function that search ingredients 
		 * with giving recipe ID and ingredient name from database
		 * @param name
		 * @param rid
		 * @return an ArrayList contains ingredients objects
		 */
		public ArrayList<Ingredient> searchIngredients(String name, String rid){
			ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
			Cursor cursor_i;
			if(name == null && rid == null) {cursor_i = db.query("ingredient", null, null, null, null, null, null);}
			else if(name == null && rid != null) {cursor_i = db.query("ingredient", null, "rid='"+rid+"'", null, null, null, null);}
			else {cursor_i = db.query("ingredient", null, "name='"+name+"'", null, null, null, null);}
			cursor_i.moveToFirst();
			while(!cursor_i.isAfterLast()){
				Ingredient ingredient = rebuildIngredient(cursor_i);
				ingredients.add(ingredient);	
				cursor_i.moveToNext();
			}
			cursor_i.close();
			return ingredients;
		}
		/**
		 * searchSteps(String rid) is a function that search steps of making a dish
		 * with giving recipe ID from database
		 * @param rid
		 * @return an ArrayList contains steps objects
		 */
		public Step searchSteps(String rid){
			Cursor cursor_s;
			if(rid == null) {cursor_s = db.query("step", null, null, null, null, null, null);}
			else {cursor_s = db.query("step", null, "rid ='"+rid+"'", null, null, null, null);}
			cursor_s.moveToFirst();
			Step step = rebuildStep(cursor_s);
			cursor_s.close();
			return step;
		}
		/**
		 * rebuildRecipe(Cursor cursor) takes a bunch of strings and ArrayList
		 * and makes them to a recipe object
		 * @param cursor
		 * @return recipe object
		 */
		private Recipe rebuildRecipe(Cursor cursor){
			ArrayList<Image> image_arraylist = searchImages(cursor.getString(0));
			ArrayList<Ingredient> ingredient_arraylist = searchIngredients(null,cursor.getString(0));
			Step step_obj = searchSteps(cursor.getString(0));
			Recipe recipes = new Recipe(cursor.getString(0),cursor.getString(1),image_arraylist,ingredient_arraylist,step_obj,cursor.getInt(2));
			return recipes;
		}
		/**
		 * rebuildImage(Cursor cursor) 
		 * @param cursor
		 * @return image object
		 */
		private Image rebuildImage(Cursor cursor){
			Image images = new Image(cursor.getString(0),cursor.getString(1),cursor.getString(2));
			return images;
		}
		/**
		 * rebuildIngredient(Cursor cursor) takes strings from database
		 * and makes them an ingredient object
		 * @param cursor
		 * @return ingredient object
		 */
		private Ingredient rebuildIngredient(Cursor cursor){
			Ingredient ingredients = new Ingredient(cursor.getString(0),cursor.getString(1),cursor.getString(2));
			return ingredients;
		}
		/**
		 * rebuildStep(Cursor cursor) takes strings from database
		 * and makes them a step object
		 * @param cursor
		 * @return step object
		 */
		private Step rebuildStep(Cursor cursor){
			Step steps = new Step(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
			return steps;
		}
		
		
		
}
