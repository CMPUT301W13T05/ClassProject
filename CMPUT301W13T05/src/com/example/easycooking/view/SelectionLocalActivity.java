package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.controller.ImageAdapter;
import com.example.easycooking.model.GalleryFlow;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;
/**
 * TODO Implemented
 * @author Alvin
 *
 */
public class SelectionLocalActivity extends Activity {
	private static Recipe mrecipe = new Recipe();
	private static ArrayList<Image> image_list = new ArrayList<Image>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		/**
		 * get the recipe object from the result view
		 */
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		/**
		 * set up all the text_view 
		 */
		TextView dish_name = (TextView)findViewById(R.id.text_dishname);
		TextView dish_ingredients = (TextView)findViewById(R.id.text_ingredients);
		TextView dish_steps = (TextView)findViewById(R.id.text_steps);
		dish_name.setText("DishName£º"+mrecipe.getName());
		String temp_string = "";
		int i = 0;
		for (i=1 ; i < mrecipe.getIngredients().size(); i++){
			temp_string += mrecipe.getIngredients().get(i).get_amount()+":"+mrecipe.getIngredients().get(i).get_name()+"|";
		}
		dish_ingredients.setText(temp_string);
		dish_steps.setText(mrecipe.getSteps().get_detail());
		/**
		 * set up gallery
		 */
		image_list = mrecipe.getImages();
		final ImageAdapter adapter = new ImageAdapter(SelectionLocalActivity.this, image_list); 
		adapter.createReflectedImages();
		GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.galleryFlow1);     
	    galleryFlow.setFadingEdgeLength(0);     
	    galleryFlow.setSpacing(-100); 
	    galleryFlow.setAdapter(adapter);   
	    galleryFlow.setSelection(0);  
		/**
		 * button share
		 */
		Button selection_share = (Button)findViewById(R.id.search);
		selection_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(SelectionLocalActivity.this, Itisatry.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		SelectionLocalActivity.this.finish();
	        	}
	        });
		
		//button modify
		Button selection_modify = (Button)findViewById(R.id.modify);
		selection_modify.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
			        Intent intent = new Intent();
			        intent.setClass(SelectionLocalActivity.this, Itisatry.class);// should change to the RecipeDetails.java
			        //start a add entry activity
			        startActivity(intent);
			        //close the old activity
			        SelectionLocalActivity.this.finish();
			      }
			});
		//button delete
		Button selection_delete = (Button)findViewById(R.id.delete);
		selection_delete.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					//TO BE IMPLEMENTED
			      }
			});
		//button upload
		Button selection_upload = (Button)findViewById(R.id.upload);
		selection_upload.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					//TO BE IMPLEMENTED
			      }
			});


	}

	

}
