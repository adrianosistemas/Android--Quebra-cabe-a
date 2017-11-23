package com.quebracabecas;

import android.content.Context;

public class TelaImagemDoArquivo extends Tela {
	
	public TelaImagemDoArquivo(Context contexto) {
		super(contexto);
    }

	public void GerarBackGround() {
		if (super.idbackground == 0)
			super.idbackground = R.drawable.b7;
		
		this.GerarBackGround(super.idbackground);
	}
	
	public void GerarBackGround(int backGround) {
		
		if (backGround==0)
			return;
		
		super.idbackground = backGround;
		
		Imagem Img = new Imagem();
		Img = super.GerarImagemTelaInteira(super.idbackground);
		Img.podeMexer = false;
		Img.podeColidir = true;
		Img.Visivel = true;
		
		super.outrasImagens.IncluirImagemNoInicio(Img);
	}
	
	public void GerarMiniatura() {
		this.GerarMiniatura(AG.imgArquivo);
	}
	
	public void GerarMiniatura(ImagensDoArquivo imgArquivo) {
		
		//Gera a em Miniatura
		super.GerarImagemDoArquivo(imgArquivo, 0, 0, 140, 190, false);
		imgArquivo.Img.movimentoLivre = true;
		imgArquivo.Img.podeColidir = true;
		imgArquivo.Img.Limites(0, 0, Tela.LarguraDispositivo, Tela.AlturaDispositivo);
		//REVER
		imgArquivo.Img.listaImagens = Blocos.listaImagens;
		
		//REVER
		super.outrasImagens.IncluirImagemNoFinal(imgArquivo.Img);
    }
	
	public ImagensDoArquivo GerarQuebraCabeca() {
		return this.GerarQuebraCabeca(AG.imgArquivo);
	}
	
	public ImagensDoArquivo GerarQuebraCabeca(ImagensDoArquivo imgArquivo) {
		
		int px = 0, py = 200;
    	//Gera o Quebra Cabeça
		if (Tela.LarguraDispositivo < Tela.AlturaDispositivo) {
		}
		else {
			px = 180;
			py = 0;
		}
		return super.GerarImagemCortadaDoArquivo(imgArquivo, px, py, 3, 4, Tela.LarguraDispositivo, Tela.AlturaDispositivo);
    }
}
