package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.IssueDTO;
import dto.RispostaIssueDTO;
import entity.Issue;

public interface IssueDAO 
{
	public long insertIssue(long idUtente, Issue issue, ArrayList<String> imageNames) throws Exception;

	public ArrayList<IssueDTO> getIssueAssegnateByUser(long idUtente) throws Exception;

	boolean salvaRisposta(RispostaIssueDTO risposta, long idUtente) throws Exception;
}
