package service;

import java.nio.file.Paths;

import dao.BlobResponse;
import dao.IssuePostgresDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/issue")
public class ImmaginiIssue {

    @GET
    @Path("/immagini/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImmagine(
            @HeaderParam("Token") String token,
            @HeaderParam("idIssue") int idIssue,
            @HeaderParam("nome") String nomeImmagine) 
   {
        try {
            // Validazione token
            int idRichiedente = new TokenGenerator(System.getenv("JWT_SECRET"))
                    .validateUserTokenAndGetID(token);
            long idUtente = new IssuePostgresDAO().getUtenteSegnalatore(idIssue);
            
            String path = idUtente + "/" + idIssue + "/" + nomeImmagine; 
            byte[] immagine = new BlobResponse().download(path); 
            return Response.ok(immagine)  
            		.build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore durante il download").build();
        }
    }
}

