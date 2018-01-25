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

import org.matozzo.training.messenger.model.Profile;
import org.matozzo.training.messenger.service.ProfileService;

@Path("/profiles")						// define uri.. http://blablabla/<profiles>
@Consumes(MediaType.APPLICATION_JSON)	// define q só vai receber (consumir) Json
@Produces(MediaType.APPLICATION_JSON)	// define q vai retornar (produzir) um json
public class ProfileResource {
	
	
	ProfileService profileService = new ProfileService();
	
	// ======================================================================
	// 
	// ======================================================================
	@GET
	public List<Profile> getAllProfiles(){
		return profileService.getAllProfiles();
	}
	
	// ======================================================================
	// 
	// ======================================================================
	@GET
	@Path("/profileName")
	public Profile getProfile(@PathParam("profileName") String profileName ) {
		return profileService.getProfile(profileName);
	}
	
	// ======================================================================
	// 
	// ======================================================================
	@POST
	public Profile postProfile (Profile profile) {
		return profileService.addProfile(profile);
	}
	
	// ======================================================================
	// 
	// ======================================================================
	@PUT
	@Path("/profileName")
	public Profile putProfile(@PathParam("profileName") String profileName, Profile profile) {
		return profileService.updateProfile(profileName, profile);
	}
	
	// ======================================================================
	// 
	// ======================================================================
	@DELETE
	@Path("/profileName")
	public Profile deleteProfile(@PathParam("profileName") String profileName) {
		return profileService.removeProfile(profileName);
	}
	

}
