package com.example.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class CreateRecipe extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrecipe);
		//button home
		Button add_modify_home = (Button)findViewById(R.id.home);
		add_modify_home.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CreateRecipe.this, MainPage.class);
				//start a add entry activity
				startActivity(intent);
				//close the old activity
				CreateRecipe.this.finish();
			}
		});	
	}

	

}