package dto;

public class RispostaIssueDTO 
{
	private long idIssue;
	private String risposta;
	
	public RispostaIssueDTO(Long idIssue, String risposta) {
		super();
		this.idIssue = idIssue;
		this.risposta = risposta;
	}
	
	public RispostaIssueDTO() {}

	public Long getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Long idIssue) {
		this.idIssue = idIssue;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}
