package com.example.easycooking.test;
/**
 * This class is testing the local database
 * @author Chenkun
 *
 */
import static org.junit.Assert.*;

import org.junit.Test;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

public class LocalDBtest {

	@Test
	public void testEquals() {
		/* Fixture */
		java.util.ArrayList<Ingredient>  v1= new java.util.ArrayList<Ingredient>(), v3  = new java.util.ArrayList<Ingredient>();
		java.util.ArrayList<Image>  v4 = new java.util.ArrayList<Image>(), v6 = new java.util.ArrayList<Image>();
		Step step1 = new Step(1,"12345","test");
		Step step3 = new Step(1,"98765","test");
		Recipe book1 = new Recipe("12345", "Title1",v4,v1,step1,0);
		Recipe book2 = new Recipe("98765", "Title2", v6, v3, step3, 0);


		/* Assertions */
		// A recipe must be equal to itself
		assertTrue(book1.equals(book1));


		// recipes with different id are not equal
		assertFalse(book1.equals(book2));
	}

}
