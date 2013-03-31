package com.example.easycooking.view;

import java.io.IOException;
import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.controller.EmailController;
import com.example.easycooking.controller.ImageAdapter;
import com.example.easycooking.controller.WEBClient;
import com.example.easycooking.model.GalleryFlow;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
//import android.view.Menu;
import android.content.Intent;
/**
 * TODO Implemented
 * @author Alvin
 *
 */
public class SelectionWebActivity extends Activity {

	private static Recipe mrecipe = new Recipe();
	private static ArrayList<Image> image_list = new ArrayList<Image>();
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection_web);
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
		/**
		 * set up all the text_view 
		 */
		ImageView no_image = (ImageView)findViewById(R.id.imageView1);
		TextView dish_name = (TextView)findViewById(R.id.text_dishname);
		TextView dish_ingredients = (TextView)findViewById(R.id.text_ingredients);
		TextView dish_steps = (TextView)findViewById(R.id.text_steps);
		dish_name.setText("DishName��"+mrecipe.getName());
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
		final ImageAdapter adapter = new ImageAdapter(SelectionWebActivity.this, image_list); 
		adapter.createReflectedImages();
		GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.galleryFlow1);     
	    galleryFlow.setFadingEdgeLength(0);     
	    galleryFlow.setSpacing(-100); 
	    galleryFlow.setAdapter(adapter);   
	    galleryFlow.setSelection(0);  
	    if (mrecipe.getImages().size()==0){
	    	no_image.setVisibility(0);
	    	no_image.setFocusable(true);
	    	System.out.println("No image herr");
			//galleryFlow.setBackgroundDrawable(R.drawable.noimage);
		}
	    else{
	    	no_image.setFocusable(false);
	    	no_image.setVisibility(100);
	    }
		/**
		 * button share
		 */
	    System.out.println("run here");

	}
    public boolean onPrepareOptionsMenu(Menu menu) {  
        // TODO Auto-generated method stub  
    	menu.clear();
    	menu.add(0, 0, 0, "Share");
    	if (mrecipe.get_download_upload_own()==98){
    		menu.add(0, 1, 0, "Save It");
    	}
    	else{
    		 menu.add(0, 1, 0, "DownLoad");
    	}
          
       
        
        menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				//TOD share
				EmailController myEmail = new EmailController();
        		Intent data = null;
				try {
					data = myEmail.sentRecipe(mrecipe);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                startActivity(data); 

				return false;
			}
        	
        });
        menu.getItem(1).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				// TODO Check the Recipe whether has been download 
				DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(SelectionWebActivity.this);	        	
	        	mrecipe.set_download_upload_own(100);
	        	dB_LocalDatabaseManager.open();
	        	if (!dB_LocalDatabaseManager.inDB(mrecipe)){//check	        		
	        		dB_LocalDatabaseManager.add_recipe(mrecipe);
	        		int i;
	        		for (i = 0 ; i < mrecipe.getIngredients().size(); i++ ){
						dB_LocalDatabaseManager.add_ingrdient(mrecipe.getIngredients().get(i));
					}					
						dB_LocalDatabaseManager.add_step(mrecipe.getSteps());
					for (i = 0 ; i < mrecipe.getImages().size(); i++ ){
						dB_LocalDatabaseManager.add_image(mrecipe.getImages().get(i));
					}
	        		Toast.makeText(SelectionWebActivity.this, "Downloaded Success", Toast.LENGTH_SHORT).show();
	        	}
	        	else{
	        		Toast.makeText(SelectionWebActivity.this, "You have alreadly Downloaded", Toast.LENGTH_SHORT).show();
	        	}
	        	dB_LocalDatabaseManager.close();	       	
				return false;
			}
        	
        });
       
        return super.onCreateOptionsMenu(menu);
    }
}