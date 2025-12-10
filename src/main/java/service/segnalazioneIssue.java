package service;

import java.io.File;
import java.util.ArrayList;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import dao.IssuePostgresDAO;
import dto.IssueWithImagesDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;

@Path("/Issue")
public class segnalazioneIssue
{
	@POST
	@Path("/segnalazioni")
	@Consumes(MediaType.APPLICATION_JSON)   
    @Produces(MediaType.APPLICATION_JSON) 
	public static Response segnalaIssue(IssueWithImagesDTO issue)
	{
		try {
			int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
				.validateUserTokenAndGetID(null);
			if(!checkIssue(issue))
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Dati non validi").build();
			addImageNames(issue);
			saveImages(issue.getImages());
			int idIssue =  new IssuePostgresDAO().insertIssue(issue);
			if(idIssue >= 1)
				return Response.status(Response.Status.OK).entity(null).build();
			else
				throw new Exception("Errore server");
		}
		catch (TokenExpiredException e) {
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

	private static void saveImages(ArrayList<File> images) {
		
	}

	private static void addImageNames(IssueWithImagesDTO issue) {
		ArrayList<String> imageNames = new ArrayList();
		for(File file : issue.getImages())
			imageNames.add(file.getName());
		issue.setImageNames(imageNames);
	}

	private static boolean checkIssue(IssueWithImagesDTO issue) 
	{
		if(	issue == null || 
			issue.getProgetto() == null || issue.getProgetto().equals("") || 
			issue.getPriorita() == null || issue.getPriorita().equals("") ||
			issue.getTitolo() == null || issue.getTitolo().equals("") ||	
			issue.getDescrizione() == null || issue.getDescrizione().equals("") 
		)
			return false;
		else
			return true;
	}
}
