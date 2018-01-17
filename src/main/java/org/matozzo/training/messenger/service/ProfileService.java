package org.matozzo.training.messenger.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.matozzo.training.messenger.database.DataBaseClass;
import org.matozzo.training.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DataBaseClass.getProfiles();

	public ProfileService() {
		profiles.put("Matozzo", new Profile(1L, "Matozzo", "Fabiano", "Matozzo"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(identifyLastId(profiles));
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(String profileName, Profile profile) {
		if (!profiles.containsKey(profileName)) {
			return null;
		}
		profiles.put(profileName, profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		if (!profiles.containsKey(profileName)) {
			return null;
		}
		profiles.remove(profileName);
		return null;
	}

	// Corre os profiles identificando qual o maior ID
	private long identifyLastId(Map<String, Profile> profiles) {

		long lastId = 0;
		Iterator<Map.Entry<String, Profile>> entries = profiles.entrySet().iterator();
		while (entries.hasNext()) {
		  Map.Entry<String, Profile> entry = entries.next();
		  //String key = entry.getKey();
		  Profile value = entry.getValue();
		  
		  if (lastId < value.getId()) lastId = value.getId();
		}	
		
		return lastId;
	}

}
