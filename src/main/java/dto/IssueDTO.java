package dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class IssueDTO extends entity.Issue
{
	private long idIssue;
	private ArrayList<String> imageNames;
	private String risposta;
	
	public IssueDTO() {}
	
	public IssueDTO(long idIssue, long idProgetto, String progetto, String tipo, String priorita, String titolo, String descrizione, LocalDate data)
	{
		super(idProgetto, progetto, tipo, priorita, titolo, descrizione, data);
		this.idIssue = idIssue;
		this.imageNames = null;
		this.risposta = null;
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

	public String getRisposta() {
		return risposta;
	}

	public void setIdIssue(long idIssue) {
		this.idIssue = idIssue;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}