package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.IssueDTO;
import dto.RispostaIssueDTO;
import entity.Issue;

public class IssuePostgresDAO implements IssueDAO 
{
	@Override
	public long insertIssue(long idUtente, Issue issue, ArrayList<String> imageNames) throws Exception 
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
		    return rs.getLong(1);
		return -1;
	}

	private String makeQuery() {
		return  "insert into \"Issue\" ("
				+ "	   \"idProgetto\","
				+ "	   \"nomeProgetto\","
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
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	private void loadValues(PreparedStatement st, long idUtente, Issue issue, ArrayList<String> imageNames) throws SQLException 
	{
		st.setLong(1, issue.getIdProgetto());
		st.setString(2, issue.getProgetto());
		st.setString(3, issue.getTitolo());
		st.setString(4, issue.getDescrizione());
		st.setString(5, issue.getTipo());
		st.setString(6, issue.getPriorita());
		st.setDate(7, new Date(issue.getData().getTime()));
		st.setString(8, "todo");
		for(int i=1; i<=5; i++)
		{
			if(i < imageNames.size())
				st.setString(8+i, imageNames.get(i-1));	//i-1 perchè gli indici partono da 0
			else 
				st.setString(8+i, null);
		}
		st.setLong(14, idUtente);
	}

	@Override
	public ArrayList<IssueDTO> getIssueAssegnateToUser(long idUtente) throws Exception
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" = ?";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	

	public ArrayList<IssueDTO> creaElenco(ResultSet risposta) throws SQLException {
		ArrayList<IssueDTO> elenco = new ArrayList<IssueDTO>();
		while(risposta.next())
		{
			ArrayList<String> imageNames = new ArrayList<String>(5);
			for(int i=1; i<=5; i++)
				imageNames.add(risposta.getString("nomeFoto" + (i)));
			
			elenco.add(new IssueDTO(
					risposta.getLong("idIssue"),
					risposta.getLong("idProgetto"),
					risposta.getString("nomeProgetto"),
					risposta.getString("tipologia"),
					risposta.getString("priority"),
					risposta.getString("titoloIssue"),
					risposta.getString("descrizione"),
					risposta.getDate("dataApertuta"),
					imageNames
				));
		}
		return elenco;
	}

	@Override
	public boolean salvaRisposta(RispostaIssueDTO risposta, long idUtente) throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = 	 "UPDATE \"Issue\" SET \"risposta\" = ?, \"statoIssue\" = ? "
						+"WHERE \"idIssue\" = ? AND \"utenteAssegnato\" = ? ";
		PreparedStatement st = database.prepareStatement(query);
		st.setString(1, risposta.getRisposta());
		st.setString(2, "Completed");
		st.setInt(4, risposta.getIdIssue());
		st.setLong(4, idUtente);
		
		int result = st.executeUpdate();
		return result > 0;
	}

	@Override
	public ArrayList<IssueDTO> getIssueSegnalate() throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" = NULL";
		PreparedStatement st = database.prepareStatement(query);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}

	public ArrayList<IssueDTO> getIssueSegnalateByUtente(long idUtente) throws SQLException 
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteSegnalatore\" = ?";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	
	public ArrayList<IssueDTO> getIssueSegnalateAdmin(long idUtente) throws SQLException 
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" = NULL";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
}