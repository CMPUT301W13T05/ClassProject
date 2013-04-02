package com.example.easycooking.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.easycooking.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.content.Context;
import com.example.easycooking.controller.DatabaseManager;

      public class DatabaseManagerTest extends TestCase{
	
		       protected DatabaseManagerTest(Context context) {
			          super();
		          }
	   	       DatabaseManager  dbHelper;
   
	
	@Before
	                public void setUp() {
	                          this.dbHelper.open();
	                 }
	
	@Test
	/**
	* recipes may have the same information, but they must have unique IDs
	*/
              public void test_storesrecipeByUniqueID() {
              	  Recipe recipe1,recipe2,recipe3 = new Recipe();
                  java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
                  java.util.ArrayList<Ingredient>  v2 = new java.util.ArrayList<Ingredient>();
                  java.util.ArrayList<Ingredient>  v3 = new java.util.ArrayList<Ingredient>();
                  java.util.ArrayList<Image>  v4 = new java.util.ArrayList<Image>();
                  java.util.ArrayList<Image>  v5 = new java.util.ArrayList<Image>();
                  java.util.ArrayList<Image>  v6 = new java.util.ArrayList<Image>();
                  Step step1 = new Step();
                  Step step2 = new Step();
                  Step step3 = new Step();
                  recipe1 = new Recipe("abc","name1",v4,v1,step1,0); 
                  recipe2 = new Recipe("def","name2",v5,v2,step2,0); 
                  recipe3 = new Recipe("abc","name3",v6,v3,step3,0); 
	
                  Assert.assertTrue(this.dbHelper.inDB(recipe1));
                  Assert.assertTrue(this.dbHelper.inDB(recipe2));
                  Assert.assertFalse(this.dbHelper.inDB(recipe3));
	
            	}
	
	@Test
	/**
	* Test that only public recipes are viewable to other users.
	*/
	            public void test_fetchesRecipesAvailableToUser() {
                	    Recipe recipe1,recipe2 = new Recipe();
                	    java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
                	    java.util.ArrayList<Ingredient>  v2 = new java.util.ArrayList<Ingredient>();
                	    java.util.ArrayList<Image>  v4 = new java.util.ArrayList<Image>();
                	    java.util.ArrayList<Image>  v5 = new java.util.ArrayList<Image>();
                	    Step step1 = new Step();
                	    Step step2 = new Step();
                	    recipe1 = new Recipe("abc","name1",v4,v1,step1,1); 
                	    recipe2 = new Recipe("def","name2",v5,v2,step2,0); 
                	
                	    Assert.assertTrue(this.dbHelper.inDB(recipe1));
                	    Assert.assertTrue(this.dbHelper.inDB(recipe2));
                	}
	

	
	@After
	              public void tearDown() {

                     this.dbHelper.close();
	              }
	
	}
