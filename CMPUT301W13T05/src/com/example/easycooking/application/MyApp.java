package com.example.easycooking.application;

import java.util.ArrayList;

import com.example.easycooking.model.Recipe;

import android.app.Application;
/**
 * This is a class extends application i add some variables like 
 * a global condition and also some functions to control them
 * @author Adonis
 *
 */
	public class MyApp extends Application {
        private ArrayList<Recipe> mrecipe_list;
        private Recipe mrecipe;
        private ArrayList<String> onhand;
        
        @Override
        public void onCreate() {
                super.onCreate();
                setRecipe_list(Set_recipe_list); //
                setRecipe(Set_recipe);
                setOnhand(Set_onhand);
        }
        public ArrayList<Recipe> getAll() {
                return mrecipe_list;
        }
        public Recipe get_mrecipe(){
        		return this.mrecipe;
        }
        public void setRecipe_list(ArrayList<Recipe> recipe_list) {
                this.mrecipe_list = recipe_list;
        }
        public void setRecipe(Recipe recipe){
        		this.mrecipe = recipe;
        }
        public ArrayList<String> get_onhand(){
        	return onhand;
        }
        public void setOnhand(ArrayList<String> onhand_list){
        	this.onhand = onhand_list;
        }
        private static final ArrayList<Recipe> Set_recipe_list = null;
        private static final Recipe Set_recipe = null;
        private static final ArrayList<String> Set_onhand = new ArrayList<String>();
}

