package com.example.easycooking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.content.Context;
//import android.view.Menu;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * setBogoPic() and saveFile() used project BogoPicGenActivity as reference
 * download address https://github.com/abramhindle/BogoPicGen
 * author: Dr.Abram Hindle
 * @author HongZu
 *
 */
public class ModifyImageActivity extends Activity {
	private Bitmap ourBMP;
	private static Recipe mrecipe = new Recipe();
	private static Image rimages = new Image();
	private static ArrayList<Image> image_obj_list = new ArrayList<Image>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyimage);
		Button takephoto = (Button)findViewById(R.id.takephoto);
		final Button accept = (Button)findViewById(R.id.accept);
		Button continue_to = (Button)findViewById(R.id.continue_to);
		accept.setEnabled(false);
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		image_obj_list = mrecipe.getImages();
		
		if(image_obj_list.size()>0) {
			displayImage();
		}
        
        takephoto.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	setBogoPic();
            	accept.setEnabled(true);
            }
        }); 
        
        accept.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	saveFile();
            }
           
        }); 
        
        continue_to.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	mrecipe.setImages(image_obj_list);
            	Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				intent.setClass(ModifyImageActivity.this, CreateRecipeActivity.class);
				mbundle.putString("FromWhere", "MODIFY");
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				finish();
            }
        }); 
    }


    private void setBogoPic() {
    	Toast.makeText(this, "Generating Photo", Toast.LENGTH_LONG).show();
    	ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
		ourBMP = GeneratePhoto.generateBitmap(400, 400);
		button.setImageBitmap(ourBMP);
	}
    private void displayImage() {
    	//FileInputStream fis = new FileInputStream(image_obj_list.get(0).get_imageUri());
    	ourBMP = BitmapFactory.decodeFile(image_obj_list.get(0).get_imageUri());
    	ImageButton old_photo = (ImageButton)findViewById(R.id.imageButton1);
    	old_photo.setImageBitmap(ourBMP);
    }
   /* private File getPicturePath(Intent intent) {
        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return new File(uri.getPath());
    }	*/
    private void saveFile() {
    	try { 
    		File file = new File("/data/data/com.example.easycooking/localimages/");
    		if(!file.exists()) {
    			file.mkdirs();
    		}
    		image_obj_list.clear();
    		FileOutputStream fos = null;
    		String pic_date = Long.toString(System.currentTimeMillis());
    		String image_uri = "/data/data/com.example.easycooking/localimages/"+pic_date+".JPEG";
    		fos = new FileOutputStream(image_uri);
    		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, fos);
    		fos.close();
    		rimages.set_IMAGE_ID(pic_date);
    		rimages.set_image_belongto("1");//mrecipe.getID());
    		rimages.set_imageUri(image_uri);
    		image_obj_list.add(rimages);
    		Toast.makeText(this, "Image Saved", Toast.LENGTH_LONG).show();
    	} catch (FileNotFoundException e) {
    		Toast.makeText(this, "Couldn't Find File to Write to?", Toast.LENGTH_LONG).show();
    	} catch (IOException e) {
    		Toast.makeText(this, "Couldn't Write File!", Toast.LENGTH_LONG).show();
    	}
    }
    /*private void saveBMP( File FileName, Bitmap ourBMP) throws IOException, FileNotFoundException {
		OutputStream out = new FileOutputStream(FileName);
		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, out);
		out.close();
    }*/
}
