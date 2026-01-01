package service;

import java.util.ArrayList;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import dao.IssuePostgresDAO;
import dto.IssueDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/issue")
public class ElencoIssueSegnalateAdmin 
{
	@Path("/elencoIssueSegnalateAdmin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response elencoIssueSegnalateRequest(@HeaderParam("Token") String token)
	{
		try {
			long idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
					.validateAdminTokenAndGetID(token);
			ArrayList<IssueDTO> elencoIssue = new IssuePostgresDAO().getIssueSegnalate();
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
