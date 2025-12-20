package dto;

public class RispostaIssueDTO 
{
	private int idIssue;
	private String risposta;
	
	public RispostaIssueDTO(Integer idIssue, String risposta) {
		super();
		this.idIssue = idIssue;
		this.risposta = risposta;
	}
	
	public RispostaIssueDTO() {}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}
