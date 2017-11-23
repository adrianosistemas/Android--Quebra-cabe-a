package com.quebracabecas;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;

public class ListaImagens {

	List<List<Imagem>> Imagens = new ArrayList<List<Imagem>>();
	
	public void LimparImagens() {
		
		if (Imagens == null)
			return;
		
		for (int y=0; y < Imagens.size(); y++)
			Imagens.get(y).clear();
		Imagens.clear();
	}

	public void MostrarImagens(Canvas canvas) {
		
		Imagem Img;
		for (int y=0; y < Imagens.size(); y++) 
	    	for (int x=0; x < Imagens.get(y).size(); x++) {
				
	    		Img = Imagens.get(y).get(x);
				if (Img.Visivel)
					canvas.drawBitmap(Img.img, Img.x, Img.y, null);
			}
	}
	
	public List<Imagem> ImagensInvisiveis() {
		
		List<Imagem> lista = new ArrayList<Imagem>();
		
		for (int y=0; y<Imagens.size(); y++)
			for (int x=0; x<Imagens.get(y).size(); x++)
				if (!Imagens.get(y).get(x).Visivel)
					lista.add(Imagens.get(y).get(x));
		
		return lista;
	}
	
	public void IncluirImagemNoInicio(Imagem Img) {
		
		if (Imagens.size()==0)
			Imagens.add(new ArrayList<Imagem>());
	
		Imagens.get(0).add(0, Img);
	}
	
	public void IncluirImagemNoFinal(Imagem Img) {
		
		if (Imagens.size()==0)
			Imagens.add(new ArrayList<Imagem>());
		
		Imagens.get(Imagens.size()-1).add(Img);
	}
	
	public void ExcluirImagem(Imagem Img) {
		
		for (int y=0; y < Imagens.size(); y++)
			Imagens.get(y).remove(Img);
	}
	
	//
	
	//----------------------------------------------------------------------------
	//Tratar a rotação automática da tela
	//----------------------------------------------------------------------------
	
	public ListaImagens CopiarPosicoesMemoria() {
		return this.CopiarPosicoesMemoria(true);
	}
	
	public ListaImagens CopiarPosicoesMemoria(boolean copiarPosicaoOriginal) {
		
		Imagem Img;
		Imagem nImg;
		ListaImagens listaImagensCopia = new ListaImagens();
		List<Imagem> listaX;
		
		for (int y=0; y < Imagens.size(); y++) {
			listaX = new ArrayList<Imagem>();
	    	for (int x=0; x < Imagens.get(y).size(); x++) {
	    		Img = Imagens.get(y).get(x);
	    		nImg = new Imagem();
	    		nImg.x = Img.x;
	    		nImg.y = Img.y;
	    		nImg.xDoIndiceAtual = Img.xDoIndiceAtual;
	    		nImg.yDoIndiceAtual = Img.yDoIndiceAtual;
	    		nImg.indiceX = Img.indiceX;
	    		nImg.indiceY = Img.indiceY;
	    		
	    		if (copiarPosicaoOriginal) {
		    		nImg.indiceXOrig = Img.indiceXOrig;
		    		nImg.indiceYOrig = Img.indiceYOrig;
	    		}
	    		
	    		listaX.add(nImg);
	    	}
	    	listaImagensCopia.Imagens.add(listaX);
		}

		for (int y=0; y < listaImagensCopia.Imagens.size(); y++)
	    	for (int x=0; x < listaImagensCopia.Imagens.get(y).size(); x++)
	    		listaImagensCopia.Imagens.get(y).get(x).listaImagens = listaImagensCopia;		
		
		return listaImagensCopia;
	}
	
	public void ResgatarPosicoesMemoria(ListaImagens listaImagensMemoria) {
		
		ListaImagens listaImagensCopia = CopiarPosicoesMemoria(); //copiarPosicoesMemoria(false);
		
		List<Imagem> listaPosAtual;
		
		Imagem ImgAtual;
		Imagem Img;
		Imagem ImgMem;
		Imagem posMem, posAtual;
		
		for (int y=0; y < Imagens.size(); y++) 
	    	for (int x=0; x < Imagens.get(y).size(); x++) {
	    		
	    		Img = listaImagensCopia.Imagens.get(y).get(x);
	    		ImgAtual = Imagens.get(y).get(x);
	    		ImgMem = listaImagensMemoria.Imagens.get(y).get(x);
				
				posMem = ImgMem.imagemNaPosicaoOriginal(x, y, false).get(0);
				
				listaPosAtual = Img.imagemNaPosicao(posMem.indiceX, posMem.indiceY, false);
				//if (listaPosAtual.size() > 0) //se a imagem não contiver a lista de imagens
				{
					posAtual = listaPosAtual.get(0);
					
					ImgAtual.x = posAtual.x;
					ImgAtual.y = posAtual.y;
					ImgAtual.xDoIndiceAtual = posAtual.xDoIndiceAtual;
					ImgAtual.yDoIndiceAtual = posAtual.yDoIndiceAtual;
					ImgAtual.indiceX = posAtual.indiceX;
					ImgAtual.indiceY = posAtual.indiceY;
				}
			}
	}
	
}
