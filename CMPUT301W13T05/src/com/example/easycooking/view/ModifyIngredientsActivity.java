package com.example.easycooking.view;

import java.util.ArrayList;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;
/**
 * This is the ModifyIngredientsActivity View It let User add the ingredents and set amount
 * also let user to modify an exist recipes' ingredients.
 * @author  Alvin
 */
public class ModifyIngredientsActivity extends Activity {
	/**
	 * @uml.property  name="mrecipe"
	 * @uml.associationEnd  
	 */
	private static Recipe mrecipe = new Recipe();
	private static ArrayList<String> ingredient_list = new ArrayList<String>();
	private static ArrayList<Ingredient> ingredient_obj_list = new ArrayList<Ingredient>();
	private static ArrayList<String> ingredient_name_list = new ArrayList<String>();
	private static String _CHECK_SAVE_BUTTON = "UN_MODIFY" ;
	private static int _CHECK_POSITION;
	private static String _FROM_WHERE = "";
	private MyApp myapp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddetail);
		myapp = (MyApp)getApplication();
		/**
		 * set up each view variable
		 */
		final EditText ingredient_name = (EditText)findViewById(R.id.ingredient_text);
		final EditText ingredient_amount = (EditText)findViewById(R.id.amount_Text);
		final ListView ingredient_listView= (ListView) findViewById(R.id.listView1);
		final Button modify_ingredients_save = (Button)findViewById(R.id.recipe_save);
		final Button modify_ingredients_add = (Button)findViewById(R.id.add);
		final Button modify_ingredients_delete = (Button)findViewById(R.id.delete);
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		//mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		mrecipe = myapp.get_mrecipe();
		ingredient_obj_list = mrecipe.getIngredients();
		update_list(ingredient_obj_list);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModifyIngredientsActivity.this,
				android.R.layout.simple_list_item_single_choice , ingredient_list);
		ingredient_listView.setAdapter(adapter);
		ingredient_listView.setItemsCanFocus(false);
		ingredient_listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
		
		if (ingredient_obj_list.isEmpty()){
			modify_ingredients_save.setEnabled(false);
			modify_ingredients_delete.setEnabled(false);
		}
		
		/**
		 * Add button, add an ingredient
		 */		
		modify_ingredients_add.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Ingredient new_ingredient = new Ingredient();
				new_ingredient.set_belongto(mrecipe.getID());
				new_ingredient.set_name(ingredient_name.getText().toString());
				new_ingredient.set_amount(ingredient_amount.getText().toString());
				if (new_ingredient.get_name().isEmpty() ){
					Toast toast = Toast.makeText(ModifyIngredientsActivity.this, "Pleas Enter The Required Information!", Toast.LENGTH_LONG);   
					toast.show();
				}
				else if (ingredient_name_list.contains(new_ingredient.get_name()) ){
					Toast toast = Toast.makeText(ModifyIngredientsActivity.this, "Cannot Add Same Ingredient", Toast.LENGTH_LONG);   
					toast.show();
				}
				else{
				ingredient_obj_list.add(new_ingredient);
				ingredient_name_list.add(new_ingredient.get_name());
				update_list(ingredient_obj_list);
				adapter.notifyDataSetChanged();//refresh the list View
				ingredient_name.setText("");
				ingredient_amount.setText("");
				}
				if (ingredient_obj_list.isEmpty()){
	    			modify_ingredients_save.setEnabled(false);
	    			modify_ingredients_delete.setEnabled(false);
	    		}
	        	else{
	        		modify_ingredients_save.setEnabled(true);
	    			modify_ingredients_delete.setEnabled(true);
	        	}
			}
		});
		
		/**
		 * Save button 
		 * Save button can save the ingredient or save the changing of 
		 * one recipe
		 */
		
		modify_ingredients_save.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				System.out.println("here"+_FROM_WHERE);
				if (_CHECK_SAVE_BUTTON.equals("UN_MODIFY")){
					mrecipe.setIngredients(ingredient_obj_list);
					/**
					 * If this is the modifycation of  the ingredientsOnhand
					 */
					System.out.println("here"+_FROM_WHERE);
					if (_FROM_WHERE.equals("IngredientOnHand")){
						DatabaseManager dB_LocalDatabaseManager = DatabaseManager
								.getInstance(ModifyIngredientsActivity.this);						
						if (mrecipe.getIngredients().size()>0){
							dB_LocalDatabaseManager.open();
							dB_LocalDatabaseManager.delete_ingredient(mrecipe.getID());
							int i;
							for (i=0;i<mrecipe.getIngredients().size();i++){
								dB_LocalDatabaseManager.add_ingrdient(mrecipe.getIngredients().get(i));
							}
							dB_LocalDatabaseManager.close();
						}
						myapp.setOnhand(ingredient_name_list);
						Intent intent = new Intent();
						intent.setClass(ModifyIngredientsActivity.this, MainPageActivity.class);// should be jump to the modify 
						/**
						 * start a add entry activity
						 */					
						startActivity(intent);
						ModifyIngredientsActivity.this.finish();
					}
					else{
					Intent intent = new Intent();
					intent.setClass(ModifyIngredientsActivity.this, CreateRecipeActivity.class);// should be jump to the modify 
					/**
					 * start a add entry activity
					 */
					Bundle mbundle = new Bundle();
					mbundle.putString("FromWhere", _FROM_WHERE);
					myapp.setRecipe(mrecipe);
					intent.putExtras(mbundle);
					startActivity(intent);
					/**
					 * close the old activity
					 */
					ModifyIngredientsActivity.this.finish();
					}
				}
				else{
					if(ingredient_name_list.get(_CHECK_POSITION).equals(ingredient_name.getText().toString())){
						ingredient_obj_list.get(_CHECK_POSITION).set_amount(ingredient_amount.getText().toString());
						update_list(ingredient_obj_list);
						adapter.notifyDataSetChanged();
						modify_ingredients_add.setEnabled(true);
						ingredient_name.setText("");
						ingredient_amount.setText("");
						_CHECK_SAVE_BUTTON = "UN_MODIFY";
					}
					else if (ingredient_name_list.contains(ingredient_name.getText().toString())){
						Toast toast = Toast.makeText(ModifyIngredientsActivity.this, "Cannot Add Same Ingredient", Toast.LENGTH_LONG);   
						toast.show();
					}
					else{
						ingredient_obj_list.get(_CHECK_POSITION).set_name(ingredient_name.getText().toString());
						ingredient_obj_list.get(_CHECK_POSITION).set_amount(ingredient_amount.getText().toString());
						update_list(ingredient_obj_list);
						adapter.notifyDataSetChanged();
						modify_ingredients_add.setEnabled(true);
						_CHECK_SAVE_BUTTON = "UN_MODIFY";
						ingredient_name.setText("");
						ingredient_amount.setText("");
					}
				}
			}
		});
		/**
		 * Here is set a listview listener to handle the click activity on 
		 * the list view
		 */
		ingredient_listView.setOnItemClickListener(new ListView.OnItemClickListener(){
	        	@Override
	        	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,long id) 
	        	{
	        		
	        		_CHECK_SAVE_BUTTON = "MODIFY";
	        		_CHECK_POSITION = position;
	        		ingredient_name.setText(ingredient_obj_list.get(position).get_name());
	        		ingredient_amount.setText(ingredient_obj_list.get(position).get_amount());
	        		modify_ingredients_add.setEnabled(false);
	        		
	        		modify_ingredients_delete.setOnClickListener(new Button.OnClickListener() {
	        		        public void onClick(View v) {
	        		        	ingredient_name.setText("");
	        					ingredient_amount.setText("");
	        		        	if (ingredient_obj_list.isEmpty()){
	        		    			modify_ingredients_save.setEnabled(false);
	        		    			modify_ingredients_delete.setEnabled(false);
	        		    		}
	        		        	else{
	        		        		modify_ingredients_save.setEnabled(true);
	        		    			modify_ingredients_delete.setEnabled(true);
	        		    			ingredient_obj_list.remove(position);
		        		        	ingredient_list.remove(position);
		        		        	ingredient_name_list.remove(position);
		        		        		//To be implemented
		        		        	adapter.notifyDataSetChanged();
		        		        	ingredient_listView.setSelected(false);
		        		        	modify_ingredients_add.setEnabled(true);
		        		        	if (ingredient_obj_list.isEmpty()){
		        		    			modify_ingredients_save.setEnabled(false);
		        		    			modify_ingredients_delete.setEnabled(false);
		        		    		}
	        		        	}
	        		        	_CHECK_SAVE_BUTTON = "UN_MODIFY";
	        		        	}
	        		        });


	        	}
		});
		
		
	
	}
	/**
	 * Given a ingreidient list to update the listview
	 * @param ingredients
	 */
	private void update_list(ArrayList<Ingredient> ingredients){
		ingredient_name_list.clear();
		ingredient_list.clear();
		Ingredient mingredient = new Ingredient();
		int i;
		String combine;
		for (i=0; i< ingredients.size();i++){
			mingredient = ingredients.get(i);
			combine = mingredient.get_amount()+" "+ mingredient.get_name();
			ingredient_list.add(combine);
			ingredient_name_list.add(mingredient.get_name());
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