package com.example.easycooking.view;


import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyProfileActivity extends Activity {
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile);
		myapp = (MyApp)getApplication();
		// record recipes
		Button profile_record = (Button)findViewById(R.id.record);
		profile_record.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
		        	dB_LocalDatabaseManager.open();
	        		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
	        		result_recipe = dB_LocalDatabaseManager.searchRecipes(null, 99);
	        		dB_LocalDatabaseManager.close();
	        		if (result_recipe.size()>0){
		        		Intent intent = new Intent();
		        		Bundle mbundle = new Bundle();
		        		//mbundle.putSerializable("RECIPE_RESULT", result_recipe);
		        		myapp.setRecipe_list(result_recipe);
		        		intent.putExtras(mbundle);
		        		intent.setClass(MyProfileActivity.this, RecipeResultActivity.class);
		        		startActivity(intent);
	        		}
	        		else{
	        			Toast toast = Toast.makeText(MyProfileActivity.this, "No Recipe Recored", Toast.LENGTH_LONG);   
						toast.show();
	        		}
	        	}
	        });
		// local recipes
		Button profile_local = (Button)findViewById(R.id.checklocal);
		profile_local.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        	dB_LocalDatabaseManager.open();
        		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
        		result_recipe = dB_LocalDatabaseManager.searchRecipes(null, -1);
        		dB_LocalDatabaseManager.close();
        		if (result_recipe.size()>0){
	        		Intent intent = new Intent();
	        		Bundle mbundle = new Bundle();
	        		//mbundle.putSerializable("RECIPE_RESULT", result_recipe);
	        		myapp.setRecipe_list(result_recipe);
	        		intent.putExtras(mbundle);
	        		intent.setClass(MyProfileActivity.this, RecipeResultActivity.class);
	        		startActivity(intent);
        		}
        		else{
        			Toast toast = Toast.makeText(MyProfileActivity.this, "No Recipe IN LOCAL", Toast.LENGTH_LONG);   
					toast.show();
        		}
	        	}
	        });
		// upload recipes
		Button profile_upload = (Button)findViewById(R.id.upload);
		profile_upload.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
//	        		Intent intent = new Intent();
//	        		intent.setClass(MyProfileActivity.this, RecipeResultActivity.class);
//	        		//start a add entry activity
//	        		startActivity(intent);
//	        		//close the old activity
//	        		MyProfileActivity.this.finish();
	        	Toast toast = Toast.makeText(MyProfileActivity.this, "TODO", Toast.LENGTH_LONG);   
				toast.show();
	        	}
	        });
		// downloaded recipes
		Button profile_downloaded = (Button)findViewById(R.id.check_download);
		profile_downloaded.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
//	        		Intent intent = new Intent();
//	        		intent.setClass(MyProfileActivity.this, RecipeResultActivity.class);
//	        		//start a add entry activity
//	        		startActivity(intent);
//	        		//close the old activity
//	        		MyProfileActivity.this.finish();
	        	Toast toast = Toast.makeText(MyProfileActivity.this, "TODO", Toast.LENGTH_LONG);   
				toast.show();
	        	
	        	}
	        });
		// back to the main
		Button profile_back_main = (Button)findViewById(R.id.backtomain);
		profile_back_main.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfileActivity.this, MainPageActivity.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfileActivity.this.finish();
	        	}
	        });
		// share with me button
		Button profile_share = (Button)findViewById(R.id.share_with_me);
		profile_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        	// to be implemented
	        	Toast toast = Toast.makeText(MyProfileActivity.this, "TODO", Toast.LENGTH_LONG);   
				toast.show();
	        	}
	        });
		
	}

	@Override
	
	//back button pressed
	public void onBackPressed() {
	return;
	}

}
