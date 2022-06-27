package com.s4ulo.whatsappclone.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsappClonePreferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;
    private String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";


    public Preferencias (Context contextoParamentro){
        contexto = contextoParamentro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();

    }
    public void salvarDados(String identificadorUsuario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();
    }



   /* public HashMap<String, String> getDadosUsuarios(){
        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));
        return dadosUsuario;
    }*/
    public String getIdenficador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }



}
