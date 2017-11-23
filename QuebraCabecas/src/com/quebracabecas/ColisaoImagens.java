package com.quebracabecas;

public class ColisaoImagens {

	public static boolean Colidem(Imagem imagem1, Imagem imagem2) {
		
		boolean c1 = pontoEstaNaImagem(imagem1, imagem2.x, imagem2.y) ||
				     pontoEstaNaImagem(imagem1, imagem2.x, imagem2.y + imagem2.altura) ||
                     pontoEstaNaImagem(imagem1, imagem2.x + imagem2.largura, imagem2.y) ||
                     pontoEstaNaImagem(imagem1, imagem2.x + imagem2.largura, imagem2.y + imagem2.altura);
	
		boolean c2 = pontoEstaNaImagem(imagem2, imagem1.x, imagem1.y) ||
			         pontoEstaNaImagem(imagem2, imagem1.x, imagem1.y + imagem1.altura) ||
                     pontoEstaNaImagem(imagem2, imagem1.x + imagem1.largura, imagem1.y) ||
                     pontoEstaNaImagem(imagem2, imagem1.x + imagem1.largura, imagem1.y + imagem1.altura);
		
		return c1 || c2;
	}
	
	/*
	public static boolean Inclui(Imagem imagem1, Imagem imagem2) {
		
		return EstahIncluso(imagem2, imagem1, 100);
	}
	*/
	
	public static boolean Inclui(Imagem imagem1, Imagem imagem2, int percentualDentro) {
		
		return EstahIncluso(imagem2, imagem1, 100);
	}
	
	/*
	public static boolean EstahIncluso(Imagem imagem1, Imagem imagem2) {
		return EstahIncluso(imagem1, imagem2, 100);
	}
	*/
	
	public static boolean EstahIncluso(Imagem imagem1, Imagem imagem2, int percentualDentro) {
		
		int vl = (int)(imagem1.largura * ((float)(100-percentualDentro)/100));
		int va = (int)(imagem1.altura * ((float)(100-percentualDentro)/100));
		
		//Imagem aux = new Imagem(imagem1.getContext());
		Imagem aux = new Imagem();
		
		aux.x = imagem2.x - vl;
		aux.y = imagem2.y - va;
		aux.largura = imagem2.largura + 2*vl;
		aux.altura = imagem2.altura + 2*va;
		
		boolean c = pontoEstaNaImagem(aux, imagem1.x, imagem1.y) &&
				    pontoEstaNaImagem(aux, imagem1.x, imagem1.y + imagem1.altura) &&
                    pontoEstaNaImagem(aux, imagem1.x + imagem1.largura, imagem1.y) &&
                    pontoEstaNaImagem(aux, imagem1.x + imagem1.largura, imagem1.y + imagem1.altura);
		
		return c;
	}
	
	public static boolean pontoEstaNaImagem(Imagem imagem, int x, int y) {
		return (x >= imagem.x && x <= imagem.x + imagem.largura) &&
			   (y >= imagem.y && y <= imagem.y + imagem.altura);
	}
	
}
