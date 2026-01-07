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
		st.setDate(7, java.sql.Date.valueOf(issue.getData()));
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
	
//	public ArrayList<IssueDTO> creaElenco(ResultSet risposta) throws SQLException {
//		ArrayList<IssueDTO> elenco = new ArrayList<IssueDTO>();
//		while(risposta.next())
//		{
//			System.out.print("ID: " + risposta.getLong("idIssue") + "\n");
//			ArrayList<String> imageNames = new ArrayList<String>(5);
//			for(int i=1; i<=5; i++)
//				imageNames.add(risposta.getString("nomeFoto" + (i)));
//			IssueDTO issue = 
//			elenco.add(new IssueDTO(
//					risposta.getLong("idIssue"),
//					risposta.getLong("idProgetto"),
//					risposta.getString("nomeProgetto"),
//					risposta.getString("tipologia"),
//					risposta.getString("priority"),
//					risposta.getString("titoloIssue"),
//					risposta.getString("descrizione"),
//					risposta.getDate("dataApertura").toLocalDate(),
//					imageNames
//				));
//		}
//		return elenco;
//	}

	@Override
	public boolean salvaRisposta(RispostaIssueDTO risposta, long idUtente) throws SQLException {
		Connection database = PostgresConnection.connect();
		String query = 	 "UPDATE \"Issue\" SET \"risposta\" = ? "
						+"WHERE \"idIssue\" = ? AND \"utenteAssegnato\" = ? ";
		PreparedStatement st = database.prepareStatement(query);
		st.setString(1, risposta.getRisposta());
		st.setLong(2, risposta.getIdIssue());
		st.setLong(3, idUtente);
		
		int result = st.executeUpdate();
		return result > 0;
	}
}