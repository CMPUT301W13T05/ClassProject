package com.example.easycooking.model;

import java.io.Serializable;


public class Image implements Serializable{

	private static final long serialVersionUID = 1L;
	private String  IMAGE_ID,image_belongto;
	private String image_uri;
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
	public String get_imageUri() {
		return this.image_uri;
	}
	public void set_imageUri(String IMAGE_URI) {
		this.image_uri = IMAGE_URI;
	}
	/*public byte[] get_imageBit(){
		return this.image_bit;
		////////////////////////////////////
		int size = image_bit.getWidth()*image_bit.getHeight()*4; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(size); 
		byte[] imagedata = baos.toByteArray();
		return imagedata;
	}*/
	/*public void set_imageBit(Bitmap image_bit) {
		this.image_bit = image_bit;
	}*/
}
