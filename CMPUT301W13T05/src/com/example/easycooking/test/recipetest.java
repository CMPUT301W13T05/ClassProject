package com.example.easycooking.test;
/**
 * This class is testing the recipe model
 * @author Chenkun
 *
 */
import static org.junit.Assert.fail;

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
			assertNull(recipe.getID());
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
        	   assertEquals(0, recipe.get_download_upload_own());        	   
           }
   //Test if there is no step
           @Test
             public void testgetSteps(){
        	
        	   java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step();
				Recipe recipe = new Recipe("12345","pizza",v2,v1,step,0);         
        	   assertEquals("",recipe.getSteps());      	   
           }
    //Test if there is no image
           @Test
             public void testgetImages(){  
				java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step();
       		    Recipe recipe = new Recipe("11111","qq",v2,v1,step,0);              
       	      recipe.setSteps(step);
        	   assertNotNull(recipe.getImages());      	   
           }
 
           
   //Test setSteps
           @Test
             public void teststeps(){
				java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step(1,"12345","test");
        		Recipe recipe = new Recipe("12345","pizza",v2,v1,step,0);              
        	   recipe.setSteps(step);
        	   assertNotNull(recipe.getSteps());                     
            }
         
     //Test setimages
           @Test
             public void testimages(){
				java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
				java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
				Step step = new Step();
       		Recipe recipe = new Recipe("12345","pizza",v2,v1,step,0);              
       	   recipe.setImages(v2);
       	   assertNotNull(recipe.getImages());                   
           }    
    
           
 
           
           //test share recipe  
       	@Test
    	public void sharetest() {
    		fail("Not yet implemented");
    	}

       	
       	
       	//test publish and download a recipe
    	@Test
    	public void webservertest() {
    		fail("Not yet implemented");
    	}

    	
    	
    	// test search recipe	
    	public void searchtest() {
    		java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
			java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
			Step step = new Step();
			Recipe recipe = new Recipe("54321","soup",v2,v1,step,0);            
    	}
    	
    	
    	
    	
}		
  
	
