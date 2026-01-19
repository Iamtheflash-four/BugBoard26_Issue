package service;

import java.time.LocalDate;
import java.util.ArrayList;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import dao.IssuePostgresDAO;
import dto.CreateIssueRequest;
import dto.ImageDTO;
import entity.Issue;
import exceptions.FileNotUploadedException;
import java.util.Base64;
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
	private static TokenGenerator validator;
	private static IssuePostgresDAO issueDAO;
	static FileUploaderWrapper fileUploader = new FileUploaderWrapper();
	
	public SegnalazioneIssue()
	{
		validator = new TokenGenerator(System.getenv("JWT_SECRET"));
		issueDAO = new IssuePostgresDAO();
	}
	
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
			if(token == null)
				throw new JWTVerificationException("Token nullo");
			long idUtente = validator.validateUserTokenAndGetID(token);
			//idUtente restituito correrttamente
			ArrayList<String> imageNames = createImageNames(issueDTO);
			long idIssue =  issueDAO.insertIssue(idUtente, issueDTO.getIssue(), imageNames);
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
					.entity("Immagini non caricate").build();
		}
		catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
	}

	private static void saveImages(ArrayList<ImageDTO> images, Long idUtente, Long idIssue) throws FileNotUploadedException {
		if(images == null)
			return;
		for (ImageDTO file : images)
		{
			if (file != null) {
				Response response = fileUploader.uploadFile(file, idUtente, idIssue);
				if(response.getStatus() != 201)
					throw new FileNotUploadedException("File non caricato, riprovare modificando la issue");
			}
		}
	}

	private static ArrayList<String> createImageNames(CreateIssueRequest issue) {
		if(issue.getImages() == null)
			return null;
		ArrayList<String> imageNames = new ArrayList<String>();
		for(ImageDTO image : issue.getImages())
			if(image != null)
				imageNames.add(image.getFileName());
		return imageNames;
	}

	private static boolean checkIssue(CreateIssueRequest issueDTO) 
	{
		if(issueDTO == null)
			return false;
		Issue issue = issueDTO.getIssue();
		if(!checkIssue(issue))
			return false;
		if(!checkImages(issueDTO.getImages()))
			return false;
		return true;
	}

	protected static boolean checkIssue(Issue issue) {
		if(	issue == null || 
				issue.getProgetto() == null || issue.getProgetto().equals("") ||  
				issue.getTipo() == null || !issue.getTipo().matches("Question|Documentation|Bug|Feature") ||
				issue.getPriorita() == null || !issue.getPriorita().matches("Alta|Media|Bassa") ||
				issue.getTitolo() == null || issue.getTitolo().equals("") || issue.getTitolo().length() > 30 ||
				issue.getData() == null || issue.getData().isAfter(LocalDate.now()) ||
				issue.getDescrizione() == null || issue.getDescrizione().equals("") ||  issue.getDescrizione().length() > 500
			)
				return false;
			return true;
	}
	
	private static boolean checkImages(ArrayList<ImageDTO> images) 
	{
	    if (images == null)
	        return true;

	    if (images.size() > 5)
	        return false;

	    for (ImageDTO img : images) {
	    	if(!checkImg(img))
	        	return false;
	    }
	    return true;
	}

	protected static boolean checkImg(ImageDTO img) {
		if (img == null)
		    return true;
		String fileName = img.getFileName();
		String content  = img.getContent();
		if (fileName == null || fileName.isEmpty())
		    return false;
		if (content == null || content.isEmpty())
		    return false;
		if (!fileName.toLowerCase().matches(".*\\.(png|jpg|jpeg)$"))
		    return false;

		try {
		    Base64.getDecoder().decode(content);
		} catch (IllegalArgumentException e) {
		    return false;
		}

		int maxBytes = 5 * 1024 * 1024;
		if (Base64.getDecoder().decode(content).length > maxBytes)
		    return false;
		return true;
	}
	
	static void setValidator(TokenGenerator v) {
	    validator = v;
	}

	static TokenGenerator getValidator() {
		return validator;
	}

	static IssuePostgresDAO getIssueDAO() {
		return issueDAO;
	}

	static void setIssueDAO(IssuePostgresDAO issueDAO) {
		SegnalazioneIssue.issueDAO = issueDAO;
	}
}
