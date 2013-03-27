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
import android.widget.PopupWindow;

public class SelectPicPopupWindow extends PopupWindow {


	private static CheckBox check_local,check_internet,check_dish,check_ingredient,check_onhand;
	private View mMenuView;

	@SuppressWarnings("deprecation")
	public SelectPicPopupWindow(Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.alert_dialog, null);
		/**
		 * set up all the check box button
		 */
		check_local = (CheckBox)mMenuView.findViewById(R.id.pop_local);
		check_internet = (CheckBox)mMenuView.findViewById(R.id.pop_internet);
		check_dish = (CheckBox)mMenuView.findViewById(R.id.pop_dish);
		check_ingredient = (CheckBox)mMenuView.findViewById(R.id.pop_ingredient);
		check_onhand = (CheckBox)mMenuView.findViewById(R.id.pop_onhand);
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

}
