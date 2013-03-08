package com.example.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class CreateRecipe extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		//button home
		Button add_modify_home = (Button)findViewById(R.id.home);
		add_modify_home.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CreateRecipe.this, MainPage.class);
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipe.this.finish();
			}
		});	
		//button home
		Button add_modify_save = (Button)findViewById(R.id.save);
		add_modify_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
			}
		});	
		//button photo
		Button add_modify_photo = (Button)findViewById(R.id.photo);
		add_modify_photo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipe.this, Itisatry.class);// should be jump to the modify image
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipe.this.finish();
			
			}
		});
		//button ingredients
		Button add_modify_ingredients = (Button)findViewById(R.id.Ingredients);
		add_modify_ingredients.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipe.this, ModifyIngredients.class);// should be jump to the modify 
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipe.this.finish();
			
			}
		});	
		//button description
		Button add_modify_description = (Button)findViewById(R.id.description);
		add_modify_description.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipe.this, Itisatry.class);// should be jump to the modify 
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipe.this.finish();
			
			}
		});	
		
	}
	

	

}