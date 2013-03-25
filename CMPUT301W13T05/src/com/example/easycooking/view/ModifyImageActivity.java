package com.example.easycooking.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.easycooking.R;
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
 * setBogoPic() and saveFile() used project BogoPicGenActivity as reference download address https://github.com/abramhindle/BogoPicGen author: Dr.Abram Hindle
 * @author  HongZu
 */
public class ModifyImageActivity extends Activity {
	private Bitmap ourBMP;
	/**
	 * @uml.property  name="mrecipe"
	 * @uml.associationEnd  
	 */
	private static Recipe mrecipe = new Recipe();
	/**
	 * @uml.property  name="rimages"
	 * @uml.associationEnd  
	 */
	private Image rimages = new Image();
	private static ArrayList<Image> image_obj_list = new ArrayList<Image>();
	private static String _FROM_WHERE = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyimage);
		Button takephoto = (Button)findViewById(R.id.takephoto);
		final Button accept = (Button)findViewById(R.id.accept);
		Button continue_to = (Button)findViewById(R.id.continue_to);
		final ImageButton delete = (ImageButton)findViewById(R.id.imageButton1);
		accept.setEnabled(false);
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		image_obj_list = mrecipe.getImages();
    	final Toast toast = Toast.makeText(ModifyImageActivity.this, "Image Deleted", Toast.LENGTH_SHORT);   
		if(image_obj_list.size()>0) {
			displayImage();
		}
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
            	if(image_obj_list.isEmpty()) {}
            	else {
            		mrecipe.setImages(image_obj_list);
                	Intent intent = new Intent();
    				Bundle mbundle = new Bundle();
    				int i;
                	for (i=0 ; i<mrecipe.getImages().size();i++){
                		System.out.println("the size="+mrecipe.getImages().size()+"||"+mrecipe.getImages().get(i).get_imageUri());
                	}
    				intent.setClass(ModifyImageActivity.this, DisplayModifyImageActivity.class);
    				mbundle.putSerializable("RECIPE_KEY", mrecipe);
    				intent.putExtras(mbundle);
    				startActivity(intent);
//		        	delete.setImageBitmap(null);
//		        	image_obj_list.remove(image_obj_list.size()-1);
//		        	toast.show();
//		        	if(image_obj_list.isEmpty()) {}
//		        	else {
//		        		ourBMP = BitmapFactory.decodeFile(image_obj_list.get(image_obj_list.size()-1).get_imageUri());
//		        		delete.setImageBitmap(ourBMP);
//		        		}
            		
            	}
            	/** jump to show all images */
            	accept.setEnabled(false);
            }
        }; 
        delete.setOnClickListener(listener);

        takephoto.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	setBogoPic();
            	accept.setEnabled(true);
            	delete.setEnabled(false);
            }
        }); 
        
        accept.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	accept.setEnabled(false);
            	saveFile();
            	delete.setEnabled(true);
            }
           
        }); 
        
        continue_to.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	mrecipe.setImages(image_obj_list);
            	
            	Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				intent.setClass(ModifyImageActivity.this, CreateRecipeActivity.class);
				mbundle.putString("FromWhere", _FROM_WHERE);
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				finish();
            }
        }); 
    }


    private void setBogoPic() {
    	Toast.makeText(this, "Generating Photo", Toast.LENGTH_SHORT).show();
    	ImageButton button = (ImageButton)findViewById(R.id.imageButton1);
		ourBMP = GeneratePhoto.generateBitmap(400, 400);
		button.setImageBitmap(ourBMP);
	}
    private void displayImage() {
    	//FileInputStream fis = new FileInputStream(image_obj_list.get(0).get_imageUri());
    	ourBMP = BitmapFactory.decodeFile(image_obj_list.get(image_obj_list.size()-1).get_imageUri());
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
    		FileOutputStream fos = null;
    		String pic_date = Long.toString(System.currentTimeMillis());
    		String image_uri = "/data/data/com.example.easycooking/localimages/"+pic_date+".JPEG";
    		System.out.println(pic_date);
    		fos = new FileOutputStream(image_uri);
    		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, fos);
    		fos.close();
    		rimages.set_IMAGE_ID(pic_date);
    		rimages.set_image_belongto(mrecipe.getID());
    		rimages.set_imageUri(image_uri);
    		image_obj_list.add(rimages);
    		rimages = new Image();
    		Toast.makeText(this, "Image Saved\nClick Image to Delete", Toast.LENGTH_SHORT).show();
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
