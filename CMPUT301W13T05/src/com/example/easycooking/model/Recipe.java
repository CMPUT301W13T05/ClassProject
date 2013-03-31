package com.example.easycooking.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.easycooking.model.Step;
import com.example.easycooking.model.Ingredient;

/**
 * This is a model class for Recipe  
 * it contains recipe id, images list and ingredient list and the steps 
 * image list and ingredient list are ArrayLists that contain image object and ingredient object
 * @author  Alvin
 */
public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * @uml.property  name="name"
	 */
	private String name;

	/**
	 * @uml.property  name="ingredients"
	 */
	private ArrayList<Ingredient> ingredients;
	/**
	 * @uml.property  name="images"
	 */
	private ArrayList<Image> images;
	/**
	 * @uml.property  name="steps"
	 * @uml.associationEnd  
	 */
	private Step steps;
	private int download_upload_own;
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
	
	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	public int get_download_upload_own(){
		return download_upload_own;
	}
	public void set_download_upload_own(int download_upload_own){
		this.download_upload_own = download_upload_own;
	}

	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 * @uml.property  name="ingredients"
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients
	 * @uml.property  name="ingredients"
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	/**
	 * @return
	 * @uml.property  name="images"
	 */
	public ArrayList<Image> getImages() {
		return images;
	}
	
	/**
	 * @return
	 * @uml.property  name="steps"
	 */
	public Step getSteps(){
		return steps;
	}
	/**
	 * @param steps
	 * @uml.property  name="steps"
	 */
	public void setSteps(Step steps){
		this.steps = steps;
	}
	/**
	 * @param images
	 * @uml.property  name="images"
	 */
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	@SuppressWarnings("null")
	@Override
	public String toString(){
		ArrayList<String> string_ingredients = null;
		ArrayList<String> string_images = null;
		ArrayList<String> string_in_amount = null;
		int i;
		for (i=0;i<this.getIngredients().size();i++){
			string_ingredients.add(this.getIngredients().get(i).get_name());
			string_in_amount.add(this.getIngredients().get(i).get_amount());
		}
		for (i=0;i<this.getImages().size();i++){
			string_images.add(this.getImages().get(i).get_imageUri());
		}
			
		return "Recipe [ id = " +id+", name ="+ name+", ingredients =" + string_ingredients + ", in_amount =" +
					string_in_amount + ", images = "+ string_images+"]";
	}
	
}