package service;

import dto.ImageDTO;
import exceptions.FileNotUploadedException;
import jakarta.ws.rs.core.Response;

class FileUploaderWrapper {
    public Response uploadFile(ImageDTO file, Long idUtente, Long idIssue) throws FileNotUploadedException {
        return FileUploader.uploadFile(file, idUtente, idIssue);
    }
}

