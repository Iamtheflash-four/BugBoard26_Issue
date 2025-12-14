package service;

import java.io.File;
import java.io.FileInputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;

import dto.ImageDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class BlobResponse implements CloudStorage
{	
	private WebTarget target;
	private Client client;
	
	public BlobResponse()
	{
		client = ClientBuilder.newClient();
	}
	
	public Response makePutReqeust(ImageDTO file, String path) throws Exception
	{
		byte[] stream = file.makeFile();
		Response response = client.target(
				"https://bugboardfiles26.blob.core.windows.net/bug-board26-issue/"
				+ path
				+ "?" + System.getenv("IMAGES_BLOB_TOKEN")
			)
			.request()
			.header("x-ms-blob-type", "BlockBlob")
			.put(Entity.entity(stream, MediaType.APPLICATION_OCTET_STREAM));
		
        return response;
	}
}
