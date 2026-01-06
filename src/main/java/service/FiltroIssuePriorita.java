package service;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
public class FiltroIssuePriorita {
    
    @Path("/filtro/priorita/utente/assegnate")
    @GET
    public Response filtraIssueAssegnateUtenteRequest(
            @HeaderParam("Token") String token,
            @QueryParam("priorita") String priorita) 
    {
        
        try {
            int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
                    .validateUserTokenAndGetID(token);
            
            ArrayList<IssueDTO> elencoIssue;
            
            if (priorita == null || priorita.equals("Tutte") || priorita.isEmpty()) 
            	elencoIssue = new SegnalazioneIssuePostgresDAO().getIssueAssegnateToUser(idUtente);
            else 
            	elencoIssue = new SegnalazioneIssuePostgresDAO()
                        .getIssueAssegnateByUserAndPriority(idUtente, priorita);
            
            
            return Response.status(Response.Status.OK)
                    .entity(elencoIssue).build();
            
        } catch (TokenExpiredException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token scaduto").build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token non valido").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
    
    @Path("/filtro/priorita/utente/segnalate")
    @GET
    public Response filtraIssueSegnalateUtenteRequest(
            @HeaderParam("Token") String token,
            @QueryParam("priorita") String priorita) 
    {
        
        try {
            int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
                    .validateUserTokenAndGetID(token);
            
            ArrayList<IssueDTO> elencoIssue;
            
            if (priorita == null || priorita.equals("Tutte") || priorita.isEmpty()) 
            	elencoIssue = new SegnalazioneIssuePostgresDAO().getIssueSegnalateByUtente(idUtente);
            else 
            	elencoIssue = new SegnalazioneIssuePostgresDAO()
                        .getIssueSegnalateByUtente(idUtente, priorita);
            
            
            return Response.status(Response.Status.OK)
                    .entity(elencoIssue).build();
            
        } catch (TokenExpiredException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token scaduto").build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token non valido").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
    
    
    @Path("/filtro/priorita/admin/segnalate")
    @GET
    public Response filtraIssueSegnateAdminRequest(
            @HeaderParam("Token") String token,
            @QueryParam("priorita") String priorita) 
    {
        
        try {
            int idUtente = new TokenGenerator(System.getenv("JWT_SECRET"))
                    .validateAdminTokenAndGetID(token);
            
            ArrayList<IssueDTO> elencoIssue;
            
            if (priorita == null || priorita.equals("Tutte") || priorita.isEmpty()) 
            	elencoIssue = new SegnalazioneIssuePostgresDAO().getIssueSegnalateAdmin(idUtente);
            else 
            	elencoIssue = new SegnalazioneIssuePostgresDAO()
                    .getIssueSegnalateAdminWithPriority(idUtente, priorita);
            
            
            return Response.status(Response.Status.OK)
                    .entity(elencoIssue).build();
            
        } catch (TokenExpiredException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token scaduto").build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token non valido").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
}
