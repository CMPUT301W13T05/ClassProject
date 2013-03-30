package com.example.easycooking.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.example.easycooking.R;
import com.example.easycooking.application.MyApp;
import com.example.easycooking.controller.DatabaseManager;
import com.example.easycooking.controller.EmailController;
import com.example.easycooking.controller.UsefulFunctions;
import com.example.easycooking.controller.WEBClient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.SelectPicPopupWindow;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This the MainPageActivity.Like the home_page.
 * 
 * Searhing bar and Other functions' entry.
 * 
 * @author Alvin
 * 
 */
public class MainPageActivity extends Activity {

	protected OnClickListener itemsOnClick;
	private MyApp myapp;
	private boolean if_local = true;
	private boolean if_internet = true;
	private boolean if_dishname = false;
	private boolean if_ingredient = false;
	private boolean if_on_hand = false;
	private SelectPicPopupWindow menuWindow;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		final DatabaseManager dB_LocalDatabaseManager = DatabaseManager
				.getInstance(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// myapp = (MyApp)getApplication();
		/**
		 * Set up mode
		 */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		myapp = (MyApp) getApplication();
		myapp.setRecipe(null);
		myapp.setRecipe_list(null);
		final EditText serching_text = (EditText) findViewById(R.id.editText1);
		/**
		 * import intent json file
		 */
		Intent intent = getIntent();
		String action = intent.getAction();
		if (intent.ACTION_VIEW.equals(action)) {
			Gson gson = new Gson();
			System.out.println("run here get action" + intent.getDataString());
			//TODO add a toast tell the user will show the imput data
			try {

				BufferedReader br = new BufferedReader(new FileReader(intent
						.getData().getEncodedPath()));

				// convert the json string back to object
				Recipe obj = gson.fromJson(br, Recipe.class);
				myapp.setRecipe(obj);
				Intent intent2 = new Intent();
				Bundle mbundle = new Bundle();
				intent2.putExtras(mbundle);
				intent2.setClass(MainPageActivity.this,
						SelectionWebActivity.class);

				/**
				 * start a add entry activity
				 */
				startActivity(intent2);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		/**
		 * Search button
		 */

		Button main_search = (Button) findViewById(R.id.search);
		main_search.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/*
				 * Complete get ArrayList<Recipe>
				 */
				if (serching_text.getText().toString().isEmpty()) {
					Toast toast = Toast.makeText(MainPageActivity.this,
							"Empty Input!", Toast.LENGTH_LONG);
					toast.show();
				} else {
					dB_LocalDatabaseManager.open();
					WEBClient myClient = new WEBClient();
					ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
					String[] String_search = serching_text.getText().toString()
							.split(",");
					if (if_local) {
						if (if_dishname) {
							if (if_ingredient) { // local_name_ingredint
								result_recipe.addAll(dB_LocalDatabaseManager
										.searchRecipes(String_search, -99));
								/**
								 * This is a test
								 */
								System.out.println(String_search.length);
								for (String st : String_search) {
									System.out.println(st);
								}
							} else {// only by name and local
								result_recipe.addAll(dB_LocalDatabaseManager
										.searchRecipes(String_search, 1));
							}

						} else {
							if (if_ingredient) {// only by ingredient and local
								result_recipe.addAll(dB_LocalDatabaseManager
										.searchRecipes(String_search, 0));
							} else {
								// no ingredient dish name wrong
							}

						}
						if (if_internet) { // local and internet
							if (if_dishname) {
								if (if_ingredient) {// local internent name
													// ingredient
									try {
										result_recipe.addAll(myClient
												.searchRecipesWithName(String_search));
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									try {
										result_recipe.addAll(myClient
												.searchRecipesWithIngredient(String_search));
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {// local internet name
									try {
										result_recipe.addAll(myClient
												.searchRecipesWithName(String_search));
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							} else {
								if (if_ingredient) {// local intenet ingredient
									try {
										result_recipe.addAll(myClient
												.searchRecipesWithIngredient(String_search));
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									// no dish name no ingredient wrong
								}
							}

						}
					}// local end
					else if (if_internet) { // intenet
						if (if_dishname) {
							if (if_ingredient) {// internent name ingredient
								try {
									result_recipe.addAll(myClient
											.searchRecipesWithName(String_search));
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								try {
									result_recipe.addAll(myClient
											.searchRecipesWithIngredient(String_search));
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {// internet name
								try {
									result_recipe.addAll(myClient
											.searchRecipesWithName(String_search));
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} else {
							if (if_ingredient) {// intenet ingredient
								try {
									result_recipe.addAll(myClient
											.searchRecipesWithIngredient(String_search));
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								// no dish name no ingredient wrong
							}
						}

					}
					/**
		        		 * 
		        		 */
					dB_LocalDatabaseManager.close();
					
					if (result_recipe.size() > 0) {
						Intent intent = new Intent();
						// Bundle mbundle = new Bundle();
						// mbundle.putSerializable("RECIPE_RESULT",
						// result_recipe);
						// intent.putExtras(mbundle);
						UsefulFunctions so_fun = new UsefulFunctions();
						result_recipe = so_fun.Unique(result_recipe);
						myapp.setRecipe_list(result_recipe);
						intent.setClass(MainPageActivity.this,
								RecipeResultActivity.class);
						startActivity(intent);
					} else {
						Toast toast = Toast.makeText(MainPageActivity.this,
								"No Recipe Matched", Toast.LENGTH_LONG);
						toast.show();
					}

				}
			}
		});
		/**
		 * Profile button
		 */
		Button main_profile = (Button) findViewById(R.id.profile);
		main_profile.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainPageActivity.this, MyProfileActivity.class);
				/**
				 * start a add entry activity
				 */
				startActivity(intent);
				/**
				 * close the old activity
				 */
				/*
				 * MainPageActivity.this.finish();
				 */
			}
		});
		/**
		 * Create Recipe
		 */
		Button main_create = (Button) findViewById(R.id.creat);
		main_create.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				mbundle.putString("FromWhere", "MAIN");
				intent.putExtras(mbundle);
				intent.setClass(MainPageActivity.this,
						CreateRecipeActivity.class);
				/**
				 * start a add entry activity
				 */
				startActivity(intent);

			}
		});
		/**
		 * Custom Search
		 */
		Button main_custom = (Button) findViewById(R.id.setup_search);
		main_custom.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/**
				 * TODO implemented
				 */

				menuWindow = new SelectPicPopupWindow(MainPageActivity.this,
						itemsOnClick1, if_local, if_internet, if_dishname,
						if_ingredient, if_on_hand);
				menuWindow.showAtLocation(
						MainPageActivity.this.findViewById(R.id.setup_search),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				menuWindow.setFocusable(true);

				Toast toast = Toast.makeText(MainPageActivity.this, "TODO",
						Toast.LENGTH_LONG);
				toast.show();
			}
		});
		/**
		 * ModifyIngredientsActivityAcitivty
		 */
		Button main_ingredients = (Button) findViewById(R.id.modifyingre);
		main_ingredients.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
				dB_LocalDatabaseManager.open();
				ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
				result_recipe = dB_LocalDatabaseManager
						.searchRecipes(null, 999);
				Recipe mrecipe = result_recipe.get(0);
				Intent intent = new Intent();
				Bundle mbundle = new Bundle();
				mbundle.putSerializable("RECIPE_KEY", mrecipe);
				intent.putExtras(mbundle);
				intent.setClass(MainPageActivity.this,
						ModifyIngredientsActivity.class);

				/**
				 * start a add entry activity
				 */
				startActivity(intent);
				/**
				 * close the old activity
				 */
				MainPageActivity.this.finish();

			}
		});
	}

	private OnClickListener itemsOnClick1 = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("select internet");
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.confirm:
				if_local = menuWindow.get_local();
				if_dishname = menuWindow.get_dish();
				if_internet = menuWindow.get_internet();
				if_ingredient = menuWindow.get_ingredient();
				if_on_hand = menuWindow.get_onhand();
				break;
			default:
				break;
			}

		}

	};
	/**
	 * The menu with the button to let the user can jump to 
	 * the querying ingredients on hand activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(0, 0, 0,"Query Your Ingredients");
		return true;
	}

}
