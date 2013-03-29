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
import android.app.Activity;
import android.app.Dialog;
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
        menu.add(0, 1, 0, "Upload");
        menu.add(0, 2, 0, "Modify");
        menu.add(0, 3, 0, "Delete");
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
				mrecipe.set_download_upload_own(101);
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
				
				return false;
			}
        	
        });
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
				confirm_text.setText("Do you confirm to delete this reciep?");
				Button confirm_yes = (Button)confirm.findViewById(R.id.Yes);
				Button confirm_no = (Button)confirm.findViewById(R.id.No);
				confirm_yes.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						DatabaseManager dB_LocalDatabaseManager = DatabaseManager.getInstance(SelectionLocalActivity.this);
			        	dB_LocalDatabaseManager.open();
			        	dB_LocalDatabaseManager.delete_recipe(mrecipe);
			        	dB_LocalDatabaseManager.close();
			        	Intent intent = new Intent();
			        	intent.setClass(SelectionLocalActivity.this, MainPageActivity.class);
			        	startActivity(intent);
				        //close the old activity
				        SelectionLocalActivity.this.finish(); 
					}
				});
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
