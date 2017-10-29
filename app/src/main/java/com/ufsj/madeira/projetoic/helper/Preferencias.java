package com.ufsj.madeira.projetoic.helper;

import android.content.Context;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "ouroboros.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_ID = "idUsuarioLogado";


    public Preferencias( Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados( String idUsuario){

        editor.putString(CHAVE_ID,idUsuario );
        editor.commit();

    }

    public String getId(){
        return preferences.getString(CHAVE_ID,null);
    }

}

