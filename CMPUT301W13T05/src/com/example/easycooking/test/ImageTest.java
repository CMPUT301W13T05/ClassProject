package com.example.easycooking.test;
/**
 * This class is testing the Image model
 * @author Chenkun
 *
 */
import static org.junit.Assert.*;
import junit.framework.TestCase;

import com.example.easycooking.model. *;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageTest extends TestCase{

	public void testGetType() {
		Image image = new Image("1","imagetestrid", "1010101010101");
		assertEquals("1", image.get_IMAGE_ID());
	}

	public void testGetAmount() {
		Image image = new Image("1","imagetestrid", "1010101010101");
		assertEquals("imagetestrid", image.get_image_belongto());
	}

	public void testGetUnit() {
		Image image = new Image("1","imagetestrid", "1010101010101");
		assertEquals("1010101010101", image.get_imageUri());
	}
	
}	
	
