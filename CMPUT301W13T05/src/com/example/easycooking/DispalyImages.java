package com.example.easycooking;

import android.os.Bundle;
import android.widget.Button;
import android.app.Activity;
/**
 * This is a test activity
 * @author Alvin Sun
 *
 */

public class DispalyImages extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_images);
		Button delete = (Button)findViewById(R.id.delete);
		
	}

	

}
