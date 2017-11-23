package com.quebracabecas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

public class Tela {
	
	public static int LarguraDispositivo;
	public static int AlturaDispositivo;
	
	public int idbackground = 0;
	//
	public CortarImagem Blocos = null;
	ListaImagens outrasImagens = null;
	//
	Context contexto; 
	//
	
	public Tela (Context contexto) {
		
		this.contexto = contexto;
		
		Blocos = new CortarImagem(contexto);
		outrasImagens = new ListaImagens();
		
		WindowManager wm = (WindowManager) contexto.getSystemService(contexto.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		this.LarguraDispositivo = display.getWidth();
		this.AlturaDispositivo = display.getHeight() - 70;
	}
	
	//
	
	public void GerarBackGround() {
	}
	public void GerarBackGround(int background) {
	}
	public void GerarMiniatura() {
	}
	public void GerarMiniatura(ImagensDoArquivo imgArquivo) {
	}
	public ImagensDoArquivo GerarQuebraCabeca() {
		return null;
	}
	public ImagensDoArquivo GerarQuebraCabeca(ImagensDoArquivo imgArquivo) {
		return null;
	}
	
	//---------------------------------------------------------------------------------------------------
	
	private ImagensDoArquivo verificarNomeArquivo(ImagensDoArquivo imgArquivo) {
		
		if (imgArquivo == null)
			imgArquivo = new ImagensDoArquivo();
		
		imgArquivo.BuscarImagemDoArquivo();
		
		return imgArquivo;
	}

	//Imagem do arquivo//
    public ImagensDoArquivo GerarImagemCortadaDoArquivo(ImagensDoArquivo imgArquivo, int px, int py, int qtX, int qtY) {
    	
    	imgArquivo = this.verificarNomeArquivo(imgArquivo);
    	
    	this.GerarImagemCortada(imgArquivo.bitmap, px, py, 0, 0, qtX, qtY);
    	
    	return imgArquivo;
	}
	
    public ImagensDoArquivo GerarImagemCortadaDoArquivo(ImagensDoArquivo imgArquivo, int px, int py, int qtX, int qtY, int ajustarLarguraTela, int ajustarAlturaTela) {
    	
    	imgArquivo = this.verificarNomeArquivo(imgArquivo);
    	
		this.GerarImagemCortada(imgArquivo.bitmap, px, py, qtX, qtY, ajustarLarguraTela, ajustarAlturaTela);
    	
    	return imgArquivo;
	}

	public ImagensDoArquivo GerarImagemDoArquivo(ImagensDoArquivo imgArquivo, int px, int py, int larguraDesejada, int alturaDesejada, boolean telaInteira) {
		
		imgArquivo = this.verificarNomeArquivo(imgArquivo);
		
		imgArquivo.Img = this.GerarImagem(imgArquivo.bitmap, px, py, larguraDesejada, alturaDesejada, telaInteira);

    	return imgArquivo;
	}
	
	//Imagem Cortada//
		
	public ListaImagens GerarImagemCortada(int idImagem, int px, int py, int qtX, int qtY, int ajustarLarguraTela, int ajustarAlturaTela) {
		
		Drawable drw = contexto.getResources().getDrawable(idImagem);
		
		return this.GerarImagemCortada(drw, px, py, qtX, qtY, ajustarLarguraTela, ajustarAlturaTela);
	}
	
	public ListaImagens GerarImagemCortada(Drawable drw, int px, int py, int qtX, int qtY, int ajustarLarguraTela, int ajustarAlturaTela) {

		ListaImagens lista = new ListaImagens();
		
		if (drw == null)
			return lista;
		
		Bitmap bitmap = Converter.DrawableParaBitmap(drw);
		
		return this.GerarImagemCortada(bitmap, px, py, qtX, qtY, ajustarLarguraTela, ajustarAlturaTela);
	}
	
	public ListaImagens GerarImagemCortada(Bitmap bitmap, int px, int py, int qtX, int qtY, int ajustarLarguraTela, int ajustarAlturaTela) {
		
		ListaImagens lista = new ListaImagens();
		
		if (bitmap == null)
			return lista;
		
		//Caso a imagem seja maior que a tela do dispositivo
    	bitmap = Imagem.AjustarNaTela(contexto, bitmap, ajustarLarguraTela-px, ajustarAlturaTela-py, false);
    	
		//Iniciando a criação do que será mostrado na tela
    	return Blocos.CortarEmBlocos(bitmap, px, py, qtX, qtY, qtX, qtY);
	}
	
	//Imagem Comum//
	
	public Imagem GerarImagemTelaInteira(int idImagem) {
		
		return this.GerarImagem(idImagem, 0, 0, 0, 0, true);
	}

	public Imagem GerarImagem(int idImagem, int px, int py, int larguraDesejada, int alturaDesejada) {
		
		return this.GerarImagem(idImagem, px, py, larguraDesejada, alturaDesejada, false);
	}

	public Imagem GerarImagem(int idImagem, int px, int py, int larguraDesejada, int alturaDesejada, boolean telaInteira) {
		
		Drawable drw = contexto.getResources().getDrawable(idImagem);
		
		return this.GerarImagem(drw, px, py, larguraDesejada, alturaDesejada, telaInteira);
	}

	public Imagem GerarImagemTelaInteira(Drawable drw) {
		
		return this.GerarImagem(drw, 0, 0, 0, 0, true);
	}
	
	public Imagem GerarImagem(Drawable drw, int px, int py, int larguraDesejada, int alturaDesejada) {
		
		return this.GerarImagem(drw, px, py, larguraDesejada, alturaDesejada, false);
	}
	
	public Imagem GerarImagem(Drawable drw, int px, int py, int larguraDesejada, int alturaDesejada, boolean telaInteira) {
		
		if (drw == null)
			return null;
		
		Bitmap bitmap = Converter.DrawableParaBitmap(drw);
		
		return this.GerarImagem(bitmap, px, py, larguraDesejada, alturaDesejada, telaInteira);
	}

	//
	
	public Imagem GerarImagemTelaInteira(Bitmap bitmap) {
		
		return this.GerarImagem(bitmap, 0, 0, 0, 0, true);
	}
	
	public Imagem GerarImagem(Bitmap bitmap, int px, int py, int larguraDesejada, int alturaDesejada) {

		
		return this.GerarImagem(bitmap, px, py, larguraDesejada, alturaDesejada, false);
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------
	//Lembrar que este método apenas cria a imagem e não insere na lista de imagens
	//Cortar.listaImagens.get(Cortar.listaImagens.size()-1).add(Img);
	//-----------------------------------------------------------------------------------------------------------------

	//Gera uma imagem em miniatura (ou tamanho que quiser)
	public Imagem GerarImagem(Bitmap bitmap, int px, int py, int larguraDesejada, int alturaDesejada, boolean telaInteira) {
		
		if (bitmap == null)
			return null;
			
		bitmap = Imagem.AjustarNaTela(contexto, bitmap, larguraDesejada, alturaDesejada, telaInteira);
		
		Imagem Img = Blocos.CortarBloco(bitmap, 0, 0, 0, 0);
		//x
		return Img;
	}

	/*
	public void GerarImagemCortada(Bitmap bitmap, int px, int py, int largura, int altura, int larguraTela, int alturaTela, int qtX, int qtY) {

		if (bitmap == null)
			return;
		
		bitmap = Imagem.AjustarNaTela(contexto, bitmap, largura, altura, false);
		
		CortarImagem miniatura = new CortarImagem(contexto);
		miniatura.CortarEmBlocos(bitmap, 0, 0, qtX, qtY, 0, 0);
		Cortar.listaImagens.addAll(miniatura.listaImagens);
	}
	*/
	
}