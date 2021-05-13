package com.guida;


public class Canale {
    protected int id;
    protected String nome;
    protected String immagine;
    
 
    public Canale() {
    }
 
    public Canale(int id) {
        this.id = id;
    }
    
    public Canale(int id, String nome, String immagine) {
    	this(nome, immagine);
    	this.id = id;
    }
 
    public Canale(String nome, String immagine) {
        this.nome = nome;
        this.immagine = immagine;
    }

    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getImmagine() {
        return immagine;
    }
 
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
}