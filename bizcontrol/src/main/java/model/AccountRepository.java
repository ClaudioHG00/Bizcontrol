package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
	
	public String findAccount(String nome, String password) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT permesso.tipo FROM permesso "
				+ "INNER JOIN account "
				+ "ON permesso.id = account.idPermesso "
				+ "WHERE (account.username = ? "
				+ "       OR account.email = ?) "
				+ "AND account.password = ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1,nome);
			ps.setString(2, nome);
			ps.setString(3, password);
			
			rs = ps.executeQuery();
			String permesso = null;
			
			if (rs.next()) {
				permesso = rs.getString(1);
			}
			ps.close();
			rs.close();
			return permesso;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
