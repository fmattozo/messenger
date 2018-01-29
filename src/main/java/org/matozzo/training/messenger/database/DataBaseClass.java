package org.matozzo.training.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.matozzo.training.messenger.model.Comment;
import org.matozzo.training.messenger.model.Message;
import org.matozzo.training.messenger.model.Profile;

public class DataBaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<Long, Comment> comments = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<Long, Comment> getComments(){
		return comments;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}

}
