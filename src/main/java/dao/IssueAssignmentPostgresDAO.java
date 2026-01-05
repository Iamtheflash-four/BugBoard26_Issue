package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueAssignmentPostgresDAO implements IssueAssignmentDAO {
    
    @Override
    public boolean assignIssue(int idIssue, int idUtenteAssegnato) throws SQLException {
        Connection database = null;
        PreparedStatement updateSt = null;
        
        try {
            database = PostgresConnection.connect();
            
            // Update della issue con l'utente assegnato
            String updateQuery = "UPDATE \"Issue\" SET \"utenteAssegnato\" = ? WHERE \"idIssue\" = ?";
            updateSt = database.prepareStatement(updateQuery);
            updateSt.setInt(1, idUtenteAssegnato);
            updateSt.setInt(2, idIssue);
            
            int rowsAffected = updateSt.executeUpdate();
            
            return rowsAffected > 0;
            
        } finally {
            if (updateSt != null) updateSt.close();
            if (database != null) database.close();
        }
    }
    
    @Override
    public int getAssignedUser(int idIssue) throws SQLException {
        Connection database = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            database = PostgresConnection.connect();
            
            String query = "SELECT \"utenteAssegnato\" FROM \"Issue\" WHERE \"idIssue\" = ?";
            st = database.prepareStatement(query);
            st.setInt(1, idIssue);
            
            rs = st.executeQuery();
            
            if (rs.next()) {
                int assignedUser = rs.getInt("utenteAssegnato");
                return rs.wasNull() ? -1 : assignedUser;
            }
            
            return -1; // Issue non trovata
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (database != null) database.close();
        }
    }
    
    @Override
    public boolean unassignIssue(int idIssue) throws SQLException {
        Connection database = null;
        PreparedStatement updateSt = null;
        
        try {
            database = PostgresConnection.connect();
            
            String updateQuery = "UPDATE \"Issue\" SET \"utenteAssegnato\" = NULL WHERE \"idIssue\" = ?";
            updateSt = database.prepareStatement(updateQuery);
            updateSt.setInt(1, idIssue);
            
            int rowsAffected = updateSt.executeUpdate();
            
            return rowsAffected > 0;
            
        } finally {
            if (updateSt != null) updateSt.close();
            if (database != null) database.close();
        }
    }
    
    @Override
    public boolean isAdmin(int idUtente) throws SQLException {
        Connection database = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            database = PostgresConnection.connect();
            
            String query = "SELECT \"admin\" FROM \"Utente\" WHERE \"id_Utente\" = ?";
            st = database.prepareStatement(query);
            st.setInt(1, idUtente);
            
            rs = st.executeQuery();
            
            if (rs.next()) {
                return rs.getBoolean("admin");
            }
            
            return false;
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (database != null) database.close();
        }
    }
    
    @Override
    public boolean userExists(int idUtente) throws SQLException {
        Connection database = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            database = PostgresConnection.connect();
            
            String query = "SELECT \"id_Utente\" FROM \"Utente\" WHERE \"id_Utente\" = ?";
            st = database.prepareStatement(query);
            st.setInt(1, idUtente);
            
            rs = st.executeQuery();
            
            return rs.next();
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (database != null) database.close();
        }
    }
    
    @Override
    public boolean issueExists(int idIssue) throws SQLException {
        Connection database = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            database = PostgresConnection.connect();
            
            String query = "SELECT \"idIssue\" FROM \"Issue\" WHERE \"idIssue\" = ?";
            st = database.prepareStatement(query);
            st.setInt(1, idIssue);
            
            rs = st.executeQuery();
            
            return rs.next();
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (database != null) database.close();
        }
    }
}