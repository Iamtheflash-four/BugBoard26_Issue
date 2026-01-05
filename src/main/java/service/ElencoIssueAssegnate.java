package service;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import dao.IssuePostgresDAO;
import dao.SegnalazioneIssuePostgresDAO;
import dto.IssueDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;

@Path("/issue")
public class ElencoIssueAssegnate
{
	@Path("/elencoIssueAssegnate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response elencoIssueAssegnateRequest(@HeaderParam("Token") String token)
	{
		try {
			int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
					.validateUserTokenAndGetID(token);
			ArrayList<IssueDTO> elencoIssue = 
				new SegnalazioneIssuePostgresDAO().getIssueAssegnateToUser(idUtente);
			return Response.status(Response.Status.OK)
					.entity(elencoIssue).build();
			
		} catch (TokenExpiredException e) {
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
}
