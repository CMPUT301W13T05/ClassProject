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
 * This class initial the database.
 * Also, this class contains many operations that is oriented to the database.
 * Such as add, edit, delete, search.
 * All interaction with local recipes much go through the DatabaseManager
 * The values below indicate local recipes' property whether they are download, upload, created, received
 * share = 98
 * owned = 99
 * download = 100
 * upload = 101
 * @author  HongZu
 */
public class DatabaseManager {
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static DatabaseManager instance = null;
		private SQLiteDatabase db ;
		/**
		 * @uml.property  name="dbHelper"
		 * @uml.associationEnd  
		 */
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
		public void delete_images(String rid) {
			db.delete("picture", "rid = '"+rid+"'", null);
		}
		/**
		 * delete_steps(Step step) deletes the row 
		 * contains the current step object in the step table
		 * @param step
		 */
		public void delete_steps(String rid){
			db.delete("step", "rid = '"+rid+"'", null);
		}
		/**
		 * delete_ingredient(Ingredient ingredient) deletes the row 
		 * contains the current ingredient object in ingredient table
		 * @param ingredient
		 */
		public void delete_ingredient(String rid){
			db.delete("ingredient", "rid ='"+rid+"'", null);
		}
		/**
		 * delete_recipe(Recipe recipe) deletes the row
		 * contains the current recipe object in all tables
		 * @param recipe
		 */
		public void delete_recipe(Recipe recipe){	
			delete_images(recipe.getID());
			delete_ingredient(recipe.getID());
			delete_steps(recipe.getID());
			db.delete("localrecipe","rid ='" + recipe.getID()+"'", null);
		}
		/**
		 * searchRecipes(String name, String rid is a function that search recipes from database
		 * by using recipe name and recipe ID as constraints
		 * @param name
		 * @return an ArrayList contains recipe objects
		 */
		public ArrayList<Recipe> searchRecipes(String[] var, int condition) {
			int add_to_list = 0;
			ArrayList<Recipe> recipes = new ArrayList<Recipe>();
			Cursor cursor_r = null;
			Cursor cursor_d = null;
			Cursor cursor_a = null;
			Cursor cursor_b = null;
			if(condition == -1) {cursor_r = db.query("localrecipe", null, "rid <> 'ingredientsonhand'", null, null, null, null);}//search all local recipes
			else if(condition == 0) {
				for(int i = 0; i<var.length;i++){ 
					cursor_d = db.query("ingredient", null, "name = '"+var[i]+"'", null, null, null, null);
					cursor_d.moveToFirst();
					while(!cursor_d.isAfterLast()){
						cursor_r = db.query("localrecipe", null, "rid ='"+cursor_d.getString(2)+"'", null, null, null, null);
						cursor_r.moveToFirst();
						while(!cursor_r.isAfterLast()){
							if(!cursor_r.getString(0).equals("ingredientsonhand")){
								Recipe recipe = rebuildRecipe(cursor_r);
								recipes.add(recipe);
							}
							cursor_r.moveToNext();
						}
						cursor_r.close();
						cursor_d.moveToNext();
					}
					cursor_d.close();
					add_to_list = 1;
				}
			}//search local recipes by ingredient
			else if(condition == 1){
				for(int i = 0;i<var.length;i++){
					cursor_r = db.query("localrecipe", null, "name ='"+var[i]+"'", null, null, null, null);
				}}//search local recipes by name
			else if(condition == 98){cursor_r = db.query("localrecipe", null, "download_upload_own = 98", null, null, null, null);}//search shared to me recipes
			else if(condition == 99){cursor_r = db.query("localrecipe", null, "download_upload_own = 99", null, null, null, null);}//search owned recipes
			else if(condition == 100){cursor_r = db.query("localrecipe", null, "download_upload_own = 100", null, null, null, null);}//search upload recipes
			else if(condition == 101){cursor_r = db.query("localrecipe", null, "download_upload_own = 101", null, null, null, null);}//search download recipes
			else {
				for(int i = 0;i<var.length;i++){
					cursor_a = db.query("ingredient", null, "name = '"+var[i]+"'", null, null, null, null);
					cursor_a.moveToFirst();
					while(!cursor_a.isAfterLast()){
						cursor_r = db.query("localrecipe", null, "rid ='"+cursor_a.getString(2)+"'or name = '"+var[i]+"'", null, null, null, null);
						cursor_r.moveToFirst();
						while(!cursor_r.isAfterLast()){
							if(!cursor_r.getString(0).equals("ingredientsonhand")){
								Recipe recipe = rebuildRecipe(cursor_r);
								recipes.add(recipe);
							}
							cursor_r.moveToNext();
						}
						cursor_r.close();
						cursor_a.moveToNext();
					}
					cursor_a.close();
					
					if(cursor_r == null) {
						cursor_b = db.query("localrecipe", null, "name = '"+var[i]+"'", null, null, null, null);
						cursor_b.moveToFirst();
						while(!cursor_b.isAfterLast()){
							if(!cursor_b.getString(0).equals("ingredientsonhand")){
								Recipe recipe = rebuildRecipe(cursor_b);
								recipes.add(recipe);
							}
							cursor_b.moveToNext();
						}
						cursor_b.close();
					}
				}
				add_to_list = 1;
			}//search local recipes with given name and ingredient
			if(add_to_list == 0){
				cursor_r.moveToFirst();
				while(!cursor_r.isAfterLast()){
					if(!cursor_r.getString(0).equals("ingredientsonhand")){
						Recipe recipe = rebuildRecipe(cursor_r);
						recipes.add(recipe);
					}
					cursor_r.moveToNext();
				}
				cursor_r.close();
			}
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
			Ingredient ingredients = new Ingredient(cursor.getString(1),cursor.getString(3),cursor.getString(2));
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
		/**
		 * inDB(Recipe recipe) receives a recipe object and check if the recipe is in the database already
		 * @param recipe
		 * @return boolean
		 */
		public boolean inDB(Recipe recipe){
			Cursor query_c = null;
			query_c = db.query("localrecipe", null, "rid='"+recipe.getID()+"'", null, null, null, null);
			//System.out.println(query_c.getString(0)+query_c.getString(1));
			if(query_c.getCount() <=0){
				query_c.close();
				return false;
			}
			else{
				//System.out.println("JUMPHERE");
				query_c.close();
				return true;
			}
		}
		/**
		 * IngredientsOnHand() returns  a recipe object that contains the ingredients in my fridge
		 * @return recipe obejct
		 */
		public Recipe IngredientsOnHand(){
			ArrayList<Ingredient> my_ingredients = searchIngredients(null, "ingredientsonhand");
			return new Recipe("ingredientsonhand",null,null,my_ingredients,null,999);
		}
		/**
		 * This operation checks if the cachedrecipe table contain more than ten recipes
		 * @return true for more than 10 recipes, false otherwise
		 */
		public boolean greaterThanTen(){
			Cursor cursor = db.query("cachedrecipe", null, null, null, null, null, null);
			if(cursor.getCount()>11){
				cursor.close();
				return true;
			}
			else{
				cursor.close();
				return false;
			}
			
		}
		/**
		 * This operation deletes the cached recipe from database
		 * @param rid
		 */
		public void deleteCacheRecipe(String rid){
			delete_images(rid);
			delete_ingredient(rid);
			delete_steps(rid);
			db.delete("cachedrecipe", "rid = '"+rid+"'", null);
			db.delete("localrecipe","rid ='" + rid+"'", null);
		}
		/**
		 * This operation checks if the recipe is cached already
		 * @param recipe
		 * @return boolean
		 */
		public boolean inCache(Recipe recipe){
			Cursor query_c = null;
			query_c = db.query("cachedrecipe", null, "rid='"+recipe.getID()+"'", null, null, null, null);
			if(query_c.getCount() <=0){
				query_c.close();
				return false;
			}
			else{
				query_c.close();
				return true;
			}
		}
		/**
		 * This operation add cached recipe into database
		 * @param recipe
		 */
		public void addCacheRecipe(Recipe recipe){
			if(greaterThanTen()){
				Cursor first_row = db.query("cachedrecipe", null, null, null, null, null, null);
				first_row.moveToFirst();
				deleteCacheRecipe(first_row.getString(0));
				first_row.close();
			}
			if(!inCache(recipe)){
				add_recipe(recipe);
				add_step(recipe.getSteps());
				for(int i = 0;i<recipe.getImages().size();i++){
					add_image(recipe.getImages().get(i));
				}
				for(int j = 0;j<recipe.getIngredients().size();j++){
					add_ingrdient(recipe.getIngredients().get(j));
				}
				ContentValues values = new ContentValues();
				values.put("rid", recipe.getID());
				db.insert("cachedrecipe", null, values);
			}
		}
		/**
		 * This operation returns all cached recipes in the database
		 * @return ArrayList<Recipe>
		 */
		public ArrayList<Recipe> cachedRecipe(){
			ArrayList<Recipe> cache = new ArrayList<Recipe>();
			Cursor history = db.query("cachedrecipe", null, null, null, null, null, null);
			history.moveToFirst();
			while(!history.isAfterLast()){
				Cursor cursor_r = db.query("localrecipe", null, "rid = '"+history.getString(0)+"'", null, null, null, null);
				Recipe recipe = rebuildRecipe(cursor_r);
				cache.add(recipe);
			}
			history.close();
			return cache;
		}
		
}
