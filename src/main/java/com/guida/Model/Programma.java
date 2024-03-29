package com.guida.Model;

import java.sql.Timestamp;

public class Programma {
    protected Integer id;
    protected int id_canale;
    protected int id_orario;
    protected String nome;
    protected String descrizione;
    protected String genere;
    protected String link_scheda;
    protected String link_immagine;
    protected String nome_canale;
    protected boolean isTvShow;
    protected int numero_stagione;
    protected int numero_episodio;
    protected Timestamp data_inizio;
    protected Timestamp data_fine;
    protected Integer durata;
    
 
    public Programma() {
    }
 
    public Programma(int id_orario) {
        this.id_orario = id_orario;
    }
    
    public Programma(int id, String nome, String descrizione, String genere, 
			String link_scheda, String link_immagine, boolean isTvShow) {
    	this.id = id;
    	this.nome = nome;
        this.descrizione = descrizione;
        this.genere = genere;
        this.link_scheda = link_scheda;
        this.link_immagine = link_immagine;
        this.isTvShow = isTvShow;
    }
    
    public Programma(int id, String nome, String descrizione, String genere, 
			String link_scheda, String link_immagine, boolean isTvShow, 
			int numero_stagione, int numero_episodio, int id_canale, String nome_canale, int id_orario, Timestamp data_inizio, Timestamp data_fine) {
    	this(nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, id_orario, data_inizio, data_fine);
    	this.id = id;
    	this.nome_canale = nome_canale;
    }
    
    public Programma(int id, String nome, String descrizione, String genere, 
			String link_scheda, String link_immagine, boolean isTvShow, 
			int numero_stagione, int numero_episodio, int id_canale, int id_orario, Timestamp data_inizio, Timestamp data_fine) {
    	this(nome, descrizione, genere, link_scheda, link_immagine, isTvShow, numero_stagione, numero_episodio, id_canale, id_orario, data_inizio, data_fine);
    	this.id = id;
    }
 
    public Programma(String nome, String descrizione, String genere, 
    			String link_scheda, String link_immagine, boolean isTvShow, 
    			int numero_stagione, int numero_episodio, int id_canale, int id_orario,
    			Timestamp data_inizio, Timestamp data_fine) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.genere = genere;
        this.link_scheda = link_scheda;
        this.link_immagine = link_immagine;
        this.isTvShow = isTvShow;
        this.numero_stagione = numero_stagione;
        this.numero_episodio = numero_episodio;
        this.id_canale = id_canale;
        this.id_orario = id_orario;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
    }
    
    public Programma(String nome, String descrizione, String genere, 
			String link_scheda, String link_immagine, boolean isTvShow, 
			int numero_stagione, int numero_episodio, int id_canale,
			Timestamp data_inizio, Timestamp data_fine) {
    this.nome = nome;
    this.descrizione = descrizione;
    this.genere = genere;
    this.link_scheda = link_scheda;
    this.link_immagine = link_immagine;
    this.isTvShow = isTvShow;
    this.numero_stagione = numero_stagione;
    this.numero_episodio = numero_episodio;
    this.id_canale = id_canale;
    this.data_inizio = data_inizio;
    this.data_fine = data_fine;
}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data_fine == null) ? 0 : data_fine.hashCode());
		result = prime * result + ((data_inizio == null) ? 0 : data_inizio.hashCode());
		result = prime * result + ((durata == null) ? 0 : durata.hashCode());
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
		Programma other = (Programma) obj;
		if (data_fine == null) {
			if (other.data_fine != null)
				return false;
		} else if (!data_fine.equals(other.data_fine))
			return false;
		if (data_inizio == null) {
			if (other.data_inizio != null)
				return false;
		} else if (!data_inizio.equals(other.data_inizio))
			return false;
		if (durata == null) {
			if (other.durata != null)
				return false;
		} else if (!durata.equals(other.durata))
			return false;
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
        
    
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId_canale() {
        return id_canale;
    }
 
    public void setId_canale(int id_canale) {
        this.id_canale = id_canale;
    }
    
    public int getId_orario() {
        return id_orario;
    }
 
    public void setId_orario(int id_orario) {
        this.id_orario = id_orario;
    }
    
    
    public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getnome_canale() {
        return nome_canale;
    }
 
    public void setnome_canale(String nome_canale) {
        this.nome = nome_canale;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
 
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public String getGenere() {
        return genere;
    }
 
    public void setGenere(String genere) {
        this.genere = genere;
    }
    
    public String getlink_scheda() {
        return link_scheda;
    }
 
    public void setlink_scheda(String link_scheda) {
        this.link_scheda = link_scheda;
    }
    
    public String getlink_immagine() {
        return link_immagine;
    }
 
    public void setlink_immagine(String link_immagine) {
        this.link_immagine = link_immagine;
    }
    
    public boolean getIsTvShow() {
        return isTvShow;
    }
 
    public void setIsTvShow(boolean isTvShow) {
        this.isTvShow = isTvShow;
    }
    
    public int getnumero_stagione() {
        return numero_stagione;
    }
 
    public void setnumero_stagione(int numero_stagione) {
        this.numero_stagione = numero_stagione;
    }
    
    public int getnumero_episodio() {
        return numero_episodio;
    }
 
    public void setnumero_episodio(int numero_episodio) {
        this.numero_episodio = numero_episodio;
    }
    
    public Timestamp getdata_inizio() {
        return data_inizio;
    }
 
    public void setdata_inizio(Timestamp data_inizio) {
        this.data_inizio = data_inizio;
    }
    
    public Timestamp getdata_fine() {
        return data_fine;
    }
 
    public void setdata_fine(Timestamp data_fine) {
        this.data_fine = data_fine;
    }
    
    public Integer getDurata() {
		return durata;
	}
	public void setDurata(Integer durata) {
		this.durata = durata;
	}
    
}