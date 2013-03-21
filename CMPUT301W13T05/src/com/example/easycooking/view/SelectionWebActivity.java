package com.example.easycooking.view;

import com.example.easycooking.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;
/**
 * TODO Implemented
 * @author Alvin
 *
 */
public class SelectionWebActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection_web);
		//button share
		Button selection_share = (Button)findViewById(R.id.share);
		selection_share.setOnClickListener(new Button.OnClickListener() {
	        public void onClick(View v) {
	        		Intent intent = new Intent();
	        		intent.setClass(SelectionWebActivity.this, Itisatry.class);// to share pop layout
	        		//start a add entry activity
	        		startActivity(intent);
	        		//close the old activity
	        		SelectionWebActivity.this.finish();
	        	}
	        });
		

		//button upload
		Button selection_web_download = (Button)findViewById(R.id.download);
		selection_web_download.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
					//TO BE IMPLEMENTED
			      }
			});


	}
}