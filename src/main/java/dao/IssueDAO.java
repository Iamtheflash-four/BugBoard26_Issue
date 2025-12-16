package dao;

import java.util.ArrayList;

import dto.IssueDTO;
import entity.Issue;

public interface IssueDAO 
{
	public int insertIssue(int idUtente, Issue issue, ArrayList<String> imageNames) throws Exception;

	public ArrayList<IssueDTO> getIssueAssegnateByUser(int idUtente) throws Exception;
}
