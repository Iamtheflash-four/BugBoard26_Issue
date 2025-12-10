package service;

import java.io.File;

import jakarta.ws.rs.core.Response;

public interface CloudStorage 
{
	public Response makePutReqeust(File file, String path) throws Exception;
}