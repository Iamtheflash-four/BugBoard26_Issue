package dto;

import java.util.ArrayList;
import entity.Issue;


public class CreateIssueRequest 
{
	private Issue issue;
	private ArrayList<ImageDTO> images;
	
	public CreateIssueRequest(Issue issue, ArrayList<ImageDTO> images) {
		super();
		this.issue = issue;
		this.images = images;
	}

	public CreateIssueRequest() {}

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