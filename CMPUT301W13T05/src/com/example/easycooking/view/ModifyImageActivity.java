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
 * takeAphoto() function is took from Dr. Abram Hindle's code
 * https://github.com/coniewt/CMPUT301W13T12/blob/master/C301W13T12Recipes/src/ca/ualberta/c301w13t12recipes/view/AddPicWizardActivity.java
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
		//Button generate = (Button)findViewById(R.id.generate);
		Button takephoto = (Button)findViewById(R.id.takephoto);
		//final Button accept = (Button)findViewById(R.id.accept);
		Button continue_to = (Button)findViewById(R.id.continue_to);
		final ImageButton delete = (ImageButton)findViewById(R.id.imageButton1);
		//accept.setEnabled(false);
		myapp = (MyApp)getApplication();
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		//mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		mrecipe = myapp.get_mrecipe();
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
    				mbundle.putString("FromWhere", _FROM_WHERE);
    				//mbundle.putSerializable("RECIPE_KEY", mrecipe);
    				myapp.setRecipe(mrecipe);
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
            	//accept.setEnabled(false);
            }
        }; 
        delete.setOnClickListener(listener);
        /*
         * generate.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		//TODO Camera implement
        		setBogoPic();
            	accept.setEnabled(true);
            	delete.setEnabled(false);
            }
        });*/
        takephoto.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		//TODO Camera implement
        		takeAPhoto();
            }
           
        }); 
        /*
        accept.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	accept.setEnabled(false);
            	saveFile();
            	delete.setEnabled(true);
            }
           
        }); */
        
        continue_to.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
            	mrecipe.setImages(image_obj_list);
            	System.out.println(mrecipe.getImages().size());
            	Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				intent.setClass(ModifyImageActivity.this, CreateRecipeActivity.class);
				mbundle.putString("FromWhere", _FROM_WHERE);
				//mbundle.putSerializable("RECIPE_KEY", mrecipe);
				myapp.setRecipe(mrecipe);
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
    	//ourBMP = BitmapFactory.decodeFile(image_obj_list.get(image_obj_list.size()-1).get_imageUri());
    	ourBMP = stringtoBitmap(image_obj_list.get(image_obj_list.size()-1).get_imageUri());
    	ImageButton old_photo = (ImageButton)findViewById(R.id.imageButton1);
    	old_photo.setImageBitmap(ourBMP);
    }
   /* private File getPicturePath(Intent intent) {
        Uri uri = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        return new File(uri.getPath());
    }	*/
    private void saveFile() {
//    	try { 
//    		File file = new File("/data/data/com.example.easycooking/localimages/");
//    		if(!file.exists()) {
//    			file.mkdirs();
//    		}
//    		FileOutputStream fos = null;
    		String pic_date = Long.toString(System.currentTimeMillis());
//    		String image_uri = "/data/data/com.example.easycooking/localimages/"+pic_date+".JPEG";
//    		System.out.println(pic_date);
//    		fos = new FileOutputStream(image_uri);
//    		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, fos);
//    		fos.close();
    		String image_base64 = null;
    		image_base64 = bitmaptoString(ourBMP);
    		rimages.set_IMAGE_ID(pic_date);
    		rimages.set_image_belongto(mrecipe.getID());
    		rimages.set_imageUri(image_base64);
    		//System.out.println(image_base64);
    		image_obj_list.add(rimages);
    		rimages = new Image();
    		
//    	} catch (FileNotFoundException e) {
//    		Toast.makeText(this, "Couldn't Find File to Write to?", Toast.LENGTH_LONG).show();
//    	} catch (IOException e) {
//    		Toast.makeText(this, "Couldn't Write File!", Toast.LENGTH_LONG).show();
//    	}
    }
    /*private void saveBMP( File FileName, Bitmap ourBMP) throws IOException, FileNotFoundException {
		OutputStream out = new FileOutputStream(FileName);
		ourBMP.compress(Bitmap.CompressFormat.JPEG, 75, out);
		out.close();
    }*/
    public String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	ourBMP = BitmapFactory.decodeFile(imageFileUri.getPath());
            	rimages.set_IMAGE_ID(imageFileUri.getPath());
        		rimages.set_image_belongto(mrecipe.getID());
        		rimages.set_imageUri(bitmaptoString(ourBMP));
        		image_obj_list.add(rimages);
            	ImageButton take_photo = (ImageButton)findViewById(R.id.imageButton1);
            	take_photo.setImageBitmap(ourBMP);
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



