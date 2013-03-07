package com.example.easycooking;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MyProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile);
		// record recipes
		Button profile_record = (Button)findViewById(R.id.record);
		profile_record.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, RecipeResult.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfile.this.finish();
	        	}
	        });
		// local recipes
		Button profile_local = (Button)findViewById(R.id.checklocal);
		profile_local.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, RecipeResult.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfile.this.finish();
	        	}
	        });
		// upload recipes
		Button profile_upload = (Button)findViewById(R.id.upload);
		profile_upload.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, RecipeResult.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfile.this.finish();
	        	}
	        });
		// downloaded recipes
		Button profile_downloaded = (Button)findViewById(R.id.check_download);
		profile_downloaded.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, RecipeResult.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfile.this.finish();
	        	}
	        });
		// back to the main
		Button profile_back_main = (Button)findViewById(R.id.backtomain);
		profile_back_main.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(MyProfile.this, MainPage.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		MyProfile.this.finish();
	        	}
	        });
		// share with me button
		Button profile_share = (Button)findViewById(R.id.share_with_me);
		profile_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        	// to be implemented
	        	}
	        });
		
	}

	@Override
	
	//back button pressed
	public void onBackPressed() {
	return;
	}

}
