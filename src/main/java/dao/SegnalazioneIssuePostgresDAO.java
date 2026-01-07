package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.IssueDTO;

public class SegnalazioneIssuePostgresDAO implements SegnalazioniIssueDAO 
{
	@Override
	public ArrayList<IssueDTO> getIssueAssegnateToUser(long idUtente) throws Exception
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" = ? AND risposta IS NULL";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
		
	@Override
	public ArrayList<IssueDTO> getIssueSegnalate() throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" IS NULL";
		PreparedStatement st = database.prepareStatement(query);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	
	@Override
	public ArrayList<IssueDTO> getIssueSegnalateByUtente(long idUtente) throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteSegnalatore\" = ?";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	
	@Override
	public ArrayList<IssueDTO> getIssueSegnalateByUtente(int idUtente, String priorita) throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteSegnalatore\" = ? AND priority = ?";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		st.setString(2, priorita);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	
	@Override
	public ArrayList<IssueDTO> getIssueSegnalateAdmin(long idUtente) throws SQLException 
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" IS NULL";
		PreparedStatement st = database.prepareStatement(query);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}

	
	@Override
	public ArrayList<IssueDTO> getIssueAssegnateByUserAndPriority(int idUtente, String priorita) throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = 	  "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" = ? "
						+ "AND priority = ?  AND risposta IS NULL";
		PreparedStatement st = database.prepareStatement(query);
		st.setLong(1, idUtente);
		st.setString(2, priorita);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
		
	@Override
	public ArrayList<IssueDTO> getIssueSegnalateAdminWithPriority(long idUtente, String priorita) throws SQLException 
	{
		Connection database = PostgresConnection.connect();
		String query = "SELECT * FROM \"Issue\" WHERE \"utenteAssegnato\" IS NULL AND priority = ?";
		PreparedStatement st = database.prepareStatement(query);
		st.setString(1, priorita);
		ResultSet risposta = st.executeQuery();
		
		return creaElenco(risposta);
	}
	
	public ArrayList<IssueDTO> creaElenco(ResultSet risposta) throws SQLException {
		ArrayList<IssueDTO> elenco = new ArrayList<IssueDTO>();
		while(risposta.next())
		{
			System.out.print("ID: " + risposta.getLong("idIssue") + "\n");
			ArrayList<String> imageNames = new ArrayList<String>(5);
			for(int i=1; i<=5; i++)
				imageNames.add(risposta.getString("nomeFoto" + (i)));
			IssueDTO issue = new IssueDTO(
					risposta.getLong("idIssue"),
					risposta.getLong("idProgetto"),
					risposta.getString("nomeProgetto"),
					risposta.getString("tipologia"),
					risposta.getString("priority"),
					risposta.getString("titoloIssue"),
					risposta.getString("descrizione"),
					risposta.getDate("dataApertura").toLocalDate()
				);
			issue.setImageNames(imageNames);
			issue.setRisposta(risposta.getString("risposta"));					
			elenco.add(issue);
		}
		return elenco;
	}
}
