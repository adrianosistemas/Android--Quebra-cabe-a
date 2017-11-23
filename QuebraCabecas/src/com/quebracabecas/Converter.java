package com.quebracabecas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Converter {

	public static Drawable BitMapParaDrawable(Context contexto, Bitmap bitmap) {

		if (bitmap == null)
			return null;
		
		return Converter.BitMapParaImageView(contexto, bitmap).getDrawable();
	}
	
	public static ImageView BitMapParaImageView(Context contexto, Bitmap bitmap) {
		
		if (bitmap == null)
			return null;
		
		ImageView img = new ImageView(contexto);
		img.setImageBitmap(bitmap);
		
		return img;
	}
	
	// ------------------------------------------------------------------------------------
	
	public static Bitmap DrawableParaBitmap(Drawable drw) {
		
		if (drw == null)
			return null;
		
		Canvas canvas = new Canvas();
		
		//REVER
	    //int largura = canvas.getWidth();
	    //int altura = canvas.getHeight();
		int largura = drw.getIntrinsicWidth();
	    int altura = drw.getIntrinsicHeight();

	    return Converter.DrawableParaBitmap(drw, largura, altura);
	}
	
	public static Bitmap DrawableParaBitmap(Drawable drw, int largura, int altura) {
		
		if (drw == null)
			return null;
		/*
		if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }
	    */
	    if (largura==0)
	    	 largura = drw.getIntrinsicWidth();
	    if (altura==0)
	    	 altura = drw.getIntrinsicHeight();
	    
	    Bitmap bitmap = Bitmap.createBitmap(largura, altura, Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
	    
	    //drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    
	    drw.setBounds(0, 0, largura, altura);
	    drw.draw(canvas);
	    
	    return bitmap;
	}
	
}