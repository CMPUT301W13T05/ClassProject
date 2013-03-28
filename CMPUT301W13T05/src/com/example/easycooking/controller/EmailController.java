package com.example.easycooking.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	public Intent sentRecipe(Recipe recipe) throws IOException{
    	String Json = gson.toJson(recipe);
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	
    	String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sendrecipes";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
       
        String jsonFilePath = folder + "/" +recipe.getName()+".json";
		File jsonFile = new File(jsonFilePath);
		FileWriter fw=new FileWriter(jsonFile);
	    BufferedWriter buffw=new BufferedWriter(fw);
	    PrintWriter pw=new PrintWriter(buffw);
	    fw.flush();
	    pw.write(Json);
	    pw.close();
	    buffw.close();
	    fw.close();
	    
		jsonFileUri = Uri.fromFile(jsonFile);
		System.out.println(jsonFileUri.getPath());
		
		intent.putExtra(Intent.EXTRA_STREAM, jsonFileUri);
		intent.putExtra(Intent.EXTRA_SUBJECT, recipe.getName());
		intent.putExtra(Intent.EXTRA_TEXT   , "");
		intent.setType("application/json");
		Intent.createChooser(intent, "Choose Email Client");
		return intent;
    } 
    public void downloadFromEmail(){
    	
    }
}
