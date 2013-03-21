package com.example.easycooking.view;


import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * This the MainPageActivity.Like the home_page.
 *  
 * Searhing bar and Other functions' entry.
 * @author Alvin
 *
 */
public class MainPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText serching_text = (EditText)findViewById(R.id.editText1);
		/**
		 * Search button 
		 */
		 Button main_search = (Button)findViewById(R.id.search);
		 main_search.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		/*
	        		 * Complete get ArrayList<Recipe>
	        		 */
	        		if (serching_text.getText().toString().isEmpty()){
	        			Toast toast = Toast.makeText(MainPageActivity.this, "Empty Input!", Toast.LENGTH_LONG);   
						toast.show();
	        		}
		        	else{
		        		dB_LocalDatabaseManager.open();
		        		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
		        		result_recipe=dB_LocalDatabaseManager.searchRecipes(serching_text.getText().toString(), -99);
		        		dB_LocalDatabaseManager.close();
		        		if (result_recipe.size()>0){
			        		Intent intent = new Intent();
			        		Bundle mbundle = new Bundle();
			        		mbundle.putSerializable("RECIPE_RESULT", result_recipe);
			        		intent.putExtras(mbundle);
			        		intent.setClass(MainPageActivity.this, RecipeResultActivity.class);
			        		startActivity(intent);
		        		}
		        		else{
		        			Toast toast = Toast.makeText(MainPageActivity.this, "No Recipe Matched", Toast.LENGTH_LONG);   
							toast.show();
		        		}
		        	
		        		
		        	}
	        }
	        });
		 /**
		  * Profile button
		  */
		 Button main_profile =(Button)findViewById(R.id.profile);
		 main_profile.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		intent.setClass(MainPageActivity.this, MyProfileActivity.class);
		        		/**
		        		 * start a add entry activity
		        		 */
		        		startActivity(intent);
		        		/**
		        		 * close the old activity
		        		 */
		        		/*
		        		 * MainPageActivity.this.finish();
		        		 */
		        	}
		        });
		 /**
		  * Create Recipe 
		  */
		 Button main_create = (Button)findViewById(R.id.creat);
		 main_create.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		Bundle mbundle = new Bundle();
		        		mbundle.putString("FromWhere", "MAIN");
		        		intent.putExtras(mbundle);
		        		intent.setClass(MainPageActivity.this, CreateRecipeActivity.class);
		        		/**
		        		 * start a add entry activity
		        		 */
		        		startActivity(intent);
		        		
		        	}
		        });
		 /**
		  * Custom Search
		  */
		 Button main_custom = (Button)findViewById(R.id.setup_search);
		 main_custom.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		/**
		        		 * TODO implemented
		        		 */
		        	Toast toast = Toast.makeText(MainPageActivity.this, "TODO", Toast.LENGTH_LONG);   
					toast.show();
		        	}
		        });
		/**
		 * ModifyIngredientsActivityAcitivty
		 */
		 Button main_ingredients = (Button)findViewById(R.id.modifyingre);
		 main_ingredients.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        	int todo = 0;
		        	if (todo == 0){
		        		Toast toast = Toast.makeText(MainPageActivity.this, "TODO", Toast.LENGTH_LONG);   
						toast.show();
			        	}
		        	dB_LocalDatabaseManager.open();
	        		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
	        		result_recipe=dB_LocalDatabaseManager.searchRecipes(null, 999);
		        	Recipe mrecipe = result_recipe.get(0);
		        		Intent intent = new Intent();
		        		Bundle mbundle = new Bundle();
		        		mbundle.putSerializable("RECIPE_KEY", mrecipe);
		        		intent.putExtras(mbundle);
		        		intent.setClass(MainPageActivity.this, ModifyIngredientsActivity.class);
		        		
		        		/**
		        		 * start a add entry activity
		        		 */
		        		startActivity(intent);
		        		/**
		        		 * close the old activity
		        		 */
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
