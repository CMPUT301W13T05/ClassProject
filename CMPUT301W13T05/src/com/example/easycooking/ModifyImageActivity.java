package com.example.easycooking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.easycooking.controller.*;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * setBogoPic() and getPicturePath(Intent intent)
 * are took from project BogoPicGenActivity
 * download address https://github.com/abramhindle/BogoPicGen
 * author: Dr.Abram Hindle
 * @author HongZu
 *
 */
public class ModifyImageActivity extends Activity {
	private static Recipe mrecipe = new Recipe();
	private static ArrayList<String> image_list = new ArrayList<String>();
	private static ArrayList<Image> image_obj_list = new ArrayList<Image>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyimage);
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		image_obj_list = mrecipe.getImages();
		
		/** Display old photos*/
    	ImageButton old_photo = (ImageButton)findViewById(R.id.imageButton1);
    	old_photo.setImageBitmap(ourBMP);
		
        Button takephoto = (Button)findViewById(R.id.takephoto);
        takephoto.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	setBogoPic();
            }
        }); 
        Button accept = (Button)findViewById(R.id.accept);
        accept.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
               //TO DO
            }
        }); 
        Button continue_to = (Button)findViewById(R.id.continue_to);
        continue_to.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
               //TO DO
            }
        }); 
    }


    private Bitmap ourBMP;
    private void setBogoPic() {
    	Toast.makeText(this, "Generating Photo", Toast.LENGTH_LONG).show();
    	ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
		ourBMP = GeneratePhoto.generateBitmap(400, 400);
		button.setImageBitmap(ourBMP);
	}
    private File getPicturePath(Intent intent) {
        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return new File(uri.getPath());
    }	
    private void processIntent() {
    	Intent intent = getIntent();
    	if (intent == null) {
    		return;
    	}
    	try {
    		if (intent.getExtras() != null) {    
    			File intentPicture = getPicturePath(intent);
    			saveBMP(intentPicture, ourBMP);
    			setResult(RESULT_OK);
    		} else {
    			Toast.makeText(this, "Photo Cancelled: No Reciever?", Toast.LENGTH_LONG).show();
    			setResult(RESULT_CANCELED);
    		}
    	} catch (FileNotFoundException e) {
    		Toast.makeText(this, "Couldn't Find File to Write to?", Toast.LENGTH_LONG).show();
    		setResult(RESULT_CANCELED);    	
    	} catch (IOException e) {
    		Toast.makeText(this, "Couldn't Write File!", Toast.LENGTH_LONG).show();
    		setResult(RESULT_CANCELED);
    	}
    	finish();
    }
    private void saveBMP( File intentPicture, Bitmap ourBMP) throws IOException, FileNotFoundException {
		OutputStream out = new FileOutputStream(intentPicture);
		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, out);
		out.close();
    }
}
