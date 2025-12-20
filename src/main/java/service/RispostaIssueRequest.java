package service;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import dao.IssuePostgresDAO;
import dto.RispostaIssueDTO;
import exceptions.FileNotUploadedException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;

@Path("issue")
public class RispostaIssueRequest 
{
	@POST
	@Path("risposte")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) 
	public Response salvaRisposta(RispostaIssueDTO risposta,  @HeaderParam("Token") String token)
	{
		try {
			checkRisposta(risposta);
			Integer idUtente = new TokenGenerator(System.getenv("JWT_SECRET")).validateUserTokenAndGetID(token);
			
			if( new IssuePostgresDAO().salvaRisposta(risposta, idUtente) )
				return Response.status(Response.Status.OK).build();
			else
				throw new Exception("L'issue non esiste");
		}catch (TokenExpiredException e) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("Token scaduto").build();
		}
		catch(JWTVerificationException e) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("Token non valido").build();
		}
		catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	private void checkRisposta(RispostaIssueDTO risposta) throws Exception
	{
		if(	risposta==null || risposta.getIdIssue() <= 0 || 
			risposta.getRisposta() == null || risposta.getRisposta().equals("") || 
			risposta.getRisposta().length() > 500
		)
			throw new Exception("Dati non validi");
	}
}
