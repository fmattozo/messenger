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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.matozzo.training.messenger.exception.ErrorClass;
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
	// aqui esta retornando o array de objetos direto, e o jersey converte em json
	// os próximos metodos usam o Response como retorno
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
	public Response getMessage(@PathParam("messageId") long messageId) {	// Usando o Response como retorno
																			// voce pode personalizar msg, header, etc
		Message foundMessage = messageService.getMessage(messageId); 
		if (foundMessage == null) {
			ErrorClass error = new ErrorClass(404, "errorDescription", "errorMsg");		//Criei uma classe que pode ser usada para retornar informações
//			return Response.status(Status.NOT_FOUND)									// uma forma de retorno direto de um erro
//			   .entity(error)
//			   .header("Result", "Message not Found.")
//			   .build();
			Response response = Response.status(Status.NOT_FOUND)						// criei o response q vai ser devolvido por um throw exception
										.entity(error)
										.header("Result", "Message not Found.")
										.build();
			throw new WebApplicationException(response);								// esse generico, vc define o codigo do erro <achei assim a melhor forma>
			//throw new NotFoundException(response);									// esse é um dos tipos que já define o tipo de erro.
			// Existe Outra forma de lidar com exceptions, criando classe para cada tipo de erro e depois mapeando isso no jersey
			// mas esse approach não me pareceu interessante pois é muito trabalhoso e não vi vantagem
					
		}
		
		// Retona uma response, que na verdade é uma forma de vc criar a sua response personalizada ao invés de apenas usar uma classe e deixar o jersey
		// empacotar essa classe numa response
		// dessa forma vc define header, msg, etc... 
		// e o objeto que seria devolvido entra no .entity(..)
		return Response.status(Status.OK)
				   .entity(foundMessage)
				   .header("Result", "Message Found.")
				   .build();
		
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
	@PUT																				// ....define a ação
	@Path("/{messageId}")																// .. defini o path a partir e onde a app esta conf... http://<app path>/<onde esse cara vai capturar algo>
	public Response updateMessage (@PathParam("messageId") long id, Message message) { 	// ....o @PathParam pega o id da url e transforma em parametro para o metodo
		message.setId(id);
		Message resultMessage = messageService.updateMessage(message);
		if (resultMessage != null) {
			return Response.status(Status.ACCEPTED)
					.entity(resultMessage)
					.header("Result", "Message updated with sucess.")
					.build();
		} else {
			// aqui eu usei o próprio responsse para retornar um erro
			return Response.status(Status.NOT_FOUND)
						   .header("Result", "Message not found.")
						   .build();
		}
		
	}

	// ======================================================================
	// Delete
	// ======================================================================
	@DELETE
	@Path("/{messageId}")
	public Response deleteMessage (@PathParam("messageId") long id) {
		Message deletedMessage = messageService.removeMessage(id);
		if (deletedMessage != null) {
			return Response.status(Status.NO_CONTENT)
						   .header("Result", "Message deleted.")
						   .build();
		}
		return Response.status(Status.NOT_FOUND)
				   .header("Result", "Message not found.")
				   .build();
		 
	}
	
	// ======================================================================
	// COMMENTS ... aqui vc passa o caminho dos comments para uma outra
	// classe <CommentResource> cuidar
	// Assim tudo que for de comments vai ser cuidado pelo Resource dele
	// Isso tb pode ser feito se vc quiser criar uma classe cuide 
	// ======================================================================
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
