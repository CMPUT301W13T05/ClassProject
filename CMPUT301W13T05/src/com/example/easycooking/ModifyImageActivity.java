package com.example.easycooking;

import java.io.File;

import com.example.easycooking.controller.*;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyimage);
        
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


}
