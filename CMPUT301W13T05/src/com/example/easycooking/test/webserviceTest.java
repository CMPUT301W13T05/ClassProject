package com.example.easycooking.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import com.example.easycooking.controller.WEBClient;
import com.example.easycooking.model.*;
import org.apache.http.client.ClientProtocolException;

public class webserviceTest  extends TestCase {
 
	
	public Recipe recipe;
	
	public void testWebServiceGetTask() {
	    	
		      WEBClient webclient = new WEBClient();
		      ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
		      String[] keywords = new String []{ "pizza"};
		        
	    	//create new recipe and add to web server
			  java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>(1);
	          java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>(2);
	          Step step = new Step();
	          recipe = new Recipe("12345","pizza",v2,v1,step,0);  
	          String key[] = {"pizza"};
	          
	          
	         try {
				webclient.UploadRecipe(recipe);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //get recipe with same id from web server
	         Recipe newRecipe = null;
			try {
				newRecipe = webclient.searchRecipesWithName(key);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        //test to see if recipes are equal
	        assertEquals(recipe.getName(),newRecipe.getName());
	        assertTrue(recipe.getSteps().equals(newRecipe.getSteps()));
	        assertTrue(recipe.getIngredients().equals(newRecipe.getIngredients()));
	        
	        //delete recipe
	        try {
				webclient.deleteRecipe(newRecipe.getID());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				assertNull(webclient.searchRecipesWithName(keywords));
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

	    }
	


	} 
