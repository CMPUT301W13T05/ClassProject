package com.example.easycooking.model;

import java.io.Serializable;
/**
 * This a model object for Step
 * contains step_id, which recipe does it belong to and the detail of the step
 * @author Alvin
 *
 */
public class Step implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int step_id;
	private String belong_to;
	private String Detail = "";
	//private String recipe_step;
	public Step(){
		
	}
	public Step(int id, String Recipe_id,String directions){
		super();
		this.step_id = id;
		this.belong_to = Recipe_id;
		this.Detail = directions;	
	
	}
	public int get_id(){
		return step_id;
	}
	public void set_id(int id){
		this.step_id = id;
	}
	public String get_belong(){
		return belong_to;
	}

	public void set_belong(String Recipe_id){
		this.belong_to = Recipe_id;
	}
	public String get_detail(){
		return Detail;
	}
	public void set_detail(String directions){
		this.Detail = directions;
	}
	public String to_String(){
		return step_id + belong_to + Detail;
	}
}
