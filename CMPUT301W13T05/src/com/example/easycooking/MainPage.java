package com.example.easycooking;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//search button 
		 Button main_search = (Button)findViewById(R.id.search);
		 main_search.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MainPage.this, RecipeResult.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MainPage.this.finish();
	        	}
	        });
		 //Profile button
		 Button main_profile =(Button)findViewById(R.id.profile);
		 main_profile.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		intent.setClass(MainPage.this, MyProfile.class);
		        		//start a add entry activity
		        		startActivity(intent);
		        		//close the old activity
		        		MainPage.this.finish();
		        	}
		        });
		 //Create Recipe
		 Button main_create = (Button)findViewById(R.id.creat);
		 main_create.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		Intent intent = new Intent();
		        		intent.setClass(MainPage.this, CreateRecipe.class);
		        		//start a add entry activity
		        		startActivity(intent);
		        		//close the old activity
		        		MainPage.this.finish();
		        	}
		        });
		 //Custom Search
		 Button main_custom = (Button)findViewById(R.id.setup_search);
		 main_custom.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		//To be implemented
		        	}
		        });
	}
		 
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	//back button pressed
	public void onBackPressed() {
	return;
	}

}
