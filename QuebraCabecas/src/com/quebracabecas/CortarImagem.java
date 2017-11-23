package com.quebracabecas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class CortarImagem {
	
	Context contexto; 
	ListaImagens listaImagens = new ListaImagens();
	Embaralhar Embaralhar = new Embaralhar();

	//
	int QtPecasX = 0, QtPecasY = 0;
	int tamXCorte = 0, tamYCorte = 0;
	//
	
	public CortarImagem (Context contexto) {
		this.contexto = contexto;
	}
	
	public ListaImagens CortarEmBlocos(Context contexto, int idImagem) {
		
		return this.CortarEmBlocos(contexto, idImagem, 0, 0);
	}
	
	public ListaImagens CortarEmBlocos(Context contexto, int idImagem, int qtX, int qtY) {

		return this.CortarEmBlocos(contexto, idImagem, 0, 0, qtX, qtY);
	}
	
	public ListaImagens CortarEmBlocos(Context contexto, int idImagem, int px, int py, int qtX, int qtY) {
		
		Bitmap bitmap  = BitmapFactory.decodeResource(contexto.getResources(), idImagem);
		return this.CortarEmBlocos(bitmap, px, py, qtX, qtY);
	}

	//
	public ListaImagens CortarEmBlocos(Bitmap bitmap) {
		
		return this.CortarEmBlocos(bitmap, 0, 0);
    }

	public ListaImagens CortarEmBlocos(Bitmap bitmap, int qtX, int qtY) {
		
		return this.CortarEmBlocos(bitmap, 0, 0, qtX, qtY);
	}
	
	public ListaImagens CortarEmBlocos(Bitmap bitmap, int px, int py, int qtX, int qtY) {
		
		return this.CortarEmBlocos(bitmap, 0, 0, qtX, qtY, 0, 0);
	}
	
	public ListaImagens CortarEmBlocos(Bitmap bitmap, int px, int py, int qtX, int qtY, int buracoX, int buracoY) {

		listaImagens.LimparImagens();
		
		//
		Imagem Img;
		
		if (qtX ==0)
			qtX = 3;
		if (qtY ==0)
			qtY = 3;
		
		buracoX--; 
		buracoY--;
		if (buracoX >= 0 && buracoY >= 0) {
			if (buracoX >= qtX)
				buracoX = qtX-1;
			if (buracoY >= qtY)
				buracoY = qtY-1;
		}
		
		this.tamXCorte = (int)Math.floor(bitmap.getWidth()/qtX);
		this.tamYCorte = (int)Math.floor(bitmap.getHeight()/qtY);
		
		this.QtPecasX = qtX;
		this.QtPecasY = qtY;
		//
		for (int y=0; y<qtY; y++) {
			
			List<Imagem> listaX = new ArrayList<Imagem>();
			
			for (int x=0; x<qtX; x++) {
				
				Img = CortarBloco(bitmap, x*tamXCorte, y*tamYCorte, tamXCorte, tamYCorte);

				Img.indiceXOrig = x; // prop não altera
				Img.indiceYOrig = y; // prop não altera
				Img.indiceX = x;
				Img.indiceY = y;
				Img.xDoIndiceAtual = x;
				Img.yDoIndiceAtual = y;
				
				Img.AjustarInicio(px, py);
		
				if (buracoX >= 0 && buracoY >= 0)
					Img.Visivel = !(y==buracoY && x==buracoX);
				
				//para um quebracabeça livre
				//Img.movimentoLivre = true;
				//Img.podeColidir = true;
				
				Img.listaImagens = this.listaImagens;
		        listaX.add(Img);
			}
			this.listaImagens.Imagens.add(listaX);
		}
		return this.listaImagens;
    }

	public Imagem CortarBloco(Context contexto, int idImagem) {
		
        return CortarBloco(contexto, idImagem, 0, 0, 0, 0);
	}
	
	//
	
	public Imagem CortarBloco(Context contexto, int idImagem, int xIniCorte, int yIniCorte, int tamXCorte, int tamYCorte) {
		
		Bitmap bitmap = BitmapFactory.decodeResource(contexto.getResources(), idImagem);
		
		return this.CortarBloco(bitmap, xIniCorte, yIniCorte, tamXCorte, tamYCorte);
	}
	
	public Imagem CortarBloco(Drawable drw, int xIniCorte, int yIniCorte, int tamXCorte, int tamYCorte) {
		
		Bitmap bitmap = Converter.DrawableParaBitmap(drw);
		
		return CortarBloco(bitmap, xIniCorte, yIniCorte, tamXCorte, tamYCorte);
	}
	
	public Imagem CortarBloco(Bitmap bitmap, int xIniCorte, int yIniCorte, int tamXCorte, int tamYCorte) {
		
		Imagem Img = new Imagem();
		
		int linhaEntreCortes = 1;
		
		if (xIniCorte < 0) xIniCorte = 0;
		if (yIniCorte < 0) yIniCorte = 0;
		if (xIniCorte > bitmap.getWidth())
			return Img;
		if (yIniCorte > bitmap.getHeight())
			return Img;
		
		if (tamXCorte <=0 || tamXCorte > bitmap.getWidth())
			tamXCorte = bitmap.getWidth();
		if (tamYCorte <=0 || tamYCorte > bitmap.getHeight())
			tamYCorte = bitmap.getHeight();
		
		tamXCorte = tamXCorte-linhaEntreCortes;
		tamYCorte = tamYCorte-linhaEntreCortes;
		
		Img.img     = Bitmap.createBitmap(bitmap, xIniCorte, yIniCorte, tamXCorte, tamYCorte);
		Img.x       = xIniCorte;
		Img.y       = yIniCorte;
		Img.largura = Img.img.getWidth();  // o mesmo que tamXCorte
		Img.altura  = Img.img.getHeight(); // o mesmo que tamYCorte
		
		//Início e Final da imagem inteira - para limitar cada bloco		
		Img.limiteXETela = 0;
		Img.limiteYCTela = 0;
		Img.limiteXDTela = (Img.largura + linhaEntreCortes) * QtPecasX -1;
		Img.limiteYBTela = (Img.altura + linhaEntreCortes) * QtPecasY -1;
		//Img.limiteXDTela = (Img.largura) * QtPecasX;
		//Img.limiteYBTela = (Img.altura) * QtPecasY;
		//
		Img.incX    = verIncremento(Img.largura + linhaEntreCortes);
		Img.incY    = verIncremento(Img.altura + linhaEntreCortes);
		//
		Img.setPodeIrParaDireita(true);
		Img.setPodeIrParaEsquerda(true);
		Img.setPodeIrParaCima(true);
		Img.setPodeIrParaBaixo(true);
		
		return Img;
	}

	private static int verIncremento(int tamanho) {
		int inc = 20;
		while (tamanho%inc != 0 && inc<tamanho) 
			inc++;
		
		return inc;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	
	public boolean VerificarFinalizou() {
		
		for (int y=0; y<this.listaImagens.Imagens.size(); y++)
			for (int x=0; x<this.listaImagens.Imagens.get(y).size(); x++)
				if (this.listaImagens.Imagens.get(y).get(x).indiceX != x || this.listaImagens.Imagens.get(y).get(x).indiceY != y)
					return false;
		
		Toast.makeText(contexto, "FINALIZADO!!! Parabéns! ", Toast.LENGTH_LONG).show();
		
		this.Embaralhar.Embaralhado = 0;
		
		return true;
	}

	public void AjustarPosicaoBloco(Imagem Img) {
		
		List<Imagem> objetos = Img.objetosMeIncluem(false, 5);
		
		if (objetos.size() == 0)
			return;
		
		if (objetos.size() == 1) {
			 if (!objetos.get(0).Visivel)
				 this.TrocarPosicaoPeloIndice(Img, objetos.get(0));
		}		
	}
	
	//
	
	public void TrocarPosicao(Imagem Img1, Imagem Img2) {
		
		this.TrocarPosicao(Img1, Img2, false);
	}

	public void TrocarPosicaoPeloIndice(Imagem Img1, Imagem Img2) {
		this.TrocarPosicao(Img1, Img2, true);
	}
	
	private void TrocarPosicao(Imagem Img1, Imagem Img2, boolean peloindiceXY) {
		
		int indiceXOld, indiceYOld, xOld, yOld;
		
		indiceXOld = Img1.indiceX;
		indiceYOld = Img1.indiceY;
		
		if(peloindiceXY) {
			xOld = Img1.xDoIndiceAtual;
			yOld = Img1.yDoIndiceAtual;
		}
		else {
			xOld = Img1.xDoIndiceAtual;
			yOld = Img1.yDoIndiceAtual;
		}
		
		//
		Img1.indiceX = Img2.indiceX;
		Img1.indiceY = Img2.indiceY;
		Img1.x = Img2.x;
		Img1.y = Img2.y;
		Img1.xDoIndiceAtual = Img2.x;
		Img1.yDoIndiceAtual = Img2.y;
		
		Img2.indiceX = indiceXOld;
		Img2.indiceY = indiceYOld;
		Img2.x = xOld;
		Img2.y = yOld;
		Img2.xDoIndiceAtual = xOld;
		Img2.yDoIndiceAtual = yOld;
	}

	public class Embaralhar {
		
		private int mov=-1, movOld;
		//
		public int QtdeVezes = 100;
		public int Embaralhado = 0;
		
		private void VerificarMovimentoETrocar(Imagem ImgBranco, int indiceX, int indiceY) {
			
			List<Imagem> objetos = ImgBranco.imagemNaPosicao(indiceX, indiceY, false);
			
			if (objetos.size() > 0)
				TrocarPosicao(objetos.get(0), ImgBranco);
		}
		
		public void Executar() {
			this.Executar(this.QtdeVezes);
		}
		
		public void Executar(int numeroMovimentos) {
			
			List<Imagem> lista = listaImagens.ImagensInvisiveis();
			if (lista.size() == 0)
				return;
			
			Imagem ImgBranco = lista.get(0);
			
			//
			Random movRandom = new Random();
			
			//this.Embaralhado++;
			
			for (int vezes=1; vezes <= numeroMovimentos; vezes++) {
				movOld = mov;
				mov = movRandom.nextInt(4);
				while (mov==0 && movOld==1 || mov==1 && movOld==0 || mov==2 && movOld==3 || mov==3 && movOld==2)
					mov = movRandom.nextInt(4);
				
				this.Embaralhado++;
				
				//mov = numeroMovimentos;
		    	switch (mov) {
			    	case 0:
		    			if (ImgBranco.indiceX > 0) {
		    				this.VerificarMovimentoETrocar(ImgBranco, ImgBranco.indiceX-1, ImgBranco.indiceY);
		    			}
		    		break;
			    	case 1:
		    			if (ImgBranco.indiceX < QtPecasX-1)
		    				this.VerificarMovimentoETrocar(ImgBranco, ImgBranco.indiceX+1, ImgBranco.indiceY);
		    		break;
			    	case 2:
		    			if (ImgBranco.indiceY > 0)
		    				this.VerificarMovimentoETrocar(ImgBranco, ImgBranco.indiceX, ImgBranco.indiceY-1);
		    		break;
			    	case 3:
		    			if (ImgBranco.indiceY < QtPecasY-1)
		    				this.VerificarMovimentoETrocar(ImgBranco, ImgBranco.indiceX, ImgBranco.indiceY+1);
		    		break;
		    	}
			}
		}
	}
	
}