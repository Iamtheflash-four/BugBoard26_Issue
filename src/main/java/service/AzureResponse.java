package service;

import java.io.File;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.glassfish.grizzly.http.server.HttpServer;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.Builder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class AzureResponse implements CloudStorage
{	
	private WebTarget target;
	private HttpRequest request;
	private BlobServiceClient serviceClient;
	private BlobClient blobClient;
	private BlobContainerClient containerClient;
	
	public AzureResponse()
	{
		//target = client.target("https://bugboardfiles26.blob.core.windows.net/bug-board26-issue"); 
		
		serviceClient = new BlobServiceClientBuilder()
	        .endpoint("https://bugboardfiles26.blob.core.windows.net/")
	        .credential(new DefaultAzureCredentialBuilder().build())
	        .buildClient();

		// If needed, you can create a BlobContainerClient object from the BlobServiceClient
		containerClient = serviceClient
	        .getBlobContainerClient("bug-board26-issue");

//		// If needed, you can create a BlobClient object from the BlobContainerClient
//		blobClient = containerClient
//	        .getBlobClient();
		
	}
	
	public Response makePutReqeust(File file, String path) throws Exception
	{
//    	byte[] stream = Files.readAllBytes(file.toPath());
//    	String date = DateTimeFormatter.RFC_1123_DATE_TIME
//			    .format(ZonedDateTime.now(ZoneOffset.UTC));
//    	
//    	request = HttpRequest.newBuilder()
//	         .uri(URI.create(target.getUri().toString()))
//	         .header("x-ms-version", "2025-11-05")
//	         .header("x-ms-blob-type", "BlockBlob")
//	         .header("x-ms-date", date)
//	         .build();
//    	String signature = makeSignature("LLUKmXhzmuOzr/AFK03WfwnlEocGS5iB23tZSVXZn0U5c2fmAYoy29tynfj6PMHapOpQiiT3zyvR+AStMseZ9w==",
//    			request, file, path);
//    	return target.path(path).request(request.toString())
//    			.header("Authorization", "SharedKey bugboardfiles26:" + signature)
//    			.put(Entity.entity(stream, MediaType.APPLICATION_OCTET_STREAM));	
    	blobClient = containerClient.getBlobClient(file.getName());
    	try {
            blobClient.uploadFromFile(file.getPath());
            return Response.status(Status.CREATED).entity("ok").build();
        } catch (UncheckedIOException ex) {
            return Response.status(Status.BAD_REQUEST).entity("Bad request").build();
        }
	}
	
	public String makeSignature(String key, HttpRequest request, File file, String path) throws Exception
	{
		Mac mac = Mac.getInstance("HmacSHA256");
		//HMAC della richiesta in Base64
    	mac.init(new SecretKeySpec(Base64.getDecoder().decode(key), "HmacSHA256"));
    	//Cripta
    	byte[] hmac = mac.doFinal(makeRequest(file, path, request).getBytes(StandardCharsets.UTF_8));
    	//Converti in testo
    	return  Base64.getEncoder().encodeToString(hmac);
	}
	
	private String makeRequest(File file, String path, HttpRequest request)
	{	
		String date = request.headers().firstValue("x-ms-date").toString();
		String head = 
		        "PUT\n" +	//Method
		        "\n" + // Content-Encoding
		        "\n" + // Content-Language
		        file.length()+ "\n" + // Content-Length
		        "\n" + // Content-MD5
		        "application/octet-stream\n" + // Content-Type
		        "\n" + // Date
		        "\n" + // If-Modified-Since
		        "\n" + // If-Match
		        "\n" + // If-None-Match
		        "\n" + // If-Unmodified-Since
		        "\n" + // Range
		        "x-ms-blob-type:BlockBlob\n" +
		        "x-ms-date:" + date + "\n" + 
		        "x-ms-version:" + request.headers().firstValue("x-ms-version") +
		        "/" + "bugboardfiles26" + "/bug-board26-issue/" + path;
		return head;
	}
}
