package com.guida.Model;



public class Utente {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    boolean mail_giornaliere;
    boolean isAdmin;
    
 
    public Utente() {
    }
 
    public Utente(int id) {
        this.id = id;
    }
    
    public Utente(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    public Utente(String username, String password, String email) {
    	this(username, password);
    	this.email = email;
    }
    
    public Utente(int id, String username, String password, String email) {
    	this(username, password, email);
    	this.id = id;
    }
 
    public Utente(String username, String password, String email, boolean mail_giornaliere, boolean isAdmin) {
    	this(username, password, email);
    	this.mail_giornaliere = mail_giornaliere;
    	this.isAdmin = isAdmin;
    }
    
    public Utente(int id, String username, String password, String email, boolean mail_giornaliere, boolean isAdmin) {
    	this(username, password, email, mail_giornaliere, isAdmin);
    	this.id = id;
    }
    

	public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getmail_giornaliere() {
        return mail_giornaliere;
    }
 
    public void setmail_giornaliere(boolean mail_giornaliere) {
        this.mail_giornaliere = mail_giornaliere;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
 
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}