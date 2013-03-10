package com.example.easycooking.model;

import java.io.Serializable;


public class Ingredient implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ingredients_name, ingredients_amount,ingredients_belongto;
	public Ingredient(){
		
	}
	public Ingredient(String name,String amount,String ingredients_belongto){
		super();
		this.ingredients_name = name;
		this.ingredients_amount = amount;
		this.ingredients_belongto = ingredients_belongto;
				
	}
	public String get_name(){
		return this.ingredients_name;
	}
	public String get_amount(){
		return this.ingredients_amount;
	}
	public void set_name(String name){
		this.ingredients_name = name;
	}
	public void set_amount(String amount){
		this.ingredients_amount = amount;
	}
	public String get_belongto(){
		return this.ingredients_belongto;
	}
	public void set_belongto(String belongto){
		this.ingredients_belongto = belongto;
	}
}
