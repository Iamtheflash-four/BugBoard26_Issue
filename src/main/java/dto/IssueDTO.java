package dto;

import java.util.Date;
import java.util.ArrayList;

public class IssueDTO extends entity.Issue
{
	long idIssue;
	ArrayList<String> imageNames;
	
	public IssueDTO(long idIssue, long idProgetto, String progetto, String tipo, String priorita, String titolo, 
			String descrizione, Date data, ArrayList<String> imageNames) {
		super(idProgetto, progetto, tipo, priorita, titolo, descrizione, data);
		this.idIssue = idIssue;
		this.imageNames = imageNames;
	}

	public long getIdIssue() {
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
