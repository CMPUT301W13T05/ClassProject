package com.example.easycooking.model;

import com.example.easycooking.R;

import android.os.Bundle;
import android.app.Activity;
/**
 * This is a test activity
 * @author Alvin Sun
 *
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;

public class SelectPicPopupWindow extends PopupWindow {


	private static CheckBox check_local,check_internet,check_dish,check_ingredient,check_onhand;
	private static Button check_confrim;
	private View mMenuView;
	private static boolean local,internet,dish,ingredient,onhand; 

	@SuppressWarnings("deprecation")
	public SelectPicPopupWindow(Activity context,OnClickListener itemsOnClick,boolean local,boolean internet,boolean dish,boolean ingredient,boolean onhand) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.alert_dialog, null);
		this.local = local;
		this.internet = internet;
		this.ingredient = ingredient;
		this.dish = dish;
		this.onhand = onhand;
		/**
		 * set up all the check box button
		 */
		check_local = (CheckBox)mMenuView.findViewById(R.id.pop_local);
		check_internet = (CheckBox)mMenuView.findViewById(R.id.pop_internet);
		check_dish = (CheckBox)mMenuView.findViewById(R.id.pop_dish);
		check_ingredient = (CheckBox)mMenuView.findViewById(R.id.pop_ingredient);
		check_onhand = (CheckBox)mMenuView.findViewById(R.id.pop_onhand);
		check_local.setChecked(get_local());
		check_internet.setChecked(get_internet());
		check_dish.setChecked(get_dish());
		check_ingredient.setChecked(get_ingredient());
		check_onhand.setChecked(get_onhand());
		check_confrim = (Button)mMenuView.findViewById(R.id.confirm);
		
		check_confrim.setOnClickListener(itemsOnClick);
		/**
		 * set up the layout
		 */
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AppBaseTheme);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		
		check_local.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					set_local(isChecked);
				}
				else{
					set_local(isChecked);
				}
			}
			
		});
		check_internet.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					set_internet(isChecked);
				}
				else{
					set_internet(isChecked);
				}
			}
			
		});
		check_dish.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					set_dish(isChecked);
				}
				else{
					set_dish(isChecked);
				}
			}
			
		});
		check_ingredient.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					set_ingredient(isChecked);
				}
				else{
					set_ingredient(isChecked);
				}
			}
			
		});
		check_onhand.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					set_onhand(isChecked);
				}
				else{
					set_onhand(isChecked);
				}
			}
			
		});
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}
	public boolean get_local(){
		return this.local;
	}
	public void set_local(boolean setting){
		this.local = setting;
	}
	public boolean get_internet(){
		return this.internet;
	}
	public void set_internet(boolean setting){
		this.internet = setting;
	}
	public boolean get_dish(){
		return this.dish;
	}
	public void set_dish(boolean setting){
		this.dish = setting;
	}
	public boolean get_onhand(){
		return this.onhand;
	}
	public void set_onhand(boolean setting){
		this.onhand = setting;
	}
	public boolean get_ingredient(){
		return this.ingredient;
	}
	public void set_ingredient(boolean setting){
		this.ingredient = setting;
	}
}
