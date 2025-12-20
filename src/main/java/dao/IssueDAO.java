package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.IssueDTO;
import dto.RispostaIssueDTO;
import entity.Issue;

public interface IssueDAO 
{
	public int insertIssue(int idUtente, Issue issue, ArrayList<String> imageNames) throws Exception;

	public ArrayList<IssueDTO> getIssueAssegnateByUser(int idUtente) throws Exception;

	boolean salvaRisposta(RispostaIssueDTO risposta, int idUtente) throws Exception;
}
