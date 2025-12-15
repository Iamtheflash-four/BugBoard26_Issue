package dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import jakarta.json.bind.annotation.JsonbTransient;

public class ImageDTO {
	private String fileName;
	private String filePath;
	private String content;
    
    public ImageDTO() {}
    
    public ImageDTO(File file) throws IOException 
    {
    	setFile(file);
    }
    
    @JsonbTransient
	private void setFile(File file) throws IOException {
		this.fileName = file.getName();
        this.filePath = file.getParent();
        this.content = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
	}
	@JsonbTransient
	public String getPath()
	{
		if(fileName != null && this.filePath != null)
			return filePath + "/" + fileName;
		else
			return null;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public String getContent() {
		return content;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@JsonbTransient
	public byte[] makeFile()
	{
		return Base64.getDecoder().decode(content);
	}
}