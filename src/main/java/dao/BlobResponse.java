package dao;

import dto.ImageDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class BlobResponse implements CloudStorage
{	
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
	
	public byte[] download(String path) throws Exception {
	    Response response = client.target(
	            "https://bugboardfiles26.blob.core.windows.net/bug-board26-issue/"
	            + path
	            + "?" + System.getenv("IMAGES_BLOB_TOKEN")
	        )
	        .request()
	        .get();

	    if (response.getStatus() == 200) {
	        return response.readEntity(byte[].class);
	    } else {
	        throw new Exception("Errore download blob: " + response.getStatus());
	    }
	}

}
