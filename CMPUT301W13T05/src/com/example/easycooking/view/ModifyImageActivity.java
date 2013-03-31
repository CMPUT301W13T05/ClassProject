package com.example.easycooking.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.*;
import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
//import android.view.Menu;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;

/**
 * The main goal of this activity is to take photos and display them
 * In this page, user have choice of taking photo and saving photo
 * if user wants to take photo, activity will jump the the camera App which is build in android system
 * After taking photo, user have the choice to save, retake and cancel.
 * If user choose ok, image will be saved, retake to re-activate camera, cancel to cancel and go back to App.
 * Also, in the this page, the most recent image will be displayed on a image button.
 * @author  HongZu
 */
public class ModifyImageActivity extends Activity {
	private Bitmap ourBMP;
	Uri imageFileUri;
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
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyimage);
		Button takephoto = (Button)findViewById(R.id.takephoto);
		Button continue_to = (Button)findViewById(R.id.continue_to);
		final ImageButton delete = (ImageButton)findViewById(R.id.imageButton1);
		myapp = (MyApp)getApplication();
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		mrecipe = myapp.get_mrecipe();
		image_obj_list = mrecipe.getImages();
		if(image_obj_list.size()>0) {
			ourBMP = stringtoBitmap(image_obj_list.get(image_obj_list.size()-1).get_imageUri());
			displayImage(ourBMP);
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
    				mbundle.putString("FromWhere", _FROM_WHERE);
    				myapp.setRecipe(mrecipe);
    				intent.putExtras(mbundle);
    				startActivity(intent);
            		
            	}
            	/** jump to show all images */
            }
        }; 
        delete.setOnClickListener(listener);

        takephoto.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		//TODO Camera implement
        		takeAPhoto();
            }
           
        }); 

        
        continue_to.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	mrecipe.setImages(image_obj_list);
            	System.out.println(mrecipe.getImages().size());
            	Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				intent.setClass(ModifyImageActivity.this, CreateRecipeActivity.class);
				mbundle.putString("FromWhere", _FROM_WHERE);
				myapp.setRecipe(mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				finish();
            }
        }); 
    }

	/**
	 * displayImage() receives a bitmap and sets it on the image button
	 * @param originalImage
	 */
    private void displayImage(Bitmap originalImage) {
    	ImageButton old_photo = (ImageButton)findViewById(R.id.imageButton1);
    	old_photo.setImageBitmap(originalImage);
    }
    /**
     * bitmaptoString receives a bitmap and convert it to base64 string
     * then return the string
     * @param bitmap
     * @return base64 string
     */
    public String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 75, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
    /**
     * stringtoBitmap() receives a string and convert it to a bitmap
     * @param string
     * @return bitmap
     */
    public Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
                byte[] bitmapArray;
                bitmapArray = Base64.decode(string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                                bitmapArray.length);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return bitmap;
    }
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    /**
     * takePhoto() activate the camera App
     */
    public void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/localimage";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
		String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis())+".JPEG";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    /**
     * This operation receives the response of the users after taking photo
     * and do the matching operation, then display the image
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	            	
            	float imagew = 300;
            	float imageh = 300;
            	 
            	BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            	bitmapFactoryOptions.inJustDecodeBounds = true;
            	ourBMP = BitmapFactory.decodeFile(imageFileUri.getPath(), bitmapFactoryOptions);
            	 
            	int yRatio = (int)Math.ceil(bitmapFactoryOptions.outHeight/imageh);
            	int xRatio = (int)Math.ceil(bitmapFactoryOptions.outWidth/imagew);
            	 
            	if (yRatio > 1 || xRatio > 1){
            	 if (yRatio > xRatio) {
            	  bitmapFactoryOptions.inSampleSize = yRatio;
            	  Toast.makeText(this,
            	    "yRatio = " + String.valueOf(yRatio),
            	    Toast.LENGTH_LONG).show();
            	 }
            	 else {
            	  bitmapFactoryOptions.inSampleSize = xRatio;
            	  Toast.makeText(this,
            	    "xRatio = " + String.valueOf(xRatio),
            	    Toast.LENGTH_LONG).show();
            	 }
            	}
            	else{
            	 Toast.makeText(this,
            	   "inSampleSize = 1",
            	   Toast.LENGTH_LONG).show();
            	}
            	 
            	bitmapFactoryOptions.inJustDecodeBounds = false;
            	ourBMP = BitmapFactory.decodeFile(imageFileUri.getPath(), bitmapFactoryOptions);
            
            	rimages.set_IMAGE_ID(imageFileUri.getPath());
        		rimages.set_image_belongto(mrecipe.getID());
        		rimages.set_imageUri(bitmaptoString(ourBMP));
        		image_obj_list.add(rimages);
            	displayImage(ourBMP);
                rimages = new Image();
                Toast.makeText(this, "Image Saved\nClick Image to view all images", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
            	rimages = new Image();
            	Toast.makeText(this, "Image cancelled", Toast.LENGTH_SHORT).show();
            } else {
            }
        }
    }


	
}



