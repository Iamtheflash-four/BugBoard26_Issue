package service;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class IssueMain 
{
	public static void main(String[] args) 
    {
        startServer();
    }

	private static void startServer() {
		// Legge la porta da variabile d'ambiente, default 8080 se non definita
		int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8082"));
		
        ResourceConfig rc = new ResourceConfig().packages("service");
        
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create("http://0.0.0.0:" + port), rc);
        
        System.out.println("Server avviato su http://0.0.0.0:" + port);
	}
}
