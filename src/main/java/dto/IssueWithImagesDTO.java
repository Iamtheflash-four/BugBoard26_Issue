package dto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entity.Issue;
import jakarta.json.bind.annotation.JsonbTransient;

public class IssueWithImagesDTO 
{
	private Issue issue;
	private ArrayList<ImageDTO> images;
	
	public IssueWithImagesDTO(Issue issue, ArrayList<ImageDTO> images) {
		super();
		this.issue = issue;
		this.images = images;
	}

	public IssueWithImagesDTO() {}

	public Issue getIssue() {
		return issue;
	}

	public ArrayList<ImageDTO> getImages() {
		return images;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public void setImages(ArrayList<ImageDTO> images) {
		this.images = images;
	}
}