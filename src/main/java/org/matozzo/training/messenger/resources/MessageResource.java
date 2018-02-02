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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.matozzo.training.messenger.exception.ErrorClass;
import org.matozzo.training.messenger.model.Message;
import org.matozzo.training.messenger.resources.bean.MessageFilterBean;
import org.matozzo.training.messenger.service.MessageService;


@Path("/messages")						// define um camiho para este rest... tipo http://blablabla../qqcoisa/<estePath>
@Consumes(MediaType.APPLICATION_JSON)	// define q só vai receber (consumir) Json
@Produces(MediaType.APPLICATION_JSON)	// define q vai retornar (produzir) um json
// @Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML})	// isso permite produzir tanto Json quanto  XML
// A relação entre Header da Request e oq vai ser aceito/retornado é
// 			Header da Request								Aplicação
// Content-Type (oq eu estou enviando)			@Consumes (oq eu aceito como input)
// Accept (oq eu aceito como resposta)			@Produces (oq eu consigo gerar)
// Colocando esses tags @Consumes/Produces aqui na Classe isso significa que todos os metodos vão seguir este padrão, MAS podem ser informados nos 
// metodos tb, para que um metodo espeficico trate de um formato e outro metodo trate outro, como o exemplo do GET q vou usar
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
//	// Usa o consumes e produces da classe
//	@GET
//	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)  {
//		if(filterBean.getYear() > 0) {
//			return messageService.getAllMessagesByYear(filterBean.getYear());
//		}
//		if((filterBean.getPaginationStart() + filterBean.getPaginationSize()) >0) {
//			return messageService.messagePaginated(filterBean.getPaginationStart(), filterBean.getPaginationSize());
//		}
//		return messageService.getAllMessages();
//	}
	// Metodo especifico para retornar Json, ignorando oq esta na classe
	// tem q ter o accept = application/json no header da request
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean)  {
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesByYear(filterBean.getYear());
		}
		if((filterBean.getPaginationStart() + filterBean.getPaginationSize()) >0) {
			return messageService.messagePaginated(filterBean.getPaginationStart(), filterBean.getPaginationSize());
		}
		return messageService.getAllMessages();
	}

	// Metodo especifico para retornar XML, ignorando oq esta na classe
	// tem q ter o Accept = text/xml no header da request
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean)  {
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
	// Usando o Response como retorno, voce pode personalizar msg, header, etc
	public Response getMessage(@PathParam("messageId") long messageId,					// Pega o </messageId> da url e passa como parametro  
							   @Context UriInfo uriInfo) {								// pega o contexto da URL todo
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

		// HATEOAS
		// Aqui eu uso o Context para pegar a URI e preencher uma lista com a informação dele mesmo
		// como link para a propria msg, link para o author e um link para os comentarios
		String uri = getUriForSelf(uriInfo, messageId);
		foundMessage.addLink(uri, "self");
		uri = getUriForAuthor(uriInfo, foundMessage.getAuthor());
		foundMessage.addLink(uri, "author");
		uri = getUriForComments(uriInfo, messageId);
		foundMessage.addLink(uri, "comments");
		
		
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
	// Metodos que Identificam e retornam as URIs de message, profile e comments
	//
	private String getUriForSelf(UriInfo uriInfo, long messageId) {
		String uri = uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)		// passa a classe para pegar o endereço dela
						.path(Long.toString(messageId))		// pego o /{messageId}
						.build().toString();
		return uri;
	}
	
	private String getUriForAuthor(UriInfo uriInfo, String author) {
		String uri = uriInfo.getBaseUriBuilder()
						.path(ProfileResource.class)		// passa a classe para pegar o endereço dela
						.path(author)						// inclui o /<author>
						.build().toString();
		return uri;
		
	}
	
	private String getUriForComments(UriInfo uriInfo, long messageId) {
		String uri = uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)						// passa a classe para pegar o endereço dela
						.path(MessageResource.class, "getCommentResource" )	// inclui o caminho dos comments passando o methodo que chama a proxima classe
						.path(CommentResource.class)						// passa a classe dos comments
						// agora no path tem um {messageId} que vem do caminho do methodo getCommentResource, <@Path("/{messageId}/comments")>
						// temos que trocar o {messageId} pelo ID da mensagem usando a linha seguinte para fazer isso
						.resolveTemplate("messageId", Long.toString(messageId))
						.build().toString();
		// Como conhecemos os caminhos que vem depois o getBaseUriBuilder, poderiamos montar isso como uma string, sem precisar passar tantos ".path"
		// mas isso seria hardcode e se a aplicação for modificada o retorno seria errado, na forma acima o unico medo é se o metodo
		// getCommentResource for renomeado, ai aquela linha vai se perder, mas fora isso (que existe até outras formas de resolver) vai sempre funcionar
		return uri;
	}
	// ======================================================================
	
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
