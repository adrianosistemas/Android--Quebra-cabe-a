package com.quebracabecas;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;

//public class Imagem extends View {
public class Imagem {
	
	/*
	public Imagem(Context context) {
		super(context);		
	}
	*/
	
	Bitmap img;
	int indiceX, indiceY;
	int indiceXOrig, indiceYOrig;
	
	int x, y;
	int xDoIndiceAtual, yDoIndiceAtual;
	
	boolean Visivel = true;
	boolean podeMexer = true;
	boolean movimentoLivre = false;
	
	int incX, incY;
	int largura, altura;
	int limiteXDTela, limiteYBTela, limiteXETela, limiteYCTela;
	
	public boolean podeColidir = false;
	public ListaImagens listaImagens = null;
	
	private boolean indoParaBaixo = false;
	private boolean indoParaCima = false;
	private boolean indoParaDireita = false;
	private boolean indoParaEsquerda = false;
	
	private boolean podeIrParaDireita = false;
	private boolean podeIrParaEsquerda = false;
	private boolean podeIrParaCima = false;
	private boolean podeIrParaBaixo = false;

	public void AjustarInicio(int largura, int altura) {
		if  (largura!=0) {
			this.x += largura;
			this.limiteXDTela += largura;
			this.limiteXETela += largura;
		}
		if  (altura!=0) {
			this.y += altura;
			this.limiteYBTela += altura;
			this.limiteYCTela += altura;
		}
		
		this.xDoIndiceAtual = x;
		this.yDoIndiceAtual = y;
	}
	
	public void Limites(int xE, int yC, int xD, int YB) {

		this.limiteXETela = xE;
		this.limiteYCTela = yC;
		//
		this.limiteXDTela = xD;
		this.limiteYBTela = YB;
		//REVER
		//Img.limiteXDTela = larguraTela - largura - px;
		//Img.limiteYBTela = alturaTela - altura - py;
	}
	
	public boolean estahIndoParaBaixo() {
		return indoParaBaixo;
	}

	public boolean setIndoParaBaixo(boolean indoParaBaixo) {
		if (this.podeIrParaBaixo() && this.podeMexer)
		{
			this.indoParaBaixo = indoParaBaixo;
			if (this.indoParaBaixo)
				this.indoParaCima = false;
			return true;
		}
		return false;
	}

	public boolean estahIndoParaCima() {
		return indoParaCima;
	}

	public boolean setIndoParaCima(boolean indoParaCima) {
		if (this.podeIrParaCima() && this.podeMexer)
		{
			this.indoParaCima = indoParaCima;
			if (this.indoParaCima)
				this.indoParaBaixo = false;
			return true;
		}
		return false;
	}

	public boolean estahIndoParaDireita() {
		return indoParaDireita;
	}

	public boolean setIndoParaDireita(boolean indoParaDireita) {
		if (this.podeIrParaDireita() && this.podeMexer)
		{
			this.indoParaDireita = indoParaDireita;
			if (this.indoParaDireita)
				this.indoParaEsquerda = false;
			return true;
		}
		return false;
	}

	public boolean estahIndoParaEsquerda() {
		return indoParaEsquerda;
	}

	public boolean setIndoParaEsquerda(boolean indoParaEsquerda) {
		if (this.podeIrParaEsquerda() && this.podeMexer)
		{
			this.indoParaEsquerda = indoParaEsquerda;
			if (this.indoParaEsquerda) 
				this.indoParaDireita = false;
			return true;
		}
		return false;
	}

	public boolean estahIndoNaHorizontal() {
		return indoParaEsquerda || indoParaDireita;
	}

	public boolean estahIndoNaVertical() {
		return indoParaCima || indoParaBaixo;
	}
	
	//
	
	public boolean podeIrParaDireita() {
		return this.podeIrParaDireita;
	}	

	public void setPodeIrParaDireita(boolean podeIrParaDireita) {
		this.podeIrParaDireita = podeIrParaDireita;
	}

	public boolean podeIrParaEsquerda() {
		return this.podeIrParaEsquerda;
	}

	public void setPodeIrParaEsquerda(boolean podeIrParaEsquerda) {
		this.podeIrParaEsquerda = podeIrParaEsquerda;
	}

	public boolean podeIrParaCima() {
		return this.podeIrParaCima;
	}

	public void setPodeIrParaCima(boolean podeIrParaCima) {
		this.podeIrParaCima = podeIrParaCima;
	}

	public boolean podeIrParaBaixo() {
		return this.podeIrParaBaixo;
	}

	public void setPodeIrParaBaixo(boolean podeIrParaBaixo) {
		this.podeIrParaBaixo = podeIrParaBaixo;
	}

	public boolean podeIrNaHorizontal() {
		return this.podeIrParaEsquerda() || this.podeIrParaDireita();
	}

	public boolean podeIrNaVertical() {
		return this.podeIrParaCima() || this.podeIrParaBaixo();
	}

	public void Mover() {
    	MoverHorizontal();
    	MoverVertical();
    }
    
	public void MoverParaDireita(int incremento) {
		int incOld = this.incX;
		this.MoverParaDireita();
		this.incX = incOld;
    }
    
    public void MoverParaEsquerda(int incremento) {
    	int incOld = this.incX;
    	this.MoverParaEsquerda();
		this.incX = incOld;
    }
    
    public void MoverParaCima(int incremento) {
    	int incOld = this.incY;
    	this.MoverParaCima();
		this.incY = incOld;	
    }
    
    public void MoverParaBaixo(int incremento) {
    	int incOld = this.incY;
    	this.MoverParaBaixo();
		this.incY = incOld;
    }
    
    public boolean MoverParaDireita() {

    	if (!this.setIndoParaDireita(true))
    		return false;
    	
    	return this.MoverHorizontal();
    }
    
    public boolean MoverParaEsquerda() {
    	
    	if(!this.setIndoParaEsquerda(true))
    		return false;
    	
    	return this.MoverHorizontal();
    }
    
    public boolean MoverParaCima() {
    	
    	if(!this.setIndoParaCima(true))
    		return false;
    	
    	return this.MoverVertical();
    }
    
    public boolean MoverParaBaixo() {
    	 
    	if(!this.setIndoParaBaixo(true))
    		return false;
    	
    	return this.MoverVertical();
    }
    
    //------------------------------------------------------

    public boolean MoverParaDireitaAte(int xDestino, int incremento) {
    	
    	if (!this.setIndoParaDireita(true))
    		return false;
    	
    	if (incremento!=0)
    		incremento = this.incX;
		
    	boolean moveu = false;
    	
    	for (int px=this.x; px <= xDestino; px+=incremento) {
			if (MoverHorizontal(px))
				moveu = true;
    	}
    	return moveu;
    }
    
    public boolean MoverParaEsquerdaAte(int xDestino, int incremento) {

    	if (!this.setIndoParaEsquerda(true))
    		return false;
    	
    	if (incremento!=0)
    		incremento = this.incX;
    	
    	boolean moveu = false;
    	
		for (int px=this.x; px >= xDestino; px-=incremento) {
			if (MoverHorizontal(px))
				moveu = true;
		}
		return moveu;
    }

    //Faz parte do procedimento de Embaralhar
    public boolean MoverParaCimaAte(int yDestino, int incremento) {

    	if (!this.setIndoParaCima(true))
    		return false;
    	
    	if (incremento==0)
    		incremento = this.incY;
    	
    	boolean moveu = false;
    	
		for (int py=this.y; py >= yDestino; py-=incremento) {
			if (MoverVertical(py))
				moveu = true;
		}
		return moveu;
    }
    
    public boolean MoverParaBaixoAte(int yDestino, int incremento) {
    	
    	if (!this.setIndoParaBaixo(true))
    		return false;
    	
    	if (incremento!=0)
    		incremento = this.incY;
    	
    	boolean moveu = false;
    	
    	for (int py=this.y; py <= yDestino; py+=incremento) {
    		if (MoverVertical(py))
    			moveu = true;
    	}
    	return moveu;
    }
    
    public boolean MoverHorizontal() {
    	
    	int px = this.x;
    	
    	if (this.indoParaDireita)
    		px = px + this.incX;
        else
    	if (this.indoParaEsquerda)
    		px = px - this.incX;
    	else
    		return false;
    	
    	return this.MoverHorizontal(px);
    }
    
	public boolean MoverVertical() {
		
		int py = this.y;
        
		if(this.indoParaBaixo)
            py = this.y + this.incY;
        else
        if (this.indoParaCima)
            py = this.y - this.incY;
        else
    		return false;
		
        return this.MoverVertical(py);
    }
	
    public boolean MoverHorizontal(int px) {
    	
    	if (!this.podeMexer)
    		return false;
    	
    	if (this.indoParaDireita)
    	{
		    if (px > this.limiteXDTela - this.largura)
            	px = this.limiteXDTela - this.largura;
        }
    	else
    	if (this.indoParaEsquerda)
    	{
    		if (px < this.limiteXETela)
            	px = this.limiteXETela;
        }
    	else
    		return false;
    	
    	boolean colide = false;
    	int xOld = this.x;
    	this.x = px;
    	
    	if (!this.podeColidir)
			colide = (this.objetosColidem().size()>0);
		
    	if (colide)
    		this.x = xOld;
    	
    	return this.x != px;
    }
    
	public boolean MoverVertical(int py) {
		
		if (!this.podeMexer)
    		return false;
		
		if(this.indoParaBaixo)
        {
		    if (py > this.limiteYBTela - this.altura)
            	py = this.limiteYBTela - this.altura;
        }
        else
        if (this.indoParaCima)
        {
		    if (py < this.limiteYCTela)
            	py = this.limiteYCTela;
        }
        else
    		return false;

		boolean colide = false;
    	int yOld = this.y;
    	this.y = py;
    	
    	if (!this.podeColidir)
			colide = (this.objetosColidem().size()>0);
		
    	if (colide)
            this.y = yOld;
    	
    	return this.y != py;
    }
	
	//
	
	public List<Imagem> objetosColidem() {
		
		return objetosColidem(true);
	}
	
	public List<Imagem> objetosColidem(boolean somenteVisiveis) {
		
		List<Imagem> lista = new ArrayList<Imagem>();
		
		if (this.listaImagens == null)
			return lista;
		
		Imagem Img;
		
		for (int y=0; y < this.listaImagens.Imagens.size(); y++)
			for (int x=0; x < this.listaImagens.Imagens.get(y).size(); x++) {
		
				Img = this.listaImagens.Imagens.get(y).get(x);
				
				if (this != Img && (!somenteVisiveis || Img.Visivel ))
					if (!this.listaImagens.Imagens.get(y).get(x).podeColidir)
						if (ColisaoImagens.Colidem(this, Img))
							lista.add(Img);
			}
		return lista;
	}

	/*
	public List<Imagem> objetosMeIncluem() {
		
		return objetosMeIncluem(100);
	}

	public List<Imagem> objetosMeIncluem(int percentualDentro) {
		
		return objetosMeIncluem(true, percentualDentro);
	}
	*/
	
	public List<Imagem> objetosMeIncluem(boolean somenteVisiveis, int percentualDentro) {
		
		List<Imagem> lista = new ArrayList<Imagem>();
		
		if (this.listaImagens == null)
			return lista;
		
		Imagem Img;
		
		for (int y=0; y < this.listaImagens.Imagens.size(); y++)
			for (int x=0; x < this.listaImagens.Imagens.get(y).size(); x++) {

				Img = this.listaImagens.Imagens.get(y).get(x);
				
				if (this != Img && (!somenteVisiveis || Img.Visivel ))
					//if (!Img.podeColidir)
						if (ColisaoImagens.EstahIncluso(this, Img, percentualDentro))
							lista.add(Img);
			}
		return lista;
	}
	
	//
	
	public List<Imagem> imagemNaPosicao(int indiceX, int indiceY, boolean somenteVisiveis) {
		
		List<Imagem> lista = new ArrayList<Imagem>();
		
		if (this.listaImagens == null)
			return lista;
		
		Imagem Img;
		
		for (int y=0; y < this.listaImagens.Imagens.size(); y++)
			for (int x=0; x < this.listaImagens.Imagens.get(y).size(); x++) {
				
				Img = this.listaImagens.Imagens.get(y).get(x);
				
				if (Img.indiceX == indiceX && Img.indiceY == indiceY)
					lista.add(Img);
			}
		
		return lista;
	}	
	
	public List<Imagem> imagemNaPosicaoOriginal(int indiceX, int indiceY, boolean somenteVisiveis) {
		
		List<Imagem> lista = new ArrayList<Imagem>();
		
		if (this.listaImagens == null)
			return lista;
		
		Imagem Img;
		
		for (int y=0; y < this.listaImagens.Imagens.size(); y++)
			for (int x=0; x < this.listaImagens.Imagens.get(y).size(); x++) {
				
				Img = this.listaImagens.Imagens.get(y).get(x);
				
				if (Img.indiceXOrig == indiceX && Img.indiceYOrig == indiceY)
					lista.add(Img);
			}
		
		return lista;
	}
	
	// ------------------------------------------------------------------------------------

	public static Bitmap AjustarNaTela(Context contexto, int idImagem, int larguraTela, int alturaTela, boolean telaInteira) {
		
		Drawable drw = contexto.getResources().getDrawable(idImagem);
		
		return Imagem.AjustarNaTela(drw, larguraTela, alturaTela, telaInteira);
	}
	
	//
	
	public static Bitmap AjustarNaTela(Context contexto, Bitmap bitmap, int larguraTela, int alturaTela, boolean telaInteira) {
		
		if (bitmap == null)
			return null;
		
		/*Matrix matrix = new Matrix();
	    matrix.postRotate(270);
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, larguraTela, alturaTela, matrix, true);*/
	    
		Drawable drw = Converter.BitMapParaImageView(contexto, bitmap).getDrawable();
		
		return Imagem.AjustarNaTela(drw, larguraTela, alturaTela, telaInteira);
	}
	
	//
	
	public static Bitmap AjustarNaTela(Drawable drw, int larguraTela, int alturaTela, boolean telaInteira) {
		
		if (drw == null)
			return null;
		
		int largura;
		int altura;
		
		if (telaInteira) {
			largura = Tela.LarguraDispositivo;
			altura = Tela.AlturaDispositivo;
		}
		else
		{
			//largura = drw.getIntrinsicWidth();
			//altura = drw.getIntrinsicHeight();
			
			largura = drw.getIntrinsicWidth();
			altura = drw.getIntrinsicHeight();
			
			if (largura < Tela.LarguraDispositivo && altura < Tela.AlturaDispositivo) {
				largura = drw.getIntrinsicWidth()*100;
				altura = drw.getIntrinsicHeight()*100;
			}
			
			int perc;
			boolean continuar = true;
			
			//while (continuar) {
				continuar = false;
				if (altura > alturaTela) {
					perc = alturaTela * 100 / altura;
					altura = alturaTela;
					largura = largura*perc /100;
					continuar = true;
				}
				if (largura > larguraTela) {
					perc = larguraTela * 100 / largura;
					largura = larguraTela;
					altura = altura*perc / 100;
					continuar = true;
				}
			//}
		}
		return Converter.DrawableParaBitmap(drw, largura, altura);
	}
	
}