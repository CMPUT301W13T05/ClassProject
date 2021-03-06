package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.ImageAdapter;
import com.example.easycooking.model.GalleryFlow;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

/**
 * This activity contains a galleryflow which is rebuild by 
 * myself. Users can review all the pictures here with the 3d View
 * and can chosen by single click the image and can delete it 
 * finally users can save the modification
 * @author Adonis
 *
 */
public class DisplayModifyImageActivity extends Activity {
	private static Recipe mrecipe = new Recipe();
	private static String _FROM_WHERE = "";
	private static ArrayList<Image> image_list = new ArrayList<Image>();
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_gallery);
		myapp = (MyApp)getApplication();
		/**
		 * get the recipe obj and pull out the image array list 
		 */
		//mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		mrecipe = myapp.get_mrecipe();
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		image_list = mrecipe.getImages();
		/**
		 * set up the button and adapter and the gallery view
		 */
		final ImageButton delete_button = (ImageButton) this.findViewById(R.id.imageButton1);
		final ImageButton save_button = (ImageButton) this.findViewById(R.id.imageSave);
		final ImageAdapter adapter = new ImageAdapter(DisplayModifyImageActivity.this, image_list); 
		adapter.createReflectedImages();
		delete_button.setEnabled(false);
		GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.galleryFlow1);     
	    galleryFlow.setFadingEdgeLength(0);     
	    galleryFlow.setSpacing(-100); 
	    galleryFlow.setAdapter(adapter);     
	    galleryFlow.setOnItemClickListener(new OnItemClickListener() {     
	            public void onItemClick(AdapterView<?> parent, View view,     
	                    final int position, long id) {     
	                	Toast.makeText(getApplicationContext(), String.valueOf("Click Delete Button To Delete pistiton: "+ position), Toast.LENGTH_SHORT).show();  
	                	delete_button.setEnabled(true);
	                	delete_button.setOnClickListener(new ImageButton.OnClickListener(){
	                		public void onClick(View v) {
	                			
	                			/**
	                			 * check he size of the image_list we do
	                			 * not allow to delete the last image
	                			 *
	                			 */
	                			if (image_list.size()==0){
	                				//Do nothing
	                				Toast.makeText(getApplicationContext(), String.valueOf("Empty Gallery"), Toast.LENGTH_SHORT).show();  
	                			}
	                			else{
		                			image_list.remove(position);
		                			
		                			adapter.notifyDataSetChanged();
		                			adapter.createReflectedImages();
		                			delete_button.setEnabled(false);
	                			}
	                		}
	                		
	                	});
	            }     
	                 
	        });     
	    galleryFlow.setSelection(0);   
	    /**
	     * Save the new recipe obj and pass to the modifyImage activity
	     */
	    save_button.setOnClickListener(new ImageButton.OnClickListener(){
    		public void onClick(View v) { 
    			mrecipe.setImages(image_list);
    			Intent intent = new Intent();
				intent.setClass(DisplayModifyImageActivity.this, ModifyImageActivity.class);
    			/**
				 * start a add entry activity
				 */
				Bundle mbundle = new Bundle();
				//mbundle.putSerializable("RECIPE_KEY", mrecipe);
				myapp.setRecipe(mrecipe);
				mbundle.putString("FromWhere", _FROM_WHERE);
				intent.putExtras(mbundle);
				startActivity(intent);
				/**
				 * close the old activity
				 */
				DisplayModifyImageActivity.this.finish();
    		}	
	    });
	    }     
	}

	

