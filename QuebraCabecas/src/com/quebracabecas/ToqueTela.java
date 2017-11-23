package com.quebracabecas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToqueTela extends View implements OnClickListener, OnTouchListener {
	
	Context contexto;
	Tela tela;
	private GestureDetector gestureDetector;
	private Imagem Img;
	Imagem imgSelecionado = null;
	LinearLayout layout;
	TextView texto;
	
	public ToqueTela(Context context) {
		
		super(context);
		contexto = context;
		gestureDetector = new GestureDetector(contexto, (OnGestureListener) new DetectorDeGesto());
		
		this.criarlayout();
		// configura a view para receber foco e tratar eventos de teclado
		setFocusable(true);
		
		this.setOnClickListener(this);
		this.setOnTouchListener(this);
	}
	
	private void criarlayout() {
		layout = new LinearLayout(contexto);
        layout.setId(gerarID.generateViewId());
        
        texto = new TextView(contexto);
        texto.setTextColor(Color.BLUE);
        texto.setWidth(480);
        texto.setText("Apenas um esboço, vou incluir algumas opções no menu...");
        layout.addView(texto);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);

		Paint pincel = new Paint();
		pincel.setColor(Color.BLACK);
		canvas.drawRect(0, 0, tela.LarguraDispositivo, tela.AlturaDispositivo, pincel);
		
		//pincel.setShadowLayer(300, 240, 340, Color.WHITE);
		//canvas.drawRect(0, 0, 200, 200, pincel);
		
		layout.measure(canvas.getWidth(), canvas.getHeight());
        layout.layout(0, 0, canvas.getWidth(), canvas.getHeight());
        layout.draw(canvas);
        
        tela.outrasImagens.MostrarImagens(canvas);
        tela.Blocos.listaImagens.MostrarImagens(canvas);
        
        //
        if (tela.Blocos.Embaralhar.Embaralhado < tela.Blocos.Embaralhar.QtdeVezes) {
        	tela.Blocos.Embaralhar.Executar(4);
        
        	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
        invalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch(event.getAction()) {
		
			case MotionEvent.ACTION_DOWN: // inicia o movimento
			break;
			case MotionEvent.ACTION_MOVE: // movimentando
			break;
			case MotionEvent.ACTION_UP:   // soltando objeto
				
				if (imgSelecionado != null) {
					
					tela.Blocos.AjustarPosicaoBloco(imgSelecionado);
					tela.Blocos.VerificarFinalizou();
					
					imgSelecionado = null;
				}
				
				//-------------------------------------------
				//Nunca esquecer de voltar essa linha!!!!!!!!
				//imgSelecionado = null;
				
			break;
		}
		invalidate();
		return true;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return gestureDetector.onTouchEvent(event);
	}
	
	@Override
	public void onClick(View v) {
	}
	
	class DetectorDeGesto extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			
			MeuProprioOnScroll(tela.outrasImagens, e1, e2, distanceX, distanceY);
			
			if (imgSelecionado == null) {
			
				if (tela.Blocos.Embaralhar.Embaralhado >= tela.Blocos.Embaralhar.QtdeVezes)
					MeuProprioOnScroll(tela.Blocos.listaImagens, e1, e2, distanceX, distanceY);
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
	
	protected boolean MeuProprioOnScroll(ListaImagens listaImagens, MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		
		final int DISTANCIA_MINIMA = 1;
		if (imgSelecionado == null)
			
			for (int y=0; y < listaImagens.Imagens.size(); y++) 
		    	for (int x=0; x < listaImagens.Imagens.get(y).size(); x++) {
		    		
		    		Img = listaImagens.Imagens.get(y).get(x);
		    		
		    		if (Img.Visivel && Img.podeMexer)
		    			if (ColisaoImagens.pontoEstaNaImagem(Img, (int)e1.getX(), (int)e1.getY()))
		    				imgSelecionado = Img;
			    	
		    		if (imgSelecionado != null)
			    	if (!Img.movimentoLivre) {
				    	boolean horiz = Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY());
				    	Img.setPodeIrParaDireita(horiz);
				    	Img.setPodeIrParaEsquerda(horiz);
				    	Img.setPodeIrParaCima(!horiz);
				    	Img.setPodeIrParaBaixo(!horiz);
				    	
				    	Img.setIndoParaDireita(e2.getX() - e1.getX() > DISTANCIA_MINIMA); // Direita
				    	Img.setIndoParaEsquerda(e1.getX() - e2.getX() > DISTANCIA_MINIMA);// Esquerda
				    	Img.setIndoParaBaixo(e2.getY() - e1.getY() > DISTANCIA_MINIMA);   // Cima
				    	Img.setIndoParaCima(e1.getY() - e2.getY() > DISTANCIA_MINIMA);    // Baixo
			    	}
			    }
		
		boolean moveu = false;
		
		if (imgSelecionado != null) {
			
			Img = imgSelecionado;
			
			if (Img.podeIrNaHorizontal()){
				Img.setIndoParaDireita(e2.getX() - e1.getX() > DISTANCIA_MINIMA); // Direita
				Img.setIndoParaEsquerda(e1.getX() - e2.getX() > DISTANCIA_MINIMA);// Esquerda
				
				//moveu = Img.MoverHorizontal((int)e2.getX() - (Img.largura/2));
				
				if (Img.movimentoLivre)
					moveu = Img.MoverHorizontal((int)e2.getX() - (Img.largura/2));
				else
					moveu = Img.MoverHorizontal();
				
			}
			if (Img.podeIrNaVertical()) {
				Img.setIndoParaBaixo(e2.getY() - e1.getY() > DISTANCIA_MINIMA);   // Cima
				Img.setIndoParaCima(e1.getY() - e2.getY() > DISTANCIA_MINIMA);    // Baixo;
				
				//moveu = Img.MoverVertical((int)e2.getY() - (Img.altura/2));
				
				if (Img.movimentoLivre) 
					moveu = Img.MoverVertical((int)e2.getY() - (Img.altura/2));
				else
					moveu = Img.MoverVertical();
				
			}
			//Img.x = (int)e2.getX() - (Img.largura/2);
			//Img.y = (int)e2.getY() - (Img.altura/2);
		}
		return moveu;
	}	
}