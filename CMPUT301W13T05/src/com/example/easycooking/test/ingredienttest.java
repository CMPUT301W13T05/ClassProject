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

