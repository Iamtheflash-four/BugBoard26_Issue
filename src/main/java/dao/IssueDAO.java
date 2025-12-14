package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import entity.Issue;

public interface IssueDAO 
{
	public int insertIssue(int idUtente, Issue issue, ArrayList<String> imageNames) throws Exception;
}
