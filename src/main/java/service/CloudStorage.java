package service;

import java.io.File;

import dto.ImageDTO;
import jakarta.ws.rs.core.Response;

public interface CloudStorage 
{
	public Response makePutReqeust(ImageDTO file, String path) throws Exception;
}