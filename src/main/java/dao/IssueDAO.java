package dao;

import java.sql.SQLException;

import entity.Issue;

public interface IssueDAO 
{
	public int insertIssue(Issue issue) throws SQLException;
}
