package com.example.easycooking.controller;


import java.util.ArrayList;

import com.example.easycooking.model.GalleryFlow;
import com.example.easycooking.model.Image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public  class ImageAdapter extends BaseAdapter {     
    int mGalleryItemBackground;     
    private Context mContext;     
    private ArrayList<Image> mImageList;     
    private ImageView[] mImages;     
         
    public ImageAdapter(Context c, ArrayList<Image> ImageList) {     
     mContext = c;     
     mImageList = ImageList;     
     mImages = new ImageView[mImageList.size()];     
    }     
    /**   
     * 
     * @return   
     */    
    public boolean createReflectedImages() {     
    
     final int reflectionGap = 4;     
     int index = 0;     
     for (Image imageId : mImageList) {     
  
      Bitmap originalImage = stringtoBitmap(imageId.get_imageUri());    
      //System.out.println("uri="+imageId.get_imageUri());
      int width = originalImage.getWidth();     
      int height = originalImage.getHeight();     
 
      Matrix matrix = new Matrix();     
           
     
      // matrix.setRotate(30);     
           
     
      matrix.preScale(1, -1);     
           
      
      Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,     
        height/2, width, height/2, matrix, false);     
           
   
      Bitmap bitmapWithReflection = Bitmap.createBitmap(width,     
        (height + height / 2), Config.ARGB_8888);     
           
     
      Canvas canvas = new Canvas(bitmapWithReflection);     
      canvas.drawBitmap(originalImage, 0, 0, null);     
           
      Paint deafaultPaint = new Paint();      
      deafaultPaint.setAntiAlias(false);     
//    canvas.drawRect(0, height, width, height + reflectionGap,deafaultPaint);     
      canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);     
      Paint paint = new Paint();     
      paint.setAntiAlias(false);     
            
      
      LinearGradient shader = new LinearGradient(0,originalImage.getHeight(), 0,     
              bitmapWithReflection.getHeight() + reflectionGap,0x70ffffff, 0x00ffffff, TileMode.MIRROR);     
    
      paint.setShader(shader);     
      paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));     
  
      canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()+ reflectionGap, paint);     
           
      
      ImageView imageView = new ImageView(mContext);     
      imageView.setImageBitmap(bitmapWithReflection);     
     
      imageView.setLayoutParams(new GalleryFlow.LayoutParams(300, 400));     
      //imageView.setScaleType(ScaleType.MATRIX);     
      mImages[index++] = imageView;   

     }     
     return true;     
    }     
    @SuppressWarnings("unused")     
    private Resources getResources() {     
        return null;     
    }     
    public int getCount() {     
        return mImageList.size();     
    }     
    public Object getItem(int position) {     
        return position;     
    }     
    public long getItemId(int position) {     
        return position;     
    }     
    public View getView(int position, View convertView, ViewGroup parent) {     
        return mImages[position];     
    }     
    public float getScale(boolean focused, int offset) {    
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));     
    }    
    public Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
                byte[] bitmapArray;
                bitmapArray = Base64.decode(string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                                bitmapArray.length);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return bitmap;
    }
   }    
