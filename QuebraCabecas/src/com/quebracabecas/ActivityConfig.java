package com.quebracabecas;

import android.app.Activity;
import android.os.Bundle;

public class ActivityConfig extends Activity {  
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_config);
	}
	
	/*@Override
	public void onBackPressed() {
		
		this.verificarSalvar();
		//super.onBackPressed();
	}
	
	public void verificarSalvar() {
    	
    	AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		
		mensagem
		.setTitle("Voltar")
		.setMessage("Salvar configurações?")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setCancelable(false)
		.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//
				//setContentView(A.mover);
			}
		  })
		.setNegativeButton("Não",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	
		mensagem.show();
    }*/
}