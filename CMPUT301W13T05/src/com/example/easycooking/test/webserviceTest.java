package com.example.easycooking.test;

import static org.junit.Assert.*;
package com.example.easycooking.test;

import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import com.example.easycooking.controller.WEBClient;
import com.example.easycooking.model.*;
import org.apache.http.client.ClientProtocolException;

public class webserviceTest  extends TestCase {
 
	
	public Recipe recipe;
	
	public void testWebServiceGetTask() {
	    	
		      WEBClient webclient = null;
		      ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
		      String[] keywords = new String []{ "pizza"};
		        
	    	//create new recipe and add to webservice
			  java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>(1);
	          java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>(2);
	          Step step = new Step();
	          recipe = new Recipe("12345","pizza",v2,v1,step,0);  
	          
	         try {
				webclient.UploadRecipe(recipe);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //get recipe with same id from webservice
	         Recipe newRecipe = null;
			try {
				newRecipe = webclient.DisplayRecipe();
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
