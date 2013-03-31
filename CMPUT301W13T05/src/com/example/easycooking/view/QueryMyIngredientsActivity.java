package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
/**
 * This is a test activity
 * @author Alvin Sun
 *
 */

public class QueryMyIngredientsActivity extends Activity {
	private Recipe recipe_onhand = new Recipe();
	private static ArrayList<String> ingredient_list = new ArrayList<String>();
	private static ArrayList<Ingredient> ingredient_obj_list = new ArrayList<Ingredient>();
	private static ListView ingredient_listView;
	private MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_ingredients);
		myapp = (MyApp)getApplication();
		recipe_onhand = myapp.get_mrecipe();
		ingredient_obj_list = recipe_onhand.getIngredients();
		update_list(ingredient_obj_list);
		ingredient_listView= (ListView) findViewById(R.id.listView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(QueryMyIngredientsActivity.this,
				android.R.layout.simple_list_item_multiple_choice, ingredient_list);
		ingredient_listView.setAdapter(adapter);
		ingredient_listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
	}

	private void update_list(ArrayList<Ingredient> ingredients){
		
		ingredient_list.clear();		
		int i;
		for (i=0; i< ingredients.size();i++){
			ingredient_list.add(ingredients.get(i).get_name());	
		}
		return;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) { 
		menu.add(0,0,0,"Confirm");
		menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				@SuppressWarnings("deprecation")
				long[] ids=ingredient_listView.getCheckItemIds();
				ArrayList<String> onhand = new ArrayList<String>();
				for(int i=0;i<ids.length;i++){
					onhand.add(ingredient_list.get(i));
				}
				myapp.setOnhand(onhand);
				Intent intent = new Intent();
				intent.setClass(QueryMyIngredientsActivity.this,
						MainPageActivity.class);
				/**
				 * start a add entry activity
				 */
				startActivity(intent);
				QueryMyIngredientsActivity.this.finish();
				return false;
			}
			
		});
		return super.onCreateOptionsMenu(menu);
	}
}
