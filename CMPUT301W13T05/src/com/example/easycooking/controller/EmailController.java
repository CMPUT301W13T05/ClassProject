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
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.easycooking.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	 public Recipe downloadFromEmail(String path) throws IOException{
    	 FileInputStream stream = new FileInputStream(new File(path));
    	  try {
    	    FileChannel fc = stream.getChannel();
    	    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    	    /* Instead of using default, pass in a decoder. */
    	    String json =  Charset.defaultCharset().decode(bb).toString();
    	    // We have to tell GSON what type we expect
    	    Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Recipe>>(){}.getType();
    	 	// Now we expect to get a Recipe response
    	 	ElasticSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchResponseType);
    	 	// We get the recipe from it!
    	 	Recipe download_recipe = esResponse.getSource();
    	 	return download_recipe;
    	  }
    	  finally {
    	    stream.close();
    	  }
    }
}
