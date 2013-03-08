package com.example.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
//import android.view.Menu;

public class ModifyIngredientsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddetail);
		//Add button
		Button modify_ingredients_add = (Button)findViewById(R.id.add);
		modify_ingredients_add.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
		        		//To be implemented
			}
		});
		//Save button
		Button modify_ingredients_save = (Button)findViewById(R.id.save);
		modify_ingredients_save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
		        		//To be implemented
			}
		});
		//delete button
		//Save button
		Button modify_ingredients_delete = (Button)findViewById(R.id.delete);
		modify_ingredients_delete.setOnClickListener(new Button.OnClickListener() {
		        public void onClick(View v) {
		        		//To be implemented
		        	}
		        });
	}
	
	//@Override
	/*public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_itisatry, menu);
		return true;
	}*/

}