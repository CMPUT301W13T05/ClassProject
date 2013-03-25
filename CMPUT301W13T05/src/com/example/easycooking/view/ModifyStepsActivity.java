package com.example.easycooking.view;

import com.example.easycooking.R;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;
/**
 * This a View to set the description of a recipe
 * @author  Alvin
 */
public class ModifyStepsActivity extends Activity {
	/**
	 * @uml.property  name="mrecipe"
	 * @uml.associationEnd  
	 */
	private static Recipe mrecipe = new Recipe();
	/**
	 * @uml.property  name="mstep"
	 * @uml.associationEnd  
	 */
	private static Step mstep = new Step();
	private static String _FROM_WHERE = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddetail_steps);
		final EditText directions = (EditText)findViewById(R.id.editText1);
		final Button save = (Button)findViewById(R.id.save);
		Bundle mbundle = getIntent().getExtras();
		_FROM_WHERE = mbundle.getString("FromWhere");
		mrecipe = (Recipe)getIntent().getSerializableExtra("RECIPE_KEY");
		directions.setText(mrecipe.getSteps().get_detail());
		save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (directions.getText().toString().equals("")){
					Toast toast = Toast.makeText(ModifyStepsActivity.this, "Pleas Enter The Required Information!", Toast.LENGTH_LONG);   
					toast.show();
				}
				else{
					mstep.set_detail(directions.getText().toString());
					mrecipe.setSteps(mstep);
					mstep.set_belong(mrecipe.getID());
					Intent intent = new Intent();
					intent.setClass(ModifyStepsActivity.this, CreateRecipeActivity.class);// should be jump to the modify 
					//start a add entry activity
					Bundle mbundle = new Bundle();
					mbundle.putString("FromWhere", _FROM_WHERE);
					mbundle.putSerializable("RECIPE_KEY", mrecipe);
					intent.putExtras(mbundle);
					startActivity(intent);
					//close the old activity
					ModifyStepsActivity.this.finish();
				}
				
			}
		});
			
	}

	

}
