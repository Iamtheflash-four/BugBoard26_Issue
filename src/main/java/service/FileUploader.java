package service;

import java.io.File;
import java.nio.file.Files;

import dto.ImageDTO;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class FileUploader
{
    public static Response uploadFile(ImageDTO file, Integer idUtente, Integer idIssue) {
        try{
        	checkFile(file);
        	String path = idUtente + "/" + idIssue + "/" + file.getFileName();
        	path = path.replace("%2F", "/");
        	Response response = new BlobResponse().makePutReqeust(file, path);
        	return response;
        } catch (Exception e) {
            return Response.serverError().entity(
            		"Errore upload: " + e.getMessage() + "\n" + e.getClass()
            	).build() ;
        }
    }

	private static void checkFile(ImageDTO file) throws Exception {
		if (file.makeFile().length > 10 * 1024 * 1024)
			throw new Exception("Dimensione file maggiore di 10MB");
	}
	
	public static void main(String[] args)
	{
		try {
			Response r = FileUploader.uploadFile(null, 1234, 12345);
			Object s = r.getEntity();
			System.out.print("Risposta: " + r.getStatus() + " " + s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
