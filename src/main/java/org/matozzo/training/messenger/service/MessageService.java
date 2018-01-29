package org.matozzo.training.messenger.service;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.matozzo.training.messenger.database.DataBaseClass;
import org.matozzo.training.messenger.model.Message;


public class MessageService {
	
	private Map<Long, Message> messages = DataBaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Helo Word", "Matozzo"));
		messages.put(2L, new Message(2, "Helo Jersy", "Matozzo."));
		
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		return messages.get(id);
	}
	
	
	// ======================================================================
	// exemplo de for each
	// exemplo de trabalho com datas
	// ======================================================================
	public List<Message> getAllMessagesByYear (int year) {
		List<Message> messagesByYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message currentMessage : messages.values()) {
			cal.setTime(currentMessage.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesByYear.add(currentMessage);
			}
		}
		
		return messagesByYear;
	}
	
	public List<Message> messagePaginated(int paginationStart, int paginationSize){
		if((messages.size()-paginationStart) < paginationSize) {
			paginationSize= (messages.size()-paginationStart);
			
		}
		ArrayList<Message> list = new ArrayList<>(messages.values());
		
		return list.subList(paginationStart, paginationStart+paginationSize);
	}
	
	public Message addMessage(Message message) {
		Long last = new TreeSet<Long>(messages.keySet()).last();
		message.setId(last +1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(!messages.containsKey(message.getId())) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		if(!messages.containsKey(id)) {
			return null;
		}
		messages.remove(id);
		return null;
	}
	

	
	
}
