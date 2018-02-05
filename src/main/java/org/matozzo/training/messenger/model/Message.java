package org.matozzo.training.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


// Aqui é o modelo de dados, usando apenas uma lista hardcode
// seria aqui q fariamos a conecxão com o banco pra salva e buscar...
// com oq for, hibernate, jdbc, etc

@XmlRootElement					// pq é uma classe root... entao precisa disso par virar json
public class Message {
	
	
	private long id;
	private String message;
	private Date created;
	private String author;
	private List<Link> links = new ArrayList<>();
	
	
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
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(String uri, String rel) {
		Link link = new Link();
		link.setLink(uri);
		link.setRel(rel);
		links.add(link);
		return;
	}

}
