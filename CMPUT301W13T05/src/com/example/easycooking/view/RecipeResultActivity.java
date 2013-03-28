package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * This is the View class that show all the recipe with the recipe name By the
 * given ArrayList of Recipe object
 * 
 * @author Alvin
 * 
 */
public class RecipeResultActivity extends Activity {
	private static Recipe choosen_recipe = new Recipe();
	private static ArrayList<Recipe> result = new ArrayList<Recipe>();
	private static ArrayList<String> result_string = new ArrayList<String>();
	private MyApp myapp;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		/**
		 * get the ArrayList<Recipe>
		 */
		myapp = (MyApp) getApplication();
		result = myapp.getAll();
		update_list(result);
		// set up view
		ListView result_listView = (ListView) findViewById(R.id.listView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_selectable_list_item, result_string);
		result_listView.setAdapter(adapter);
		result_listView.setItemsCanFocus(false);
		result_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		/**
		 * build up the click function for choosing a recipe name and jump to
		 * the details
		 */
		result_listView
				.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							final int position, long id) {
						choosen_recipe = result.get(position);
						/**
						 * pass the recipe object to the selectionLocal view
						 */
						System.out.println(choosen_recipe
								.get_download_upload_own());
						if (choosen_recipe.get_download_upload_own() == 101) {
							System.out.println(choosen_recipe
									.get_download_upload_own() + "==");
							myapp.setRecipe(choosen_recipe);
							Intent intent = new Intent();
							intent.setClass(RecipeResultActivity.this,
									SelectionWebActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.setClass(RecipeResultActivity.this,
									SelectionLocalActivity.class);
							/**
							 * start a add entry activity
							 */
							myapp.setRecipe(choosen_recipe);
							// Bundle mbundle = new Bundle();
							// mbundle.putSerializable("RECIPE_KEY",
							// choosen_recipe);
							// intent.putExtras(mbundle);
							startActivity(intent);
						}
					}
				});
		/**
		 * Take a new search
		 */
		Button result_search = (Button) findViewById(R.id.search);
		result_search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RecipeResultActivity.this,
						MainPageActivity.class);
				// start a add entry activity
				startActivity(intent);
				// close the old activity
				RecipeResultActivity.this.finish();
			}
		});

	}

	private void update_list(ArrayList<Recipe> recipes) {
		result_string.clear();
		Recipe recipe = new Recipe();
		int i;
		for (i = 0; i < recipes.size(); i++) {
			recipe = recipes.get(i);
			result_string.add(recipe.getName());
		}
		return;
	}
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
	 */

}
