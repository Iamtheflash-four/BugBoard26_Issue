package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.Issue;

public class IssuePostgresDAO implements IssueDAO 
{
	@Override
	public int insertIssue(Issue issue) throws SQLException 
	{
		Connection database = PostgresConnection.connect();
		String query = "insert into Issue";
		PreparedStatement st = database.prepareStatement(query);
		return 0;
	}
}
