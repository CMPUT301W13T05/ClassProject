package com.example.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;

public class SelectionLocal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		//button share
		Button selection_share = (Button)findViewById(R.id.search);
		selection_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(SelectionLocal.this, Itisatry.class);
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		SelectionLocal.this.finish();
	        	}
	        });
		
		//button modify
		Button selection_modify = (Button)findViewById(R.id.modify);
		selection_modify.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
			        Intent intent = new Intent();
			        intent.setClass(SelectionLocal.this, Itisatry.class);// should change to the RecipeDetails.java
			        //start a add entry activity
			        startActivity(intent);
			        //close the old activity
			        SelectionLocal.this.finish();
			      }
			});
		//button delete
		Button selection_delete = (Button)findViewById(R.id.delete);
		selection_delete.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					//TO BE IMPLEMENTED
			      }
			});
		//button upload
		Button selection_upload = (Button)findViewById(R.id.upload);
		selection_upload.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					//TO BE IMPLEMENTED
			      }
			});


	}

	

}
