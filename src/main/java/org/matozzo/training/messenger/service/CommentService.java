package org.matozzo.training.messenger.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


import org.matozzo.training.messenger.database.DataBaseClass;
import org.matozzo.training.messenger.model.Comment;




public class CommentService {
	
	private Map<Long, Comment> comments = DataBaseClass.getComments();
	
	public CommentService() {
		
	}
	
	// ======================================================================
	// Return a List of Comments for a specific Message
	// ======================================================================
	public List<Comment> getAllCommentsByMessage(long messageId) {
		List<Comment> commentsByMessage = new ArrayList<>();
		for(Comment currentComment : comments.values()) {
			if(currentComment.getMessageId() == messageId) {
				commentsByMessage.add(currentComment);
			}
		}
		return commentsByMessage;
	}
	
	// ======================================================================
	// Return the List of messages from a specific author from a specific message
	// ======================================================================
	public List<Comment> getCommentsbyAuthor(long messageId, String commentAuthor){
		
		List<Comment> commentsByMessage = getAllCommentsByMessage(messageId);
		List<Comment> commentsByAuthor = new ArrayList<>();
		
		for(Comment currentComment : commentsByMessage) {
			if(currentComment.getAuthor() == commentAuthor) {
				commentsByAuthor.add(currentComment);
			}
		}
		return commentsByAuthor;
	}
	
	// ======================================================================
	// Get a specific comment
	// ======================================================================
	public Comment getComment(long messageId, long commentId) {
		if(comments.containsKey(commentId)) {
			Comment selectedComment = comments.get(commentId);
			if (selectedComment.getMessageId() == messageId) {
				return (selectedComment);
			}	
		}
		
		return null;
	}
	
	// ======================================================================
	// Add Comment
	// ======================================================================
	public Comment addComment(long messageId, Comment comment) {
		comment.setCommentId(generateCommentId());
		comment.setMessageId(messageId);
		if (comment.getCreated() == null) comment.setCreated(new Date());
		comments.put(comment.getCommentId(), comment);
		return comment;
	}
	
	// ======================================================================
	// identify the last commentId and +1, generate the next ID
	// ======================================================================
	public long generateCommentId() {
		Long last = 0L;
		if (comments.size() > 0) {
			last = new TreeSet<Long>(comments.keySet()).last();
		}
		return (last + 1);
	}
	
	// ======================================================================
	// Update.. put
	// ======================================================================
	public Comment updateComment(long messageId, long commentId, Comment comment) {
		if(!comments.containsKey(comment.getCommentId())) {
			return null;
		}
		if(comments.get(commentId).getMessageId() != messageId || comment.getMessageId() != messageId) {

			return null;
		}
		comment.setCreated(new Date());
		comments.put(comment.getCommentId(), comment);
		return comment;
	}
	
	// ======================================================================
	// Delete
	// ======================================================================
	public Comment removeComment(long messageId, long commentId) {
		if(!comments.containsKey(commentId)) {
			return null;
		}
		if (comments.get(messageId).getMessageId() != messageId) {
			return null;
		}
		comments.remove(commentId);
		return null;
	}
	


}
