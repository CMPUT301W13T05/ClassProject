package com.example.easycooking;

import java.util.ArrayList;

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
//import android.view.Menu;
import android.content.Intent;

public class ModifyIngredientsActivity extends Activity {
	private static Recipe mrecipe = new Recipe();
	private static ArrayList<String> ingredient_list = new ArrayList<String>();
	private static ArrayList<Ingredient> ingredient_obj_list = new ArrayList<Ingredient>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddetail);
		//ingredient_list.add("");
		/**
		 * set up each view variable
		 */
		final EditText ingredient_name = (EditText)findViewById(R.id.ingredient_text);
		final EditText ingredient_amount = (EditText)findViewById(R.id.amount_Text);
		ListView ingredient_listView= (ListView) findViewById(R.id.listView1);
		
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		ingredient_obj_list = mrecipe.getIngredients();
		update_list(ingredient_obj_list);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice , ingredient_list);
		ingredient_listView.setAdapter(adapter);
		ingredient_listView.setItemsCanFocus(false);
		ingredient_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
		
		
		//Add button
		Button modify_ingredients_add = (Button)findViewById(R.id.add);
		modify_ingredients_add.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Ingredient new_ingredient = new Ingredient();
				new_ingredient.set_belongto(mrecipe.getID());
				new_ingredient.set_name(ingredient_name.getText().toString());
				new_ingredient.set_amount(ingredient_amount.getText().toString());
				ingredient_obj_list.add(new_ingredient);
				update_list(ingredient_obj_list);
				adapter.notifyDataSetChanged();//refressh the list View
			}
		});
		
		//Save button
		Button modify_ingredients_save = (Button)findViewById(R.id.save);
		modify_ingredients_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mrecipe.setIngredients(ingredient_obj_list);
				Intent intent = new Intent();
				intent.setClass(ModifyIngredientsActivity.this, CreateRecipeActivity.class);// should be jump to the modify 
				//start a add entry activity
				Bundle mbundle = new Bundle();
				mbundle.putString("FromWhere", "MODIFY");
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				startActivity(intent);
				//close the old activity
				ModifyIngredientsActivity.this.finish();
			}
		});
		/////////////////////////////////////////////////////
		ingredient_listView.setOnItemClickListener(new ListView.OnItemClickListener(){
	        	@Override
	        	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,long id) 
	        	{
	        		//get the position
	        		//delete button
	        		Button modify_ingredients_delete = (Button)findViewById(R.id.delete);
	        		modify_ingredients_delete.setOnClickListener(new Button.OnClickListener() {
	        		        public void onClick(View v) {
	        		        		//To be implemented
	        		        	}
	        		        });
	        	}

		});
	}
	
	private void update_list(ArrayList<Ingredient> ingredients){
		ingredient_list.clear();
		Ingredient mingredient = new Ingredient();
		int i;
		for (i=0; i< ingredients.size();i++){
			mingredient = ingredients.get(i);
			ingredient_list.add(mingredient.get_amount()+" "+ mingredient.get_name());
		}
		return;
	}
	
	//@Override
	/*public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_itisatry, menu);
		return true;
	}*/

}