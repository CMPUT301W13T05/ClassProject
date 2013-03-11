package com.example.easycooking;

import java.util.ArrayList;
import java.util.UUID;

import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class CreateRecipeActivity extends Activity {
	private static String _FROM_WHERE = "";
	private static Recipe mrecipe = new Recipe();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		
		/////////Set up view
		final EditText enter_recipe_name = (EditText)findViewById(R.id.recipe_name);
		TextView count_updated =(TextView)findViewById(R.id.uploaded_view);
		TextView count_ingredients =(TextView)findViewById(R.id.ingredients_view);
		TextView count_steps =(TextView)findViewById(R.id.steps_view);
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		if (_FROM_WHERE.equals("MAIN")){
			UUID uuid = UUID.randomUUID(); 
			mrecipe.setID(uuid.toString());
			mrecipe.setImages(new ArrayList<Image>());
			mrecipe.setIngredients(new ArrayList<Ingredient>());
			mrecipe.setSteps(new ArrayList<Step>());
		}
		else if (_FROM_WHERE.equals("MODIFY") ){
			mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
			enter_recipe_name.setText(mrecipe.getName());
			count_updated.setText(mrecipe.getImages().size() + " uploaded");
			count_ingredients.setText(mrecipe.getIngredients().size() + " ingredients");
			count_steps.setText(mrecipe.getSteps().size() + " stpes");
		}
		//button home
		//@SuppressWarnings("unchecked")
		//test object 
		//ArrayList<Recipe> mrecipe = (ArrayList<Recipe>)getIntent().getSerializableExtra("ArrayList");
		//EditText enter_recipe_name = (EditText)findViewById(R.id.recipe_name);
		//enter_recipe_name.setText(_FROM_WHERE+mrecipe.size());
		//button home
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
		//button save
		Button add_modify_save = (Button)findViewById(R.id.save);
		add_modify_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mrecipe.setName(enter_recipe_name.getText().toString());
				mrecipe.set_download_upload_own(1);
				dB_LocalDatabaseManager.open();
				
				ArrayList<Ingredient> db_input_ingredients = mrecipe.getIngredients();
				ArrayList<Step> db_input_steps = mrecipe.getSteps();
				dB_LocalDatabaseManager.add_recipe(mrecipe);
				int i;
				for (i = 0 ; i < db_input_ingredients.size(); i++ ){
					dB_LocalDatabaseManager.add_ingrdient(mrecipe.getIngredients().get(i));
				}
				for (i =0 ; i < db_input_steps.size(); i++){
					dB_LocalDatabaseManager.add_step(mrecipe.getSteps().get(i));
				}
				dB_LocalDatabaseManager.close();
				enter_recipe_name.setText(mrecipe.getID());
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, MainPageActivity.class);
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipeActivity.this.finish();
			}
		});	
		/**
		 * The add_modify_photo function
		 */
		Button add_modify_photo = (Button)findViewById(R.id.photo);
		add_modify_photo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, ModifyImageActivity.class);// should be jump to the modify image
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
				Bundle mbundle = new Bundle();
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				//close the old activity
				//CreateRecipeActivity.this.finish();
			
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