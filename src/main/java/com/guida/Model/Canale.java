package com.guida.Model;

public class Canale {
    protected Integer id;
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
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Canale other = (Canale) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
    
}