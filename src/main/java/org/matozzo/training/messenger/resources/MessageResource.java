package org.matozzo.training.messenger.resources;

import java.util.List;

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
import org.matozzo.training.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)	// define q só vai receber (consumir) Json
@Produces(MediaType.APPLICATION_JSON)	// define q vai retornar (produzir) um json
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	public List<Message> getMessages() {
		return messageService.getAllMessages();
	}
	
	// vai retornar apenas a msg do id q colocar na request ..../messages/id
	// o pathparam passa o id para o parametro do metodo
	// vc pode usar quantos sub path, como esse, qts quiser ..../messages/id/.../id2/name/.../id
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageService.getMessage(messageId);
	}
	
	@POST
	public Message postMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")																// ....define a ação
	public Message updateMessage (@PathParam("messageId") long id, Message message) { 	// ....o @PathParam pega o id da url e transforma em parametro para o metodo
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	public Message deleteMessage (@PathParam("messageId") long id) {
		return messageService.removeMessage(id);
	}
	
}
