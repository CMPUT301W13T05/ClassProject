package com.example.easycooking.model;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * This is GalleryFlow which is extends the Gallery
 * It rewrite some functions and add some functions
 * to create the 3D view and reflection effects also
 * asign several parameter
 * @author Adonis
 *
 */
public class GalleryFlow extends Gallery{
	private Camera mCamera = new Camera();
    private int mMaxRotationAngle = 60;
    private int mMaxZoom = -300;
    private int mCoveflowCenter;
	public GalleryFlow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setStaticTransformationsEnabled(true);
	}
	public GalleryFlow(Context context, AttributeSet attrs) {      
        super(context, attrs);      
        this.setStaticTransformationsEnabled(true);      
    }      
    public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {      
        super(context, attrs, defStyle);      
        this.setStaticTransformationsEnabled(true);      
    }      
    public int getMaxRotationAngle() {      
        return mMaxRotationAngle;      
    }      
    public void setMaxRotationAngle(int maxRotationAngle) {      
        mMaxRotationAngle = maxRotationAngle;      
    }      
    public int getMaxZoom() {      
        return mMaxZoom;      
    }      
    public void setMaxZoom(int maxZoom) {      
        mMaxZoom = maxZoom;      
    }      
    private int getCenterOfCoverflow() {      
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2     
                        + getPaddingLeft();      
    }      
    private static int getCenterOfView(View view) {      
        System.out.println("view left :"+view.getLeft());      
        System.out.println("view width :"+view.getWidth());      
        return view.getLeft() + view.getWidth() / 2;      
    }      
          
          
    /**
     * control each pics in the gallery
     * rewrite the function of gallery
     * it will calculate the rotation angle by the 
     * the position of the pics in the gallery
     */
    protected boolean getChildStaticTransformation(View child, Transformation t) {        
    	/**
    	 * get the r of the current view
    	 */
        final int childCenter = getCenterOfView(child);      
        System.out.println("childCenter£º"+childCenter);      
        final int childWidth = child.getWidth();      
         /**
          * the angle 
          * and reset the status
          */
        int rotationAngle = 0;      
       
        t.clear();      
       /**
        * this is the type of transformat 
        */
        t.setTransformationType(Transformation.TYPE_MATRIX);      

        if (childCenter == mCoveflowCenter) {      
            transformImageBitmap((ImageView) child, t, 0);      
        } else {      
            
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);      
            System.out.println("rotationAngle:" +rotationAngle);      
            
            if (Math.abs(rotationAngle) > mMaxRotationAngle) {      
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;      
            }      
            transformImageBitmap((ImageView) child, t, rotationAngle);      
        }      
        return true;      
    }      
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {      
        mCoveflowCenter = getCenterOfCoverflow();      
        super.onSizeChanged(w, h, oldw, oldh);      
    }   
    /**
     * this function are used to save the affect and set the 
     * viewpot of the camera by the real height and width of 
     * the pics
     * @param child
     * @param t
     * @param rotationAngle
     */
    private void transformImageBitmap(ImageView child, Transformation t,      
                    int rotationAngle) {      
        mCamera.save();      
        final Matrix imageMatrix = t.getMatrix();          
        final int imageHeight = child.getLayoutParams().height;       
        final int imageWidth = child.getLayoutParams().width;       
        final int rotation = Math.abs(rotationAngle);             
        mCamera.translate(0.0f, 0.0f, 100.0f);      
 
        if (rotation < mMaxRotationAngle) {      
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));      
            mCamera.translate(0.0f, 0.0f, zoomAmount);      
        }      
   
        mCamera.rotateY(rotationAngle);      
        mCamera.getMatrix(imageMatrix);      
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));      
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));      
        mCamera.restore();      
    }      
}    


