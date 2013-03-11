package com.example.easycooking.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

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
		//test add ingredient
				@Test
				 public void testaddIngredients(){
					java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
					java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
					Step step = new Step();
					Recipe recipe = new Recipe("12345","pizza",v2,v1,step, 0);
					Ingredient i = new Ingredient("egg", "5", "12345");
					Ingredient ii = new Ingredient("rice", "5", "12345");
					boolean exceptionWasCaught = false;

					try {
						recipe.getIngredients().add(i);
						} catch (Exception e) {
							e.printStackTrace();
						} 
	    ;
						try {
							recipe.getIngredients().add(ii);
						} catch (Exception e) {
							exceptionWasCaught = true;
							e.printStackTrace();
						}

						assertFalse(exceptionWasCaught);
				}
				
				 
				
//test if duplicate recipe is added

				@Test
				public void testAddingDuplicate() {
					java.util.ArrayList<Ingredient>  v1 = new java.util.ArrayList<Ingredient>();
					java.util.ArrayList<Image>  v2 = new java.util.ArrayList<Image>();
					Step step = new Step();
					Recipe recipe = new Recipe("12345","pizza",v2,v1,step, 0);
					Ingredient i = new Ingredient("egg", "5", "12345");
					Ingredient ii = new Ingredient("egg", "5", "12345");
					boolean exceptionWasCaught = false;

					try {
					recipe.getIngredients().add(i);
					} catch (Exception e) {
						e.printStackTrace();
					} 
    ;
					try {
						recipe.getIngredients().add(ii);
					} catch (Exception e) {
						exceptionWasCaught = true;
					}

					assertTrue(exceptionWasCaught);
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

