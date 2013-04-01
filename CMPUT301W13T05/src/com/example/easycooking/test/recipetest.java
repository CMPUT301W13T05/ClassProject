package com.example.easycooking.test;
/**
 * This class is testing the recipe model
 * @author Chenkun
 *
 */

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.easycooking.model.*;

public class recipetest extends TestCase {
	
	     public Recipe recipe;
      
		@Before
	         	 public void setUp()  {
				   
				    java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>(1);
			            java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>(2);
				    Step step = new Step();
       		                    recipe = new Recipe("12345","pizza",v2,v1,step,0);  
					
			               	}
			
			


	// Test getID 
		 @Test
			 public void testgetID(){		
				   
				   assertEquals("12345",recipe.getID());
			               
			               }
			 
		

       // Test setName of the recipe
	          @Test
     			public void testgetName(){				         
				       
				     recipe.setName("rice");
				     String expect = "rice";
				     assertEquals(expect, recipe.getName());
				
				      }
		     
        //Test set_download_upload_own and get_download_upload_own
	          
	            @Test
	               public void testget_download_upload_own(){
	         
	        	              recipe.set_download_upload_own(1);
	        	              assertEquals(1, recipe.get_download_upload_own());        	   
	                        
	                             }
	        
	//Test if the getStep runs
	          
		    @Test
	              public void testgetSteps(){					
				
				       Step step = new Step(2,"12345","get it");
			               recipe.setSteps(step);					       
	        	               assertEquals(step,recipe.getSteps());      	   
	      
	                              }
	  //Test if there is no image
	           @Test
	              public void testgetImages(){  
				
				        java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>(4);
					recipe.setImages(v2);
					assertEquals(v2,recipe.getImages());      	   
	         
	                              }
	 
	           
	   //Test setSteps
	           @Test
	              public void teststeps(){
				
				       Step step1 = new Step(2,"12345","test");              
	        	               recipe.setSteps(step1);
	        	               assertEquals(step1,recipe.getSteps());                     
	                    
	                               }
			
	           @After 
				   public void tearDown(){
				
				        recipe = null;					  
			       	        }
		
	    	
}		
	  
