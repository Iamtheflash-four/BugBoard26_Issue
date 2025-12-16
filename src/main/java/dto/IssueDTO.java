package dto;

import java.util.Date;
import java.util.ArrayList;

public class IssueDTO extends entity.Issue
{
	int idIssue;
	ArrayList<String> imageNames;
	
	public IssueDTO(int idIssue,String progetto, String tipo, String priorita, String titolo, 
			String descrizione, Date data, ArrayList<String> imageNames) {
		super(progetto, tipo, priorita, titolo, descrizione, data);
		this.idIssue = idIssue;
		this.imageNames = imageNames;
	}

	public int getIdIssue() {
		return idIssue;
	}

	public ArrayList<String> getImageNames() {
		return imageNames;
	}

	public void setIdIssue(int idIssue) {
		this.idIssue = idIssue;
	}

	public void setImageNames(ArrayList<String> imageNames) {
		this.imageNames = imageNames;
	}
}
