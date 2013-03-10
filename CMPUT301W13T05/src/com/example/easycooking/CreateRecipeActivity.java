package com.example.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class CreateRecipeActivity extends Activity {
	private static String _FROM_WHERE = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		//button home
		EditText enter_recipe_name = (EditText)findViewById(R.id.recipe_name);
		enter_recipe_name.setText(_FROM_WHERE);
		Button add_modify_home = (Button)findViewById(R.id.home);
		add_modify_home.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, MainPageActivity.class);
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipeActivity.this.finish();
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
				intent.setClass(CreateRecipeActivity.this, Itisatry.class);// should be jump to the modify image
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipeActivity.this.finish();
			
			}
		});
		//button ingredients
		Button add_modify_ingredients = (Button)findViewById(R.id.Ingredients);
		add_modify_ingredients.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, ModifyIngredientsActivity.class);// should be jump to the modify 
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipeActivity.this.finish();
			
			}
		});	
		//button description
		Button add_modify_description = (Button)findViewById(R.id.description);
		add_modify_description.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, Itisatry.class);// should be jump to the modify 
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipeActivity.this.finish();
			
			}
		});	
		
	}
	

	

}