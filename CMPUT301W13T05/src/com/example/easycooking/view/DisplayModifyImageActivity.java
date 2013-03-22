package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
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
/**
 * This is a test activity
 * @author Alvin Sun
 *
 */

public class DisplayModifyImageActivity extends Activity {
	private Recipe mrecipe = new Recipe();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_gallery);
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		final ImageButton delete_button = (ImageButton) this.findViewById(R.id.imageButton1);
		final ImageAdapter adapter = new ImageAdapter(this, mrecipe.getImages()); 
		adapter.createReflectedImages();
		GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.galleryFlow1);     
	    galleryFlow.setFadingEdgeLength(0);     
	    galleryFlow.setSpacing(-100); 
	    galleryFlow.setAdapter(adapter);     
	    galleryFlow.setOnItemClickListener(new OnItemClickListener() {     
	            public void onItemClick(AdapterView<?> parent, View view,     
	                    final int position, long id) {     
	                	Toast.makeText(getApplicationContext(), String.valueOf("Click Button To Delete"), Toast.LENGTH_SHORT).show();  
	                	
	                	delete_button.setOnClickListener(new ImageButton.OnClickListener(){
	                		public void onClick(View v) {
	                			mrecipe.getImages().remove(position);
	                			adapter.notifyDataSetChanged();
	                		}
	                		
	                	});
	            }     
	                 
	        });     
	        galleryFlow.setSelection(0);     
	    }     
	}

	

