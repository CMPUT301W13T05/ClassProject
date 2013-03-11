package com.example.easycooking.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int download_upload_own;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Image> images;
	private Step steps;
	public Recipe(){
		
	}
	public Recipe(String id , String name, ArrayList<Image> image_list, ArrayList<Ingredient> ingredient_list,Step step,
			int download_upload_own){
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredient_list;
		this.images = image_list;
		this.steps = step;
		this.download_upload_own = download_upload_own;
	}
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public int get_download_upload_own(){
		return download_upload_own;
	}
	public void set_download_upload_own(int download_upload_own){
		this.download_upload_own = download_upload_own;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public ArrayList<Image> getImages() {
		return images;
	}
	
	public Step getSteps(){
		return steps;
	}
	public void setSteps(Step steps){
		this.steps = steps;
	}
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	
}