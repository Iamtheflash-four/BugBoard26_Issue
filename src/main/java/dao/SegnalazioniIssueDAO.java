package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.IssueDTO;

public interface SegnalazioniIssueDAO {

	public ArrayList<IssueDTO> getIssueAssegnateToUser(long idUtente) throws Exception;

	public ArrayList<IssueDTO> getIssueSegnalate() throws Exception;

	public ArrayList<IssueDTO> getIssueSegnalateAdmin(long idUtente) throws Exception;

	public ArrayList<IssueDTO> getIssueAssegnateByUserAndPriority(int idUtente, String priorita) throws Exception;

	public ArrayList<IssueDTO> getIssueSegnalateAdminWithPriority(long idUtente, String priorita) throws Exception;

	public ArrayList<IssueDTO> getIssueSegnalateByUtente(long idUtente) throws Exception;

	public ArrayList<IssueDTO> getIssueSegnalateByUtente(int idUtente, String priorita) throws Exception;
}