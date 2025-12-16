package service;

import java.util.ArrayList;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import dao.IssuePostgresDAO;
import dto.CreateIssueRequest;
import dto.ImageDTO;
import entity.Issue;
import exceptions.FileNotUploadedException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.HeaderParam;

@Path("/issue")
public class SegnalazioneIssue
{
	@POST
	@Path("/segnalazioni")
	@Consumes(MediaType.APPLICATION_JSON)   
    @Produces(MediaType.APPLICATION_JSON) 
	public static Response segnalaIssue(CreateIssueRequest issueDTO, @HeaderParam("Token") String token)
	{
		try {
			if(!checkIssue(issueDTO))
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Dati non validi").build();
			int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
					.validateUserTokenAndGetID(token);
			//idUtente restituito correrttamente
			ArrayList<String> imageNames = createImageNames(issueDTO);
			int idIssue =  new IssuePostgresDAO().insertIssue(idUtente, issueDTO.getIssue(), imageNames);
			if(idIssue >= 1)
			{
				saveImages(issueDTO.getImages(), idUtente, idIssue);
				return Response.status(Response.Status.OK).entity(null).build();
			}
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
		catch(FileNotUploadedException e) {
			return Response.status(207) //MULTI_STATUS
					.entity("Token non valido").build();
		}
		catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	private static void saveImages(ArrayList<ImageDTO> images, Integer idUtente, Integer idIssue) throws FileNotUploadedException {
		for (ImageDTO file : images)
		{
			Response response = FileUploader.uploadFile(file, idUtente, idIssue);
			if(response.getStatus() != 201)
				throw new FileNotUploadedException("File non caricato, riprovare modificando la issue");
		}
	}

	private static ArrayList<String> createImageNames(CreateIssueRequest issue) {
		ArrayList<String> imageNames = new ArrayList<String>();
		for(ImageDTO image : issue.getImages())
			imageNames.add(image.getFileName());
		return imageNames;
	}

	private static boolean checkIssue(CreateIssueRequest issueDTO) 
	{
		if(issueDTO == null)
			return false;
		Issue issue = issueDTO.getIssue();
		if(	issue == null || 
			issue.getProgetto() == null || issue.getProgetto().equals("") || 
			issue.getTipo() == null || !issue.getTipo().matches("Question|Documentation|Bug|Feature") ||
			issue.getPriorita() == null || !issue.getPriorita().matches("Alta|Media|Bassa") ||
			issue.getTitolo() == null || issue.getTitolo().equals("") ||	
			issue.getDescrizione() == null || issue.getDescrizione().equals("") ||
			issueDTO.getImages().size() > 5
		)
			return false;
		else
			return true;
	}
}
