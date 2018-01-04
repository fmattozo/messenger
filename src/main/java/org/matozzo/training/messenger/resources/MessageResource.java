package org.matozzo.training.messenger.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.matozzo.training.messenger.model.Message;
import org.matozzo.training.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public List<Message> getMessages() {
		// return "Hello MATOZZO!!!!!";
		return messageService.getAllMessages();
	}
	

	// vai retornar apenas a msg do id q colocar na request ..../messages/id
	// o pathparam passa o id para o parametro do metodo
	// vc pode usar quantos sub path, como esse, qts quiser ..../messages/id/.../id2/name/.../id
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageService.getMessage(messageId);
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public Message postMessages(Message message) {
		return messageService.addMessage(message);
	}
	
	

}
