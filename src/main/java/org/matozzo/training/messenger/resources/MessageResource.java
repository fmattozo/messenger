package org.matozzo.training.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.matozzo.training.messenger.model.Message;
import org.matozzo.training.messenger.resources.bean.MessageFilterBean;
import org.matozzo.training.messenger.service.MessageService;


@Path("/messages")						// define um camiho para este rest... tipo http://blablabla../qqcoisa/<estePath>
@Consumes(MediaType.APPLICATION_JSON)	// define q só vai receber (consumir) Json
@Produces(MediaType.APPLICATION_JSON)	// define q vai retornar (produzir) um json
public class MessageResource {

	MessageService messageService = new MessageService();

//	// ======================================================================
//	// GET com os pathParam
//	// ======================================================================
//	@GET
//	public List<Message> getMessages(@QueryParam("year") int year,		// passado com ../messages?year=2018
//									 @QueryParam("start") int start,	// ../messages?start=5&size=10		.. coloca o & para passar mais de um
//									 @QueryParam("size") int size) {
//		if(year > 0) {
//			return messageService.getAllMessagesByYear(year);
//		}
//		if((start+size) >0) {
//			return messageService.messagePaginated(start, size);
//		}
//		return messageService.getAllMessages();
//	}
	
	
	// ======================================================================
	// GET com beanParam
	// ou seja, vc cria uma classe que vai receber os parametros.. la já está
	// preparado para isso... ai vc usa <classe.parametro>
	// ======================================================================
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)  {
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesByYear(filterBean.getYear());
		}
		if((filterBean.getPaginationStart() + filterBean.getPaginationSize()) >0) {
			return messageService.messagePaginated(filterBean.getPaginationStart(), filterBean.getPaginationSize());
		}
		return messageService.getAllMessages();
	}
	
	// ======================================================================
	// GET 
	// vai retornar apenas a msg do id q colocar na request ..../messages/id
	// o pathparam passa o id para o parametro do metodo
	// vc pode usar quantos sub path, como esse, qts quiser ..../messages/id/.../id2/name/.../id
	// ======================================================================
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageService.getMessage(messageId);
	}
	
	
	
	// ======================================================================
	// POST
	// ======================================================================
	@POST
	public Message postMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	// ======================================================================
	// PUT - update
	// ======================================================================
	@PUT
	@Path("/{messageId}")																// ....define a ação
	public Message updateMessage (@PathParam("messageId") long id, Message message) { 	// ....o @PathParam pega o id da url e transforma em parametro para o metodo
		message.setId(id);
		return messageService.updateMessage(message);
	}

	// ======================================================================
	// Delete
	// ======================================================================
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage (@PathParam("messageId") long id) {
		return messageService.removeMessage(id);
	}
	
	// ======================================================================
	// COMMENTS ... aqui vc passa o caminho dos comments para uma outra
	// classe <CommentResource> cuidar
	// Assim tudo que for de comments vai ser cuidado pelo Resource dele
	// ======================================================================
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
