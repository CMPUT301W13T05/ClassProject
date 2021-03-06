package com.example.easycooking.view;

import java.util.ArrayList;
import java.util.UUID;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
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
 * This is a CreateRcipeActivtiy View class That allow user to  creat a new recipe and upload image, 
 * ingredient and steps and insert them into database, and a object Recipe passed  
 * among activities. The control condition _FROM_WHERE is for checking whether this page
 * is used for modifying or creating a new recipe. The object recipe are get and set in the 
 * myapp variable.
 * @author  Alvin Sun 
 */

public class CreateRecipeActivity extends Activity {
	private static String _FROM_WHERE = "";
	private MyApp myapp;
	/**
	 * @uml.property  name="mrecipe"
	 * @uml.associationEnd  
	 */
	private static Recipe mrecipe = new Recipe();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		myapp = (MyApp)getApplication();
		/**
		 * Set up all the views widget
		 */
		final EditText enter_recipe_name = (EditText)findViewById(R.id.recipe_name);
		TextView count_updated =(TextView)findViewById(R.id.uploaded_view);
		TextView count_ingredients =(TextView)findViewById(R.id.ingredients_view);
		TextView count_steps =(TextView)findViewById(R.id.steps_view);
		Bundle mbundle = getIntent().getExtras();
		/**
		 * Check the condition and decide what should we do next
		 */
		_FROM_WHERE = mbundle.getString("FromWhere");
		System.out.println("==============="+_FROM_WHERE);
		if (_FROM_WHERE.equals("MAIN")){
			UUID uuid = UUID.randomUUID(); 
			mrecipe.setID(uuid.toString());
			mrecipe.setImages(new ArrayList<Image>());
			mrecipe.setIngredients(new ArrayList<Ingredient>());
			mrecipe.setSteps(new Step());
			count_steps.setText("No steps");
			_FROM_WHERE = "MODIFY";
		}
		else if (_FROM_WHERE.equals("MODIFY") || _FROM_WHERE.equals("SELECTION") ){
			mrecipe = myapp.get_mrecipe();
			
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
		Button add_modify_save = (Button)findViewById(R.id.recipe_save);
		add_modify_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (enter_recipe_name.getText().toString().equals("")){
					Toast toast = Toast.makeText(CreateRecipeActivity.this, "Pleas Enter The Recipe Name", Toast.LENGTH_LONG);   
					toast.show();
				}

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
					/**
					 * check the whether the  recipe is modified by a downloaded recipe
					 */
					if (mrecipe.get_download_upload_own() == 100){
						/**
						 * give the recipe the new UUID
						 */
						UUID uuid = UUID.randomUUID(); 
						mrecipe.setID(uuid.toString());
						int i;
						/**
						 * set all the images;steps;ingredients belong to 
						 */
						for (i=0;i<mrecipe.getImages().size();i++){
							mrecipe.getImages().get(i).set_image_belongto(mrecipe.getID());
						}
						for (i=0;i<mrecipe.getIngredients().size();i++){
							mrecipe.getIngredients().get(i).set_belongto(mrecipe.getID());
						}
						mrecipe.getSteps().set_belong(mrecipe.getID());
					}
					/**
					 * 99 mains the recipe is the own created 
					 */
					mrecipe.set_download_upload_own(99);
					dB_LocalDatabaseManager.open();
					ArrayList<Ingredient> db_input_ingredients = mrecipe.getIngredients();
					ArrayList<Image> db_input_images = mrecipe.getImages();
					Step db_input_steps = mrecipe.getSteps();
					/**
					 * If it is a recipe already in the db we need to update it
					 */
					int i;
					if (_FROM_WHERE.equals("SELECTION")){
						System.out.println("DELETING SELECTION");
						dB_LocalDatabaseManager.delete_recipe(mrecipe);
					}
					dB_LocalDatabaseManager.add_recipe(mrecipe);
					
					for (i = 0 ; i < db_input_ingredients.size(); i++ ){
						dB_LocalDatabaseManager.add_ingrdient(mrecipe.getIngredients().get(i));
					}
					
						dB_LocalDatabaseManager.add_step(db_input_steps);
					for (i = 0 ; i < db_input_images.size(); i++ ){
						dB_LocalDatabaseManager.add_image(mrecipe.getImages().get(i));
					}
					dB_LocalDatabaseManager.close();
					/**
					 * Test
					 * enter_recipe_name.setText(mrecipe.getID());
					 */
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
				mbundle.putString("FromWhere", _FROM_WHERE);
				myapp.setRecipe(mrecipe);
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
				mbundle.putString("FromWhere", _FROM_WHERE);
				myapp.setRecipe(mrecipe);
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
				mbundle.putString("FromWhere", _FROM_WHERE);
				myapp.setRecipe(mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);

			
			}
		});	
		
	}
	

	

}