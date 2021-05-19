package com.guida.Model;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class Utente {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String hash;
    protected boolean mail_giornaliere;
    protected boolean has_confirmed;
    protected boolean isAdmin;
    protected int id_email;
    
    
 
    public Utente() {
    }
 
    public Utente(int id) {
        this.id = id;
    }
    
    public Utente(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    public static Utente UtenteActivate(String email, String hash) {
    	Utente utente = new Utente();
    	utente.email = email;
    	utente.hash = hash;
    	return utente;
    }
    
    public Utente(String username, String password, String email) {
    	this(username, password);
    	this.email = email;
    	String myHash;
    	Random random = new Random();
    	int number = random.nextInt(999999);
    	myHash = String.format("%06d", number);
    	this.hash = myHash;
    }
    
    public Utente(int id, String username, String password, String email) {
    	this(username, password, email);
    	this.id = id;
    }
    
    public Utente(int id, String username, String password, String email, boolean mail_giornaliere) {
    	this(id, username, password, email);
    	this.mail_giornaliere = mail_giornaliere;
    }
 
    public Utente(String username, String password, String email, boolean mail_giornaliere, boolean isAdmin, boolean has_confirmed) {
    	this(username, password, email);
    	this.mail_giornaliere = mail_giornaliere;
    	this.isAdmin = isAdmin;
    	this.has_confirmed = has_confirmed;
    }
    
    public Utente(int id, String username, String password, String email, boolean mail_giornaliere, boolean isAdmin, boolean has_confirmed) {
    	this(username, password, email, mail_giornaliere, isAdmin, has_confirmed);
    	this.id = id;
    }
    

	public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
    
    public int getid_email() {
        return id_email;
    }
 
    public void setid_email(int id_email) {
        this.id_email = id_email;
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

    public String getHash() {
        return hash;
    }
 
    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean getmail_giornaliere() {
        return mail_giornaliere;
    }
 
    public void setmail_giornaliere(boolean mail_giornaliere) {
        this.mail_giornaliere = mail_giornaliere;
    }
    
    public boolean gethas_confirmed() {
        return has_confirmed;
    }
 
    public void sethas_confirmed(boolean has_confirmed) {
        this.has_confirmed = has_confirmed;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
 
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}