package com.quebracabecas;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;

public class ActivityImagens extends Activity {
	
	public ToqueTela mover;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_imagens);
		
		CriarTela();
	}
	
	private void CriarTela() {
		mover = new ToqueTela(this);
		mover.tela = new TelaImagemDoArquivo(this);
		
		mover.tela.GerarBackGround(R.drawable.b3);
		ImagensDoArquivo ic = new ImagensDoArquivo();
		/*List<ImagensDoArquivo> lista = ic.TodasImagensDaPastaCamera();
		
		for (int i=0; i<lista.size(); i++)
			mover.tela.GerarMiniatura(lista.get(i));*/
		
		setContentView(mover);
	}
	
}