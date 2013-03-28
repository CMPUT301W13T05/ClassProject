package com.example.easycooking.controller;

import java.util.ArrayList;

import com.example.easycooking.model.*;

/**
 * This class contains some functions make our life easier.
 * @author HongZu
 */
public class UsefulFunctions {
	public ArrayList<Recipe> Unique(ArrayList<Recipe> inputList){
		ArrayList<String> uniqueString = new ArrayList<String>();
		ArrayList<Recipe> uniqueList= new ArrayList<Recipe>();
		for(int i = 0;i<inputList.size();i++){
			if(!uniqueString.contains(inputList.get(i).getID())){
				uniqueString.add(uniqueList.get(i).getID());
				uniqueList.add(inputList.get(i));
			}
		}
		return uniqueList;
	}
}                                                                                                                                                                                                                                                                                                                    

