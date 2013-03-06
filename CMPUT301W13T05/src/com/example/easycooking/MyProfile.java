package com.example.easycooking;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MyProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile);
		 Button b1 = (Button) findViewById(R.id.button1);
	        b1.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, RecipeDisplay.class);
	        		startActivity(intent);
	        		MyProfile.this.finish();
	        	}
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	//back button pressed
	public void onBackPressed() {
	return;
	}

}
