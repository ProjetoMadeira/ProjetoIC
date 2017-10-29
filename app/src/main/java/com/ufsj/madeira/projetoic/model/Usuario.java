package com.ufsj.madeira.projetoic.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.ufsj.madeira.projetoic.config.ConfiguracaoFirebase;

/**
 * Created by JV on 18/09/2017.
 */
public class Usuario {
    private String id;
    private String email;
    private String nome;
    private String senha;
    private DatabaseReference databaseReference;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void salvarDados(){
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("usuarios").child(getId()).setValue(this);
    }
}

