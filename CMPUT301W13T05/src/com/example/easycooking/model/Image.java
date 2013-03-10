package com.example.easycooking.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;

public class Image implements Serializable{

	private static final long serialVersionUID = 1L;
	private String  IMAGE_ID,image_belongto;
	private Bitmap image_bit;
	public Image(){
		
	}
	public Image(String IMAGE_ID,String image_belongto){
		super();
		this.IMAGE_ID = IMAGE_ID;
		this.image_belongto = image_belongto;			
	}
	public String get_IMAGE_ID(){
		return this.IMAGE_ID;
	}
	public void set_IMAGE_ID(String IMAGE_ID){
		this.IMAGE_ID = IMAGE_ID;
	}
	public String get_image_belongto(){
		return this.image_belongto;
	}
	public void set_image_belongto(String image_belongto){
		this.image_belongto = image_belongto;
	}
	public byte[] get_imageBlob(){
		image_bit = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
		int size = image_bit.getWidth()*image_bit.getHeight()*4; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(size); 
		byte[] imagedata = baos.toByteArray();
		return imagedata;
	}
	public void set_imageBit(Bitmap image_bit) {
		this.image_bit = image_bit;
	}
}
