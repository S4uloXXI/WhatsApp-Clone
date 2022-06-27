package com.s4ulo.whatsappclone.model;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.s4ulo.whatsappclone.config.ConfiguracaoFirebase;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;

    // Construtor vazio e necessario para funcionamento do banco de dados do Firebase
    public Usuario(){

    }
    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuario = firebaseRef.child("usuarios"); usuario.setValue(this);

    }


    @Exclude /* Exclui informacao no banco de dados */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude /* Exclui informacao no banco de dados */
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
