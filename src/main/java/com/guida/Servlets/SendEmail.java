package com.guida.Servlets;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	private String toEmail;
	private String myHash;
	
	public SendEmail(String toEmail, String myHash) {
		this.toEmail = toEmail;
		this.myHash = myHash;
	}
    
    public void sendEmail() {
    	String fromEmail = "francesco.perilli96@gmail.com";
    	// Inserire password email
    	String emailPassword = "------------";
    	
    	try {
    		// your host email smtp server details
            Properties pr = new Properties();
            pr.put("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.starttls.enable", "true");
 
            //get session to authenticate the host email address and password
            Session session = Session.getDefaultInstance(pr, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, emailPassword);
                }
            });

            //set email message details
            MimeMessage mess = new MimeMessage(session);

    		//set from email address
            mess.setFrom(new InternetAddress(fromEmail));
    		//set to email address or destination email address
            mess.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
    		
    		//set email subject
            mess.setSubject("Guida TV | Link di attivazione");
            
    		//set message text
            mess.setText("Ti sei registrato con successo. Conferma la registazione clickando"
            		+ "su questo link: http://localhost:8080/guidatv/utente/activate?email="+ toEmail 
            		+ "&hash=" + myHash);
            //send the message
            Transport.send(mess);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
    

