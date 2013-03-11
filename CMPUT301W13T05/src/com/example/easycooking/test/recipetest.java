package com.example.easycooking.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;
public class recipetest extends TestCase {
	
	


	// Test ID can not BE NULL
       @Test
	 public void testgetID(){
		    Recipe recipe = new Recipe();  
			assertNotNull("Recipe ID is empty", recipe.getID());
	  }
    // Test setName of the recipe
			@Test
			public void testgetName()
			{
		        Recipe recipe = new Recipe();  
		        recipe.setName("pizza");  
		        assertEquals("Name", "pizza", recipe.getName());
			}
	//Test set_download_upload_own and get_download_upload_own
           @Test
             public void testget_download_upload_own(){
        	   Recipe recipe = new Recipe();  
        	   recipe.set_download_upload_own(0);
        	   assertEquals("0", recipe.get_download_upload_own());        	   
           }
   //Test if there is no step
           @Test
             public void testgetSteps(){
        	   Recipe recipe = new Recipe();  
        	   recipe.set_download_upload_own(0);
        	   assertNotNull("Recipe steps is empty", recipe.getSteps());      	   
           }
    //Test if there is no image
           @Test
             public void testgetImages(){
        	   Recipe recipe = new Recipe();  
        	   
        	   assertNotNull("Recipe image is empty", recipe.getImages());      	   
           }
 
           
   //Test setSteps
           @Test
             public void teststeps(){
				java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step(1,"12345","test");
        		Recipe recipe = new Recipe("12345","pizza",v2,v1,step,0);              
        	   recipe.setSteps(step);
        	   assertEquals("1.test", recipe.getSteps());                     
            }
         
     //Test setimages
           @Test
             public void testimages(){
				java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step();
       		Recipe recipe = new Recipe("12345","pizza",v2,v1,step,0);              
       	   recipe.setImages(v2);
       	   assertEquals("1", recipe.getImages());                   
           }    
           
          
}		
  
	

		
 
