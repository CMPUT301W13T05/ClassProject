package com.example.easycooking.controller;

import java.util.ArrayList;

import com.example.easycooking.model.*;

/**
 * This class contains some functions that make our life easier.
 * @author HongZu
 */
public class UsefulFunctions {
	/**
	 * This operation receives a ArrayList<Recipe> that may contain 2 or more same recipes
	 * the operation will pick distinct recipes and put them to a new ArrayList<Recipe>
	 * then return this ArrayList<Recipe>
	 * @param inputList
	 * @return ArrayList<Recipe>
	 */
	public ArrayList<Recipe> Unique(ArrayList<Recipe> inputList){
		ArrayList<String> uniqueString = new ArrayList<String>();
		ArrayList<Recipe> uniqueList= new ArrayList<Recipe>();
		
		for(int i = 0;i<inputList.size();i++){
			if(!uniqueString.contains(inputList.get(i).getID())){
				uniqueString.add(inputList.get(i).getID());
				uniqueList.add(inputList.get(i));
			}
		}
		return uniqueList;
	}
}                                                                                                                                                                                                                                                                                                                    

