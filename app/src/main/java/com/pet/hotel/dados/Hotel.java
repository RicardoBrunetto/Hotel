package com.pet.hotel.dados;

import java.io.Serializable;

//import java.io.Serializable;
public class Hotel implements Serializable {

    public long id;
    public String nome;
    public String endereco;
    public float estrelas;
    public boolean interesse = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(float estrelas) {
        this.estrelas = estrelas;
    }

    public boolean isInteresse() {
        return interesse;
    }

    public void setInteresse(boolean interesse) {
        this.interesse = interesse;
    }

    public Hotel(long id, String nome, String endereco, float estrelas) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }

    public Hotel(String nome, String endereco, float estrelas) {
        this(0, nome, endereco, estrelas);
    }

    @Override
    public String toString() {
        return nome;
    }
}
