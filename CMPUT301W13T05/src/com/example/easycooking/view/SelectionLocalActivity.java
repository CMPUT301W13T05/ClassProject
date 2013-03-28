package com.example.easycooking.view;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.controller.ImageAdapter;
import com.example.easycooking.controller.WEBClient;
import com.example.easycooking.model.GalleryFlow;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore.Images;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
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
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		myapp = (MyApp)getApplication();
		/**
		 * Set up mode
		 */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
        .detectDiskReads()       
        .detectDiskWrites()       
        .detectNetwork()   // or .detectAll() for all detectable problems       
        .penaltyLog()       
        .build());       
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()       
        .detectLeakedSqlLiteObjects()    
        .penaltyLog()       
        .penaltyDeath()       
        .build());    
		/**
		 * get the recipe object from the result view
		 */
		mrecipe = myapp.get_mrecipe();
//		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		/**
		 * set up all the text_view 
		 */
		TextView dish_name = (TextView)findViewById(R.id.text_dishname);
		TextView dish_ingredients = (TextView)findViewById(R.id.text_ingredients);
		TextView dish_steps = (TextView)findViewById(R.id.text_steps);
		dish_name.setText("DishName£º"+mrecipe.getName());
		String temp_string = "";
		int i = 0;
		for (i=0 ; i < mrecipe.getIngredients().size(); i++){
			temp_string += mrecipe.getIngredients().get(i).get_amount()+":"+mrecipe.getIngredients().get(i).get_name()+"|";
		}
		dish_ingredients.setText(temp_string);
		dish_ingredients.setMovementMethod(ScrollingMovementMethod.getInstance());
		dish_steps.setText(mrecipe.getSteps().get_detail());
		dish_steps.setMovementMethod(ScrollingMovementMethod.getInstance()); 
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
	    if (mrecipe.getImages().size()==0){
	    	System.out.println("No image herr");
			//galleryFlow.setBackgroundDrawable(R.drawable.noimage);
		}
		/**
		 * button share
		 */
	    System.out.println("run here");
		Button selection_share = (Button)findViewById(R.id.share);
		
		selection_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		//TODO
	        	}
	        });
		
		//button modify
		Button selection_modify = (Button)findViewById(R.id.modify);
		selection_modify.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					myapp.setRecipe(mrecipe);
			        Intent intent = new Intent();
			        intent.setClass(SelectionLocalActivity.this, CreateRecipeActivity.class);// should change to the RecipeDetails.java
			        //start a add entry activity
			        Bundle mbundle = new Bundle();
			        mbundle.putString("FromWhere", "SELECTION");
					//mbundle.putSerializable("RECIPE_KEY", mrecipe);
					intent.putExtras(mbundle);
					
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
					Recipe try_recipe = initializeRecipe();
					WEBClient my_client = new WEBClient();
					try {
						my_client.UploadRecipe(mrecipe);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(try_recipe.getID());
					
			}
			});


	}
	private Recipe initializeRecipe() {
		Recipe r = new Recipe();
		Ingredient in = new Ingredient();
		Step s = new Step();
		Image im = new Image();
		r.setID("999");
		r.setName("Fried egg");
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		in.set_name("egg");
		in.set_amount("1");
		in.set_belongto("999");
		ingredients.add(in);
		r.setIngredients(ingredients);
		s.set_belong("999");
		s.set_detail("fry the egg");
		r.setSteps(s);
		r.setID("1");
		ArrayList<Image> images = new ArrayList<Image>();
		im.set_imageUri("0101111011100011010111101110001101011110111000110101111011100011");
		im.set_image_belongto("999");
		im.set_IMAGE_ID("1");
		images.add(im);
		r.setImages(images);
		return r;
	}
	@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // TODO Auto-generated method stub  
        menu.add(0, 0, 0, "Share");  
        menu.add(0, 1, 1, "Upload");
        menu.add(0, 2, 0, "Modify");
        menu.add(0, 3, 1, "Delete");
        return super.onCreateOptionsMenu(menu);  
    }  
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        // TODO Auto-generated method stub  
        switch (item.getItemId()) {  
        case 0:
        	/**
        	 * TODO SHARE 
        	 */
        	break;
        case 1:
			WEBClient my_client = new WEBClient();
			try {
				my_client.UploadRecipe(mrecipe);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Uploaded");
        	break;
        case 2:  
        	myapp.setRecipe(mrecipe);
	        Intent intent = new Intent();
	        intent.setClass(SelectionLocalActivity.this, CreateRecipeActivity.class);// should change to the RecipeDetails.java
	        //start a add entry activity
	        Bundle mbundle = new Bundle();
	        mbundle.putString("FromWhere", "SELECTION");
			//mbundle.putSerializable("RECIPE_KEY", mrecipe);
			intent.putExtras(mbundle);
			
	        startActivity(intent);
	        //close the old activity
	        SelectionLocalActivity.this.finish(); 
            break;  
        case 3:
        	DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(this);
        	dB_LocalDatabaseManager.delete_recipe(mrecipe);
        	dB_LocalDatabaseManager.close();
        	Intent intent2 = new Intent();
        	intent2.setClass(SelectionLocalActivity.this, MainPageActivity.class);
        	startActivity(intent2);
	        //close the old activity
	        SelectionLocalActivity.this.finish(); 
        	break;
        }  
        return super.onOptionsItemSelected(item);  
    }  
	

}
