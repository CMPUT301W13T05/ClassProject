package com.example.easycooking.model;

public class Step {
	private String step_id;
	private String belong_to;
	private String Detail;
	public Step(){
		
	}
	public Step(String id, String Recipe_id,String directions){
		this.step_id = id;
		this.belong_to = Recipe_id;
		this.Detail = directions;			
	}
	public String get_id(){
		return step_id;
	}
	public void set_id(String id){
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
