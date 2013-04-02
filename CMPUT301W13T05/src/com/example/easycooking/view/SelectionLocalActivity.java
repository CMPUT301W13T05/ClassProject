package com.example.easycooking.view;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.controller.EmailController;
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
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
//import android.view.Menu;
import android.content.Intent;

/**
 * This activity is a local recipe details show
 * User can see all the images, ingredients, steps in this pages
 * On the menu users are allowed to modify this recipe,share this recipe,upload this recipe,
 * And delete this recipe
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

		/**
		 * set up all the text_view 
		 */
		ImageView no_image = (ImageView)findViewById(R.id.imageView1);
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
	    	no_image.setVisibility(0);
	    	no_image.setFocusable(true);
	    	System.out.println("No image herr");
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

	@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // TODO Auto-generated method stub  
        menu.add(0, 0, 0, "Share");  
        menu.add(0, 1, 0, "Upload");
        menu.add(0, 2, 0, "Modify");
        menu.add(0, 3, 0, "Delete");
        menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener(){
        	/**
        	 * link to the email app to share the .json file
        	 */
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				//TOD share
				EmailController myEmail = new EmailController();
				mrecipe.set_download_upload_own(101);
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
        /**
         * Upload this recipe
         */
        menu.getItem(1).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				if (mrecipe.get_download_upload_own() != 99){
					Toast.makeText(SelectionLocalActivity.this, "You cannot Upload Not Own Recipe", Toast.LENGTH_SHORT).show();
					return false;
				}
				mrecipe.set_download_upload_own(101);
				DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(SelectionLocalActivity.this);
	        	dB_LocalDatabaseManager.open();
	        	dB_LocalDatabaseManager.update_recipe(mrecipe);
	        	dB_LocalDatabaseManager.close();
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
				Toast.makeText(SelectionLocalActivity.this, "Success", Toast.LENGTH_SHORT).show();
				return false;
			}
        	
        });
        /**
         * Modify This Recipe
         */
        menu.getItem(2).setOnMenuItemClickListener(new OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
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
				return false;
			}
			
        });
        /**
         * Give a dialog confirm view
         * and delete the recipe from the database
         */
        menu.getItem(3).setOnMenuItemClickListener(new OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				/**
				 * set up dialog
				 */
				final Dialog confirm = new Dialog(SelectionLocalActivity.this);
				confirm.setContentView(R.layout.confirm_dialog);
				confirm.setTitle("Notice!");
				TextView confirm_text = (TextView)confirm.findViewById(R.id.confirm_text);
				confirm_text.setText("Do you confirm to delete this recipe?");
				Button confirm_yes = (Button)confirm.findViewById(R.id.Yes);
				Button confirm_no = (Button)confirm.findViewById(R.id.No);
				/**
				 * Confirm click
				 */
				confirm_yes.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(SelectionLocalActivity.this);
			        	dB_LocalDatabaseManager.open();
			        	dB_LocalDatabaseManager.delete_recipe(mrecipe);
			        	dB_LocalDatabaseManager.close();
			        	confirm.dismiss();
			        	Intent intent = new Intent();
			        	intent.setClass(SelectionLocalActivity.this, MainPageActivity.class);
			        	startActivity(intent);
				        SelectionLocalActivity.this.finish(); 
					}
				});
				/**
				 * cancle
				 */
				confirm_no.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						confirm.dismiss();
					}
				});
				confirm.show();
				return false;
			}
        	
        }); 
        return super.onCreateOptionsMenu(menu);
    }  
  
  

	

}
