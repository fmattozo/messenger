package org.matozzo.training.messenger.resources;
//======================================================================
// EXEMPLOS de outros tipos de parametros
//======================================================================

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/injectDemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	// ======================================================================
	// http://localhost:8080/messenger/training/injectDemo/annotations;param=value
	// tem q existir os valores de header cookie
	// ======================================================================
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,		// similar queryparameter, mas usa ";" ou invés do "?" e pode ter varios 
											@HeaderParam("authSessionID") String header,	// Acessa um parametro do header
											@CookieParam("name") String cookie) {			// Acessa um cookie
																							// tb Existe o FormParam mas não é usado.
		
		return "Matrix param: " + matrixParam + " Header param: " + header + " Cookie param: " + cookie;
	}
	
	
	// ======================================================================
	// Pegando todo um contexto
	// http://localhost:8080/messenger/training/injectDemo/context
	// ======================================================================
	@GET
	@Path("context")
	public String getParamUsingContext(@Context UriInfo uriInfo,		// Pega todos os parametros da URI
									   @Context HttpHeaders headers		// Paga todo o header
									   ) {		
		
		String path = uriInfo.getAbsolutePath().toString();
		String header = headers.getCookies().toString();
		return "Path: " + path + " | Cookies: " + header;
		
	}
	

}