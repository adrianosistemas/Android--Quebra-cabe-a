package com.quebracabecas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class ImagensDaCamera extends ImagensDoArquivo {

public void BuscarImagemNaPastaCamera(String nomearquivo) {
		
		this.nomearquivo = nomearquivo;
		if (!this.nomearquivo.trim().equals("")) {
			this.bitmap = ImagensDaCamera.ProcurarBitmapNaPastaCamera(this.nomearquivo);
		}
		else
		{
			//drw = ArquivoImagem.SortearDrawableDaPastaCamera(contexto);
			//mudou para
			this.nomearquivo = ImagensDaCamera.SortearNomeDaPastaCamera();
			this.bitmap = ImagensDaCamera.ProcurarBitmapNaPastaCamera(this.nomearquivo);
		}
	}
	
	//----------------------------------------------------------------------------------------
	
	/*//Método não estático
	public List<ImagensDaCamera> TodasImagensDaPastaCamera() {
		
		List<File> imagens = ImagensDaCamera.ListaArquivosDaPastaCamera();
		List<ImagensDaCamera> lista = new ArrayList<ImagensDaCamera>();
		
		ImagensDaCamera img;
		int inicio = imagens.size();
		
		for (int i=0; i<imagens.size(); i++) {
			img = new ImagensDaCamera();
			img.f = imagens.get(i);
			Options op = new Options();
			op.inSampleSize = 4;
			img.bitmap = BitmapFactory.decodeFile(img.f.getPath(), op);
			lista.add(img);
		}
		
		return lista;
	}*/
	
	//----------------------------------------------------------------------------------------
	
	public static List<File> ListaArquivosDaPastaCamera() {
		
		//File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/camera");
		File dir = Environment.getExternalStoragePublicDirectory("DCIM" + "/camera");
		
		List<File> imagens = new ArrayList<File>();
		
		for(File img: dir.listFiles()) {
			
			if (img.getName().toLowerCase().contains(".jpg") || img.getName().toLowerCase().contains(".png"))
				imagens.add(img);
		}
		return imagens;
	}
	
	//

	public static File ProcurarImagemNaPastaCamera(String nomearquivo) {
		
		List<File> imagens = ImagensDaCamera.ListaArquivosDaPastaCamera();
		File img = null;
		
		if (nomearquivo == null)
			return null;
		
		for (int i=0; i<imagens.size() && img==null; i++)
			if (nomearquivo.toLowerCase().contains(imagens.get(i).getName().toLowerCase()))
				img = imagens.get(i);
			
		return img;
	}
	
	//REVER
	public static Drawable ProcurarDrawableNaPastaCamera(Context contexto, String nomearquivo) {
		
		Bitmap bitmap = ImagensDaCamera.ProcurarBitmapNaPastaCamera(nomearquivo);
		
		if (bitmap == null)
			return null;
		
		return Converter.BitMapParaImageView(contexto, bitmap).getDrawable();
	}
	
	public static Bitmap ProcurarBitmapNaPastaCamera(String nomearquivo) {
		
		File f = ImagensDaCamera.ProcurarImagemNaPastaCamera(nomearquivo);
		if (f == null)
			return null;
		
		return BitmapFactory.decodeFile(f.getPath());
	}

	//----------------------------------------------------------------------------------------
	
	public static int SortearNumeroImagemDaPastaCamera() {
		
		List<File> imagens = ImagensDaCamera.ListaArquivosDaPastaCamera();
		
		if (imagens.size() == 0)
			return -1;
		
		Random num = new Random();
		
		return num.nextInt(imagens.size());
	}

	public static File SortearArquivoDaPastaCamera() {
		
		List<File> imagens = ImagensDaCamera.ListaArquivosDaPastaCamera();
		
		int numSorteado = ImagensDaCamera.SortearNumeroImagemDaPastaCamera();
		
		return imagens.get(numSorteado);
	}
	
	public static String SortearNomeDaPastaCamera() {
		
		return ImagensDaCamera.SortearArquivoDaPastaCamera().getPath();
	}

	public static Bitmap SortearBitmapDaPastaCamera() {
		
		return BitmapFactory.decodeFile( ImagensDaCamera.SortearNomeDaPastaCamera() );
	}

	//REVER
	public static Drawable SortearDrawableDaPastaCamera(Context contexto) {
		
		Bitmap bitmap = ImagensDaCamera.SortearBitmapDaPastaCamera();
		
		return Converter.BitMapParaDrawable(contexto, bitmap);
	}

}