package com.quebracabecas;

import java.io.File;
import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

public class ImagensDoArquivo {
	
	String nomearquivo = "";
	File f;
	Bitmap bitmap;
	Imagem Img = null;
	
	public void BuscarImagemDoArquivo() {
		
		if (nomearquivo.trim().equals("") || bitmap == null)
			this.BuscarImagemDoArquivo(nomearquivo);
	}
	
	public void BuscarImagemDoArquivo(String nomearquivo) {
		
		this.nomearquivo = nomearquivo;
		if (!this.nomearquivo.trim().equals("")) {
			//this.bitmap = BitmapFactory.decodeFile(this.nomearquivo);
			this.AjustarOrientacaoImagem(this.nomearquivo);
		}
    	else
    	{
    		////drw = ArquivoImagem.SortearDrawableDaPastaCamera(contexto);
    		////mudou para
    		this.nomearquivo = ImagensDaCamera.SortearNomeDaPastaCamera();
    		//this.bitmap = BitmapFactory.decodeFile(this.nomearquivo);
    		this.AjustarOrientacaoImagem(this.nomearquivo);
    		
    		////this.bitmap = ImagensDaCamera.ProcurarBitmapNaPastaCamera(this.nomearquivo);
    	}
	}
	
	//
	
	public void onActivityResult(Context contexto, int requestCode, int resultCode, Intent data, boolean carregarAuto) {
		
		//if (resultCode != android.app.Activity.RESULT_OK)
		//	return;
				
		/*if (requestCode == MeuRetornoCamera) {
			Uri imagemSelecionada = data.getData();
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(imagemSelecionada.getPath());
            Toast.makeText(this, "" + imagemSelecionada.getPath(), Toast.LENGTH_SHORT).show();
		}*/
		
		if (requestCode == AG.imagemSelecionada) {
			
			Uri imagemSelecionada = data.getData();
            String[] colunas = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
            Cursor cursor = contexto.getContentResolver().query(imagemSelecionada, colunas, null, null, null);
            
            cursor.moveToFirst();
            int indice = cursor.getColumnIndex(colunas[0]);
            int graus = cursor.getColumnIndex(colunas[1]);
            
            this.nomearquivo = cursor.getString(indice);
            
            if(carregarAuto) {
	            if (graus!=0)
	            	this.AjustarOrientacaoImagem(this.nomearquivo);
	            else
	            	bitmap = BitmapFactory.decodeFile(this.nomearquivo);
            }
		}
    }
	
	public void AjustarOrientacaoImagem(int graus) {
		//
        Matrix matrix = new Matrix();
	    matrix.postRotate(graus);
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	public void AjustarOrientacaoImagem(String filePath) {
		//
		bitmap = BitmapFactory.decodeFile(filePath);
		int graus = getExifOrientation(filePath);
		if (graus != 0) {
			Matrix matrix = new Matrix();
        	matrix.postRotate(graus);
        	bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
	}

	public static int getExifOrientation(String filePath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                }
            }
        }
        return degree;
    }	

}