package com.example.easycooking.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.example.easycooking.model.Image;
import com.example.easycooking.model.Ingredient;
import com.example.easycooking.model.Recipe;
import com.example.easycooking.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This class used chenlei's ESClient as reference
 * @author HongZu
 *
 */

public class WEBClient {
	// Http Connector
	private HttpClient httpclient = new DefaultHttpClient();

	// JSON Utilities
	private Gson gson = new Gson();

	
	
	private Recipe initializeRecipe() {
		Recipe r = new Recipe();
		Ingredient in = new Ingredient();
		Step s = new Step();
		Image im = new Image();
		r.setID("999");
		r.setName("Fried egg");
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		in.set_name("egg");
		in.set_amount("1");
		in.set_belongto("999");
		ingredients.add(in);
		r.setIngredients(ingredients);
		s.set_belong("999");
		s.set_detail("fry the egg");
		r.setSteps(s);
		r.setID("1");
		ArrayList<Image> images = new ArrayList<Image>();
		im.set_imageUri("0101111011100011010111101110001101011110111000110101111011100011");
		im.set_image_belongto("999");
		im.set_IMAGE_ID("1");
		images.add(im);
		r.setImages(images);
		return r;
	}
	/**
	 * This function is build to allow user to upload recipe to the Internet
	 * @param recipe
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void UploadRecipe(Recipe recipe) throws IllegalStateException, IOException{
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/lab02/"+recipe.getID());
		StringEntity stringentity = null;
		try {
			stringentity = new StringEntity(gson.toJson(recipe));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setHeader("Accept","application/json");

		httpPost.setEntity(stringentity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String status = response.getStatusLine().toString();
		Log.d("server", status);
		if(response.getStatusLine() == null){
			recipe.set_download_upload_own(100);
		}
	}

	/**
	 * This function is build to download recipe from web server to this APP,
	 * allow user to view the recipe that is on the Internet
	 * @param recipe
	 * @return recipe
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Recipe DownloadRecipe(Recipe recipe) throws ClientProtocolException, IOException{
			HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/CMPUT301W13T05/"+recipe.getID()+"?pretty=1");

			getRequest.addHeader("Accept","application/json");

			HttpResponse response = httpclient.execute(getRequest);

			String status = response.getStatusLine().toString();
			Log.d("server", status);

			String json = getEntityContent(response);

			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Recipe>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the recipe from it!
			Recipe download_recipe = esResponse.getSource();
			return download_recipe;
	}

	/**
	 * This function is build to search recipe by giving Ingredient
	 * @param str
	 * @return ArrayList<Recipe>
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<Recipe> searchRecipesWithIngredient(String[] keywords, int condition) throws ClientProtocolException, IOException {
		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
		HttpPost searchRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/CMPUT301W13T05/_search");
	
		for(int i=0; i<=keywords.length;i++){
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ingredients\",\"query\" : \"" + keywords[i] + "\"}}}";
			StringEntity stringentity = new StringEntity(query);
		
			searchRequest.setHeader("Accept","application/json");
			searchRequest.setEntity(stringentity);
		
			HttpResponse response = httpclient.execute(searchRequest);
			String status = response.getStatusLine().toString();
			Log.d("server", status);			
			String json = getEntityContent(response);
		
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Recipe>>(){}.getType();
			ElasticSearchSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			for (ElasticSearchResponse<Recipe> r : esResponse.getHits()) {
				Recipe recipe = r.getSource();
				result_recipe.add(recipe);
			}
		}
		return result_recipe;
	}
	/**
	 * This function is build to search recipe by giving dish name
	 * @param str
	 * @return ArrayList<Recipe>
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<Recipe> searchRecipesWithName(String[] keywords, int condition) throws ClientProtocolException, IOException {
		ArrayList<Recipe> result_recipe = new ArrayList<Recipe>();
		HttpPost searchRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/CMPUT301W13T05/_search");
	
		for(int i=0; i<=keywords.length;i++){
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"name\",\"query\" : \"" + keywords[i] + "\"}}}";
			StringEntity stringentity = new StringEntity(query);
		
			searchRequest.setHeader("Accept","application/json");
			searchRequest.setEntity(stringentity);
		
			HttpResponse response = httpclient.execute(searchRequest);
			String status = response.getStatusLine().toString();
			Log.d("server", status);			
			String json = getEntityContent(response);
		
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Recipe>>(){}.getType();
			ElasticSearchSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			for (ElasticSearchResponse<Recipe> r : esResponse.getHits()) {
				Recipe recipe = r.getSource();
				result_recipe.add(recipe);
			}
		}
		return result_recipe;
	}	


	/**
	 * get the http response and return json string
	 */
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}
}


