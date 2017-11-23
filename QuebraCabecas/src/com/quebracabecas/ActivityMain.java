package com.quebracabecas;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class ActivityMain extends Activity {

	public ToqueTela mover;
	
	//-----------------------------------------------------------------------------------------------------
	//guardando cópia da lista - recuperar após a rotação automática
	//-----------------------------------------------------------------------------------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		AG.listaImagensMemoria = mover.tela.Blocos.listaImagens.CopiarPosicoesMemoria();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (AG.imgArquivo.nomearquivo.trim().equals("") || AG.imgArquivo.bitmap == null)
			this.CriarTela();
		else
			this.RecuperarTela();
	}

	//-----------------------------------------------------------------------------------------------------
	
	private void CriarTela() {
		
		mover = new ToqueTela(this);
		mover.tela = new TelaImagemDoArquivo(this);
		//mover.tela.idbackground = buscarDoBanco();
		
		this.NovaImagem(AG.imgArquivo.nomearquivo);
		
		setContentView(mover);
	}

	private void NovaImagem(String nomearquivo) {
		
		AG.imgArquivo.nomearquivo = nomearquivo;
		mover.tela.outrasImagens.LimparImagens();
		mover.tela.GerarBackGround();
		mover.tela.GerarMiniatura();
		mover.tela.GerarQuebraCabeca();
		mover.tela.Blocos.Embaralhar.Embaralhado = 0;
		//mover.tela.Blocos.Embaralhar.Executar();
	}
	
	//Rotação automática e retorno da tela de Configurações
	private void RecuperarTela() {
		
		//Toast.makeText(this, "recuperar imagem: " + AG.imgArquivo.nomearquivo, Toast.LENGTH_SHORT).show();
		
		mover = new ToqueTela(this);
		mover.tela = new TelaImagemDoArquivo(this);
		
		mover.tela.GerarBackGround();
		mover.tela.GerarMiniatura();
		mover.tela.GerarQuebraCabeca();

		//para pode movimentar os blocos
		mover.tela.Blocos.Embaralhar.Embaralhado = mover.tela.Blocos.Embaralhar.QtdeVezes;
		//
		mover.tela.Blocos.listaImagens.ResgatarPosicoesMemoria(AG.listaImagensMemoria);
		
		setContentView(mover);
	}

	//
	
	public void verificarSairPrograma() {
    	
    	AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		
		mensagem
		.setTitle("Finalizar app")
		.setMessage("Fechar a aplicação?")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setCancelable(false)
		.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
				AG.imgArquivo.nomearquivo = "";
				finish();
			}
		  })
		.setNegativeButton("Não",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		mensagem.show();
    }
	
	@Override
	public void onBackPressed() {
		
		this.verificarSairPrograma();
		//super.onBackPressed();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.clear();
		MenuItem item;
		SubMenu submenu;
		
		item = menu.add(0, 10, 100, "Embaralhar");
        //item.setIcon(R.drawable.ic_launcher);
		
		item = menu.add(0, 20, 200, "Sortear imagem");
		//item.setIcon(R.drawable.ic_launcher);
		
		item = menu.add(0, 15, 201, "Escolher imagem");
		//item.setIcon(R.drawable.ic_launcher);
		
		item = menu.add(0, 30, 300, "Melhores jogadores");
		//item.setIcon(R.drawable.ic_launcher);
		
		//item = menu.add(0, 40, 400, "Girar Imagem");
		//item.setIcon(R.drawable.ic_launcher);
		
		//ABRE UMA TELA DE CONFIGURAÇÕES
		item = menu.add(0, 50, 500, "Configurações");
		//item.setIcon(R.drawable.ic_launcher);
		
        //item = menu.add(0, 100, 600, "Fechar App");
        //item.setIcon(R.drawable.ic_launcher);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()){  
        
	        case 10:
	        	mover.tela.Blocos.Embaralhar.Embaralhado = 0;
	            break;
	            
	        case 15:
	        	/*Intent fotoIntent = new Intent();
	        	startActivityForResult(Intent.createChooser(getIntent(), "Selecione imagem"), SELECT_PHOTO);*/
	        			
	        	/*//captura foto
	        	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);           
	            startActivityForResult(cameraIntent, MeuRetornoCamera);*/
	            
	        	Intent intencao = new Intent(Intent.ACTION_GET_CONTENT);
	        	intencao.setType("image/*");
	        	startActivityForResult(intencao, AG.imagemSelecionada);
	            
	        	/*startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 2);
	        	startActivity(new Intent(ActivityMain.this, ActivityImagens.class));*/
	            break;
	            
	        case 20:
	        	this.NovaImagem("");
	            break;
	            
	        case 30:
	            //mover.tela.novoBackground();
	            break;
	            
	        case 40:
	            //mover.tela.Blocos.Girar();
	            break;
	            
	        case 50:
	        	startActivity(new Intent(ActivityMain.this, ActivityConfig.class));
	            break;
	    }
        return super.onMenuItemSelected(featureId, item);
	}
	
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
        
		if (requestCode == MeuRetornoCamera && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
        }    
    }*/
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
		
		if (resultCode != RESULT_OK)
			return;
				
		AG.imgArquivo.onActivityResult(this, requestCode, resultCode, data, false);
		AG.imgArquivo.bitmap = null; 
    }	
}
