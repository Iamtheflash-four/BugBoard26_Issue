package service;

import java.io.File;
import java.nio.file.Files;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class FileUploader
{
    public static Response uploadFile(File file, Integer idUtente, Integer idIssue) {
        try{
//        	checkFile(file);
        	file = new File("C:\\Users\\Sasy\\Desktop\\System Design.pdf");
        	String path = idUtente + "/" + idIssue + "/" + file.getName();
        	Response response = new AzureResponse().makePutReqeust(file, path);
        	return response;
        } catch (Exception e) {
            return Response.serverError().entity(
            		"Errore upload: " + e.getMessage() + "\n" + e.getClass()
            	).build() ;
        }
    }

	private static void checkFile(File file) {
		//if(file.get)
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
