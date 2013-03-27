package com.example.easycooking.application;

import java.util.ArrayList;

import com.example.easycooking.model.Recipe;

import android.app.Application;

	public class MyApp extends Application {
        private ArrayList<Recipe> mrecipe_list;
        private Recipe mrecipe;
        
        @Override
        public void onCreate() {
                super.onCreate();
                setRecipe_list(Set_recipe_list); //初始化全局变量
                setRecipe(Set_recipe);
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
        private static final ArrayList<Recipe> Set_recipe_list = null;
        private static final Recipe Set_recipe = null;
}

