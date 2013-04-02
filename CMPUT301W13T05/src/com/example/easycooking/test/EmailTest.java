package com.example.easycooking.test;
/**
 * This class is testing the email function
 * @author Hongzu
 *
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.example.easycooking.controller.EmailController;
import com.example.easycooking.model. *;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest extends TestCase{
	
	final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sendrecipes";
	EmailController ec = new EmailController();
	ArrayList<Image> v2 = new ArrayList<Image>();
	ArrayList<Ingredient> v1 = new ArrayList<Ingredient>();
	Step step = new Step();
	
	@Test
	/**
	 * Checking if the activity is getting the correct intent.
	 */
	public void tryemail(){
		Intent intent = null;
		Recipe recipe = new Recipe("emailrecipe","pizza",v2,v1,step,0);
		try {
			intent = ec.sentRecipe(recipe);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Uri uri = intent.getData();
		assertEquals(path,uri.getPath());
	}
	
}	
	
