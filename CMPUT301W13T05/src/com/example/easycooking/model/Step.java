package com.example.easycooking.model;

public class Step {
	private Integer step_id;
	private String belong_to;
	private String Detail;
	//private String recipe_step;
	public Step(){
		
	}
	public Step(Integer id, String Recipe_id,String directions){
		this.step_id = id;
		this.belong_to = Recipe_id;
		this.Detail = directions;	
		//this.recipe_step = recipe_step;
	}
	public Integer get_id(){
		return step_id;
	}
	public void set_id(Integer id){
		this.step_id = id;
	}
	public String get_belong(){
		return belong_to;
	}
//	public String get_recipe_step(){
//		return recipe_step;
//	}
//	public void set_recipe_step(String recipe_step){
//		this.recipe_step = recipe_step;
//	}
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
