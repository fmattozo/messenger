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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.matozzo.training.messenger.model.Comment;
import org.matozzo.training.messenger.service.CommentService;

@Consumes(MediaType.APPLICATION_JSON)	// define q só vai receber (consumir) Json
@Produces(MediaType.APPLICATION_JSON)	// define q vai retornar (produzir) um json
@Path("/")
public class CommentResource {

	// cria um objeto que gera as operações mesmo
	CommentService commentService = new CommentService();
	
	
	// ======================================================================
	// GET 
	// Return a List of Comments for a specific Message
	// Return Messages by author @queryParam author
	// ======================================================================
	@GET
	public List<Comment> getCommentsByMessage(@PathParam("messageId") long messageId, 
											  @QueryParam("author") String author) {   // .../comments?author=<string>
		if (author != null ) {
			return (commentService.getCommentsbyAuthor(messageId, author));
		} 
		return (commentService.getAllCommentsByMessage(messageId));
	}
	

	// ======================================================================
	// GET - specific comment
	// ======================================================================
	@GET
	@Path("/{commentId}")
	public Comment getCommentID(@PathParam("messageId") long messageId,
								@PathParam("commentId") long commentId) { 
		return (commentService.getComment(messageId, commentId));
	}
	
	// ======================================================================
	// POST
	// ======================================================================
	@POST
	public Comment postComment (@PathParam("messageId") long messageId, Comment comment) {
		return (commentService.addComment(messageId, comment));
	}

	// ======================================================================
	// PUT
	// ======================================================================
	@PUT
	@Path("/{commentId}")
	public Comment putComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment) {
		return (commentService.updateComment(messageId, commentId, comment));
	}
	
	// ======================================================================
	// DELETE
	// ======================================================================
	@DELETE
	@Path("/{commentId}")
	public Comment deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		return (commentService.removeComment(messageId, commentId));
	}

	
	
}
