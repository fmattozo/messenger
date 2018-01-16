package org.matozzo.training.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


// Aqui é o modelo de dados, usando apenas uma lista hardcode
// seria aqui q fariamos a conecxão com o banco pra salva e buscar...
// com oq for, hibernate, jdbc, etc

@XmlRootElement					// to xml
public class Message {
	
	
	private long id;
	private String message;
	private Date created;
	private String author;
	
	
	public Message(){
		
	}
	
	public Message(long id, String message, String author) {
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String messag) {
		this.message = messag;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	

}
