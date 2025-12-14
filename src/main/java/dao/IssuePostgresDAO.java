package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Issue;

public class IssuePostgresDAO implements IssueDAO 
{
	@Override
	public int insertIssue(int idUtente, Issue issue, ArrayList<String> imageNames) throws Exception 
	{
		Connection database = PostgresConnection.connect();
		String query = makeQuery();
		PreparedStatement st = database.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		loadValues(st, idUtente, issue, imageNames);
		
		int rows = st.executeUpdate();
		if (rows <= 0)
			return -1;	//errore
		ResultSet rs = st.getGeneratedKeys();	//Recupera l'idIssue generato
		if (rs.next()) 
		    return rs.getInt(1);
		return -1;
	}

	private String makeQuery() {
		return  "insert into \"Issue\" ("
				+ "    \"titoloIssue\","
				+ "    \"descrizione\","
				+ "    \"tipologia\","
				+ "    \"priority\","
				+ "    \"dataApertura\","
				+ "    \"statoIssue\","
				+ "    \"nomeFoto1\","
				+ "    \"nomeFoto2\","
				+ "    \"nomeFoto3\","
				+ "    \"nomeFoto4\","
				+ "    \"nomeFoto5\","
				+ "    \"utenteSegnalatore\""
				+ "  ) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	private void loadValues(PreparedStatement st, int idUtente, Issue issue, ArrayList<String> imageNames) throws SQLException 
	{
		st.setString(1, issue.getTitolo());
		st.setString(2, issue.getDescrizione());
		st.setString(3, issue.getTipo());
		st.setString(4, issue.getPriorita());
		st.setDate(5, Date.valueOf(LocalDate.now()));
		st.setString(6, "todo");
		for(int i=1; i<=5; i++)
		{
			if(i < imageNames.size())
				st.setString(6+i, imageNames.get(i-1));	//i-1 perchè gli indici partono da 0
			else 
				st.setString(6+i, null);
		}
		st.setInt(12, idUtente);
	}
}