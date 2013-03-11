package com.example.easycooking.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import com.example.easycooking.model.Image;
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
 
           /**
   //Test setSteps
           @Test
             public void teststeps(){
        	   Recipe recipe = new Recipe();
        	   java.util.ArrayList<Step>  v = new java.util.ArrayList<Step>();
               
        	   recipe.setSteps(v);
        	   assertEquals("0", recipe.getSteps);                     
           }
         
           //Test setSteps
           @Test
             public void testimages(){
        	   Recipe recipe = new Recipe();
        	   java.util.ArrayList<Image>  v = new java.util.ArrayList<Image>();              
        	   recipe.setImages(v);
        	   assertEquals("0", recipe.getImages);                     
           }    
           
      */     
}		
 
