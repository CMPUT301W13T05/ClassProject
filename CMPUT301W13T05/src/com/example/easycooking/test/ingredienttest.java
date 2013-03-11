package com.example.easycooking.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import com.example.easycooking.ModifyIngredientsActivity;

public class ingredienttest extends TestCase {

  
       //Test if the ingredient is empty
				@Test
				  public void testEmptyIngredients(){
			        Recipe recipe = new Recipe();  
			        recipe.setIngredients(null);  
			        assertEquals("Recipe Ingredient is empty", recipe.getIngredients());

				}




	// test create a ingredient			
				@Test
				public  void testCreateingredient()
				{
					Ingredient new_ingredient = new Ingredient();

					new_ingredient.set_belongto("pizza");
					new_ingredient.set_name("salt");
					new_ingredient.set_amount("5 scrops");

			        assertEquals("belongto", "pizza", new_ingredient.get_belongto());
			        assertEquals("name", "salt", new_ingredient.get_name());
			        assertEquals("amount", "5 scrops", new_ingredient.get_amount());
				}


	}
/**

//test remove ingredient	    	 
	@Test
	public void testRemoveIngredient()
	{
		Recipe recipe = new Recipe();  
      Ingredient ingredient1 = new Ingredient("test1", (double) 5, "test1");    
      Ingredient ingredient2 = new Ingredient("test2", (double) 6, "test2");
      try
		{
			recipe.addIngredient(ingredient1);
			recipe.addIngredient(ingredient2);
		} catch (DuplicateIngredientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      recipe.removeIngredient(ingredient1);
      assertEquals("Remove Ingredient", ingredient2, recipe.getIngredients().get(0));		
	}



	@Test
	public void testAddIngredient()
	{

	    Recipe recipe = new Recipe();  
	    Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
	    try
	    {
	    	recipe.addIngredient(ingredient);
		} catch (DuplicateIngredientException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        assertEquals("Get Ingredient", ingredient, recipe.getIngredient(ingredient.getType()));	
	}

	@Test
	public void testGetOnServer()
	{
      Recipe recipe = new Recipe();  
      recipe.setOnServer(true);  
      assertEquals("GetOnServer", true , recipe.getOnServer());
	}

	@Test
	public void testSetOnServer()
	{
      Recipe recipe = new Recipe();  
      recipe.setOnServer(true);  
      assertEquals("GetOnServer", true , recipe.getOnServer());
	}



/**
* Tests the equals method
*/
