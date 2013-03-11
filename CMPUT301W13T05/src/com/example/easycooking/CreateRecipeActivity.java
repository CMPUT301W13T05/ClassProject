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
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
/**
 * This is a CreateRcipeActivtiy View class That allow user to 
 * creat a new recipe and upload image, ingredient and steps
 * and insert them into database, and a object Recipe passed 
 * among activities
 * @author Alvin Sun
 *
 */

public class CreateRecipeActivity extends Activity {
	private static String _FROM_WHERE = "";
	private static Recipe mrecipe = new Recipe();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		/**
		 * Set up all the views widget
		 */
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
			mrecipe.setSteps(new Step());
			count_steps.setText("No steps");
		}
		else if (_FROM_WHERE.equals("MODIFY") ){
			mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
			enter_recipe_name.setText(mrecipe.getName());
			count_updated.setText(mrecipe.getImages().size() + " uploaded");
			count_ingredients.setText(mrecipe.getIngredients().size() + " ingredients");
			if (mrecipe.getSteps().get_detail().equals("")){
				count_steps.setText("No steps");
			}
			else{
				count_steps.setText("Updated");
			}
			
		}

		/**
		 * Home button and back to the main page
		 */
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
		
		/**
		 * The save button and handle the uncompleted information
		 */
		Button add_modify_save = (Button)findViewById(R.id.save);
		add_modify_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (enter_recipe_name.getText().toString().equals("")){
					Toast toast = Toast.makeText(CreateRecipeActivity.this, "Pleas Enter The Recipe Name", Toast.LENGTH_LONG);   
					toast.show();
				}
//				we do not need to check no image
//				else if (mrecipe.getImages().size() == 0){
//					Toast toast = Toast.makeText(CreateRecipeActivity.this, "Pleas Upadate The Recipe Picture", Toast.LENGTH_LONG);   
//					toast.show();
//				}
				else if (mrecipe.getIngredients().size() == 0){
					Toast toast = Toast.makeText(CreateRecipeActivity.this, "Pleas Add The Recipe Ingredients", Toast.LENGTH_LONG);   
					toast.show();
				}
				else if (mrecipe.getSteps().get_detail().equals("")){
					Toast toast = Toast.makeText(CreateRecipeActivity.this, "Pleas Add The Recipe Steps", Toast.LENGTH_LONG);   
					toast.show();
				}
				else{
					mrecipe.setName(enter_recipe_name.getText().toString());
					mrecipe.set_download_upload_own(1);
					dB_LocalDatabaseManager.open();
					ArrayList<Ingredient> db_input_ingredients = mrecipe.getIngredients();
					ArrayList<Image> db_input_images = mrecipe.getImages();
					Step db_input_steps = mrecipe.getSteps();
					dB_LocalDatabaseManager.add_recipe(mrecipe);
					int i;
					for (i = 0 ; i < db_input_ingredients.size(); i++ ){
						dB_LocalDatabaseManager.add_ingrdient(mrecipe.getIngredients().get(i));
					}
					
						dB_LocalDatabaseManager.add_step(db_input_steps);
					for (i = 0 ; i < db_input_images.size(); i++ ){
						dB_LocalDatabaseManager.add_image(mrecipe.getImages().get(i));
					}
					dB_LocalDatabaseManager.close();
					enter_recipe_name.setText(mrecipe.getID());
					Intent intent = new Intent();
					intent.setClass(CreateRecipeActivity.this, MainPageActivity.class);
					/**
					 * start a add entry activity
					 */
					startActivity(intent);
					
					/**
					 *  close the old activity
					 */
					CreateRecipeActivity.this.finish(); 
				} 
			}
		});	
		/**
		 * Link to the ModifyImageActivity
		 */
		Button add_modify_photo = (Button)findViewById(R.id.photo);
		add_modify_photo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
				mrecipe.setName(enter_recipe_name.getText().toString());
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, ModifyImageActivity.class);			
				Bundle mbundle = new Bundle();
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
			
			}
		});
		
		/**
		 * Link to the ModifyIngredientsActivity
		 */
		Button add_modify_ingredients = (Button)findViewById(R.id.Ingredients);
		add_modify_ingredients.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//TO BE IMPLEMENTED
				mrecipe.setName(enter_recipe_name.getText().toString());
				Intent intent = new Intent();
				intent.setClass(CreateRecipeActivity.this, ModifyIngredientsActivity.class); 
				Bundle mbundle = new Bundle();
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				
			
			}
		});	
		
		/**
		 * Link to the ModifyStepsActivity 
		 */
		Button add_modify_description = (Button)findViewById(R.id.description);
		add_modify_description.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
				mrecipe.setName(enter_recipe_name.getText().toString());
				Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				intent.setClass(CreateRecipeActivity.this, ModifyStepsActivity.class);				
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);

			
			}
		});	
		
	}
	

	

}