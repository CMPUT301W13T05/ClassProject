package com.example.easycooking;


import java.util.ArrayList;

import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RecipeResultActivity extends Activity {
	private static ArrayList<Recipe> result = new ArrayList<Recipe>();
	private static ArrayList<String> result_string = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		result = (ArrayList<Recipe>)getIntent().getSerializableExtra("RECIPE_RESULT");
		update_list(result);
		//set up view 
		ListView result_listView= (ListView) findViewById(R.id.listView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice , result_string);
		result_listView.setAdapter(adapter);

		result_listView.setItemsCanFocus(false);

		result_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
		////////////////////////////////Profile button
		Button result_search =(Button)findViewById(R.id.search);
		result_search.setOnClickListener(new Button.OnClickListener() {
		       public void onClick(View v) {
		        	Intent intent = new Intent();
		        	intent.setClass(RecipeResultActivity.this, MainPageActivity.class);
		        	//start a add entry activity
		        	startActivity(intent);
		        	//close the old activity
		        	RecipeResultActivity.this.finish();
		        }
		       });
		
	}
	private void update_list(ArrayList<Recipe> recipes){
		result_string.clear();
		Recipe recipe = new Recipe();
		int i;
		for (i=0; i< recipes.size();i++){
			recipe = recipes.get(i);
			result_string.add(recipe.getName());
		}
		return;
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/
	
	//back button pressed
	
	//public void onBackPressed() {
	//return;
	//}
	
}
