package dto;

import java.io.File;
import java.util.ArrayList;

import entity.Issue;
import entity.Utente;

public class IssueWithImagesDTO extends entity.Issue
{
	private ArrayList<File> images;
	
	public IssueWithImagesDTO(String progetto, String priorita, String titolo, String descrizione, String tokenUtente, ArrayList<File> images)
	{
		super(progetto, priorita, titolo, descrizione, tokenUtente);
		this.images = images;
	}
	
	public IssueWithImagesDTO(Issue issue, ArrayList<File> images) 
	{
		this(issue.getProgetto(), issue.getPriorita(), issue.getTitolo(), issue.getDescrizione(), 
			issue.getTokenUtente(), images);
	}
	
	public IssueWithImagesDTO() {}

	public ArrayList<File> getImages() {
		return images;
	}

	public void setImages(ArrayList<File> images) {
		this.images = images;
	}
}