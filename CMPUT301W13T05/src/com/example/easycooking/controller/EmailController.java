package com.example.easycooking.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.easycooking.model.*;
import com.google.gson.Gson;

public class EmailController {
	private Gson gson = new Gson();
	Uri jsonFileUri;
    public void sentRecipe(Recipe recipe){
    	String Json = gson.toJson(recipe);
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sendrecipes";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
        String jsonFilePath = folder + "/" + String.valueOf(System.currentTimeMillis())+".json";
		File jsonFile = new File(jsonFilePath);
		jsonFileUri = Uri.fromFile(jsonFile);
		intent.putExtra(Intent.EXTRA_STREAM, jsonFileUri.getPath());
		intent.setType("application/json");
		Intent.createChooser(intent, "Choose Email Client");
		startActivity(intent);

    }
    private void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		
	}
	public void attachImages(Image image){
    	
    }
    public void downloadFromEmail(){
    	
    }
}
