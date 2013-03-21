package com.example.easycooking.test;
/**
 * This class is testing the ingredient model
 * @author Chenkun
 *
 */
import org.junit.Test;
import junit.framework.TestCase;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;
import com.example.easycooking.view.ModifyIngredientsActivity;

public class ingredienttest extends TestCase {

  
     

				
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
					Recipe recipe = new Recipe("33333","pizza",v2,v1,step, 0);
					Ingredient i = new Ingredient("tomato", "5", "33333");
					Ingredient ii = new Ingredient("tomato", "5", "33333");
     				recipe.getIngredients().add(i);
     				recipe.getIngredients().add(ii);				
					assertEquals(2,recipe.getIngredients().size());
				
					
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

