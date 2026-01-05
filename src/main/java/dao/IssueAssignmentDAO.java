package dao;

public interface IssueAssignmentDAO {
    /**
     * Assegna una issue a un utente
     * @param idIssue ID della issue da assegnare
     * @param idUtenteAssegnato ID dell'utente a cui assegnare la issue
     * @return true se l'assegnazione ha successo, false altrimenti
     * @throws Exception
     */
	public boolean assignIssue(int idIssue, int idUtenteAssegnato) throws Exception;
    
    /**
     * Ottiene l'ID dell'utente assegnato a una issue
     * @param idIssue ID della issue
     * @return ID dell'utente assegnato, -1 se nessuno è assegnato
     * @throws Exception
     */
    public int getAssignedUser(int idIssue) throws Exception;
    
    /**
     * Rimuove l'assegnazione di una issue
     * @param idIssue ID della issue
     * @return true se la rimozione ha successo, false altrimenti
     * @throws Exception
     */
    public boolean unassignIssue(int idIssue) throws Exception;
    
    /**
     * Verifica se un utente è admin
     * @param idUtente ID dell'utente
     * @return true se l'utente è admin, false altrimenti
     * @throws Exception
     */
    public boolean isAdmin(int idUtente) throws Exception;
    
    /**
     * Verifica se un utente esiste nel sistema
     * @param idUtente ID dell'utente
     * @return true se l'utente esiste, false altrimenti
     * @throws Exception
     */
    public boolean userExists(int idUtente) throws Exception;
    
    /**
     * Verifica se una issue esiste nel sistema
     * @param idIssue ID della issue
     * @return true se la issue esiste, false altrimenti
     * @throws Exception
     */
    public boolean issueExists(int idIssue) throws Exception;
}