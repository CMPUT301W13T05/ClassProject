package com.example.easycooking.test;
/**
 * This class is testing the recipe model
 * @author Chenkun
 *
 */
import static org.junit.Assert.fail;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;
public class recipetest extends TestCase {
	
	     private Recipe recipe;
      
			@Before
				   public void setup() throws Exception {
					 recipe = new Recipe();
					
				}
			@After 
				   public void teardown() throws Exception{
					 recipe = null;					  
				}
		

			// Test ID can not BE NULL
			 @Test
				 public void testgetID(){		    	   				     
						assertNull(recipe.getID());
				   }

		    // Test setName of the recipe
	              
			 @Test
					public void testgetName(){				         
				        recipe.setName("pizza");
				        String expect = "pizza";
				        assertEquals(expect, recipe.getName());
					}
		     
		   //Test set_download_upload_own and get_download_upload_own
	          
			  @Test
	               public void testget_download_upload_own(){
	         
	        	   recipe.set_download_upload_own(0);
	        	   assertEquals(0, recipe.get_download_upload_own());        	   
	           }
	        
			//Test if the getStep runs
	          
			  @Test
	              public void testgetSteps(){					
					Step step = new Step(1,"112233","make it");
					recipe.setSteps(step);					       
	        	   assertEquals(step,recipe.getSteps());      	   
	           }
	       //Test if there is no image
	           @Test
	              public void testgetImages(){  
					java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
					recipe.setImages(v2);
					assertNotNull(recipe.getImages());      	   
	           }
	 
	           
	      //Test setSteps
	           @Test
	              public void teststeps(){
					Step step = new Step(1,"12345","test");              
	        	    recipe.setSteps(step);
	        	    assertNotNull(recipe.getSteps());                     
	            }
	    
	           
	    	
	    	
	    	
	}		
