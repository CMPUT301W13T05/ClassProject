package com.example.easycooking;


import java.util.ArrayList;

import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/////////////////////////////////Search button 
		 Button main_search = (Button)findViewById(R.id.search);
		 main_search.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		//TODO get ArrayList<Recipe>
	        		dB_LocalDatabaseManager.open();
	        		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
	        		result_recipe=dB_LocalDatabaseManager.searchRecipes(null, null);
	        		dB_LocalDatabaseManager.close();
	        		Intent intent = new Intent();
	        		Bundle mbundle = new Bundle();
	        		mbundle.putSerializable("RECIPE_RESULT", result_recipe);
	        		intent.putExtras(mbundle);
	        		intent.setClass(MainPageActivity.this, RecipeResultActivity.class);
	        		//start a add entry activity
	        		
	        		startActivity(intent);
	        		//close the old activity
	        		//MainPageActivity.this.finish();
	        	}
	        });
		 ////////////////////////////////Profile button
		 Button main_profile =(Button)findViewById(R.id.profile);
		 main_profile.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		intent.setClass(MainPageActivity.this, MyProfileActivity.class);
		        		//start a add entry activity
		        		startActivity(intent);
		        		//close the old activity
		        		MainPageActivity.this.finish();
		        	}
		        });
		 ////////////////////////////////Create Recipe
		 Button main_create = (Button)findViewById(R.id.creat);
		 main_create.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		Bundle mbundle = new Bundle();
		        		mbundle.putString("FromWhere", "MAIN");
		        		//ArrayList<Recipe> mrecipe = new ArrayList<Recipe>();
		        		//mbundle.putSerializable("ArrayList", mrecipe);
		        		intent.putExtras(mbundle);
		        		intent.setClass(MainPageActivity.this, CreateRecipeActivity.class);
		        		//start a add entry activity
		        		startActivity(intent);
		        		//close the old activity
		        		//MainPageActivity.this.finish();
		        	}
		        });
		 ////////////////////////////////Custom Search
		 Button main_custom = (Button)findViewById(R.id.setup_search);
		 main_custom.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		//To be implemented
		        	}
		        });
		 ///////////////////////////////ModifyIngredientsActivityAcitivty
		 Button main_ingredients = (Button)findViewById(R.id.modifyingre);
		 main_ingredients.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		intent.setClass(MainPageActivity.this, ModifyIngredientsActivity.class);
		        		//start a add entry activity
		        		startActivity(intent);
		        		//close the old activity
		        		MainPageActivity.this.finish();
		        	}
		        });
	}
		 
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
//	@Override
//	//back button pressed
//	public void onBackPressed() {
//	return;
//	}

}
