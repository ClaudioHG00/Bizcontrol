package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProgettoRepository {
	
	public int insertProgetto(ProgettoDTO prog) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sqlProgetto = "INSERT INTO progetto(nomeProgetto,descrizione,linkImg,costo) "
				+ "VALUES (?,?,?,?)";
		PreparedStatement psProgetto = null;
		try {
			psProgetto = conn.prepareStatement(sqlProgetto, Statement.RETURN_GENERATED_KEYS);
			psProgetto.setString(1, prog.getNomeProgetto());
			psProgetto.setString(2, prog.getDescrizione());
			psProgetto.setString(3, prog.getLinkImg());
			psProgetto.setDouble(4, prog.getCosto());
			
			num += psProgetto.executeUpdate();
			
			ResultSet rs = psProgetto.getGeneratedKeys();
			
			if(rs.next()) {
				int idProgetto = rs.getInt(1);
				String sqlWorking = "INSERT INTO working(idProgetto) VALUES (?)";
				
				PreparedStatement psWorking = null;
				psWorking = conn.prepareStatement(sqlWorking);
				psWorking.setInt(1, idProgetto);
				
				num += psWorking.executeUpdate();
				psWorking.close();
			}
			
			psProgetto.close();
			rs.close();
			return num;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return num;
		}
	}
	
	public ProgettoDTO findProgettoByNomeProgetto(String nomeProgetto) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetto "
				+ "WHERE nomeProgetto=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProgettoDTO prog = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				prog = new ProgettoDTO();
				prog.setNomeProgetto(rs.getString("nomeProgetto"));
				prog.setDescrizione(rs.getString("descrizione"));
				prog.setLinkImg(rs.getString("linkImg"));
				prog.setCosto(rs.getDouble("costo"));
//				prog.setIdWorking(rs.getInt("idWorking"));
//				prog.setIdProgetto(rs.getInt("idProgetto"));
//				prog.setIdDipendente(rs.getInt("IdDipendente"));
			}
			
			ps.close();
			rs.close();
			return prog;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<ProgettoDTO> findAllProgetti(){
		ArrayList<ProgettoDTO> arrayprog = new ArrayList<ProgettoDTO>();
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetto "
				// Una left in quanto vogliamo tutti i progetti, anche se non abbiamo lavoratori(idWorking associati)
				+ "LEFT JOIN working ON progetto.id=working.idProgetto "
				+ "GROUP BY progetto.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ProgettoDTO prog = new ProgettoDTO();
				prog.setId(rs.getInt("id"));
				prog.setNomeProgetto(rs.getString("nomeProgetto"));
				prog.setDescrizione(rs.getString("descrizione"));
				prog.setLinkImg(rs.getString("linkImg"));
				prog.setCosto(rs.getDouble("costo"));
//				prog.setIdWorking(rs.getInt("idWorking"));
//				prog.setIdDipendente(rs.getInt("idDipendente"));
//				prog.setIdProgetto(rs.getInt("idProgetto"));
				arrayprog.add(prog);
			}
			
			ps.close();
			rs.close();
			return arrayprog;
		} catch(SQLException e) {
			e.printStackTrace();
			return arrayprog;
		}
	}
	
	public ArrayList<ProgettoDTO> findAllProgettiWithPaginazione(int limit, int offset){
		ArrayList<ProgettoDTO> arrayprog = new ArrayList<ProgettoDTO>();
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetto "
				+ "LEFT JOIN working ON progetto.id=working.idProgetto "
				+ "GROUP BY progetto.id "
				+ "LIMIT ? OFFSET ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ProgettoDTO prog = new ProgettoDTO();
				prog.setNomeProgetto(rs.getString("nomeProgetto"));
				prog.setDescrizione(rs.getString("descrizione"));
				prog.setLinkImg(rs.getString("linkImg"));
				prog.setCosto(rs.getDouble("costo"));
				arrayprog.add(prog);
			}
			
			ps.close();
			rs.close();
			return arrayprog;
		} catch(SQLException e) {
			e.printStackTrace();
			return arrayprog;
		}
	}
	
	public ArrayList<ProgettoDTO> findProgettiAssociatiByUsernameEmail(String username, String email) {
		ArrayList<ProgettoDTO> arrayprog = new ArrayList<ProgettoDTO>();
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetto "
				+ "INNER JOIN working ON progetto.id=working.idProgetto "
				+ "INNER JOIN dipendente ON dipendente.id=working.idDipendente "
				+ "INNER JOIN persona ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "WHERE username=? OR email=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, email);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ProgettoDTO prog = new ProgettoDTO();
				prog.setNomeProgetto(rs.getString("nomeProgetto"));
				prog.setDescrizione(rs.getString("descrizione"));
				prog.setLinkImg(rs.getString("linkImg"));
				prog.setCosto(rs.getDouble("costo"));
				arrayprog.add(prog);
			}
			
			ps.close();
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return arrayprog;
	}
	
	// Non ho bisogno di ProgettoDTO, perche idProgetto non cambiera, sara sempre associato
	public int modifyProgettoByNomeProgetto(Progetto prog, String nomeProgettoOld) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "UPDATE progetto SET nomeProgetto=?,descrizione=?,linkImg=?,costo=? "
				+ "WHERE nomeProgetto=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, prog.getNomeProgetto());
			ps.setString(2, prog.getDescrizione());
			ps.setString(3, prog.getLinkImg());
			ps.setDouble(4, prog.getCosto());
			ps.setString(5, nomeProgettoOld);
			num = ps.executeUpdate();
			ps.close();
			
			return num;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return num;
		}
	}
	
	public int deleteProgettoByNomeProgetto(String nomeProgetto) {
		int num = 0;
		// Elimino uno alla volta, usando IN, a differenza di JOIN con le SELECT
		Connection conn = Connessione.getConnection();
		String sqlWorking = "DELETE FROM working WHERE idProgetto IN "
				+ "(SELECT id FROM progetto WHERE nomeProgetto=?)";
		String sqlProgetto = "DELETE FROM progetto WHERE nomeProgetto=?";
		PreparedStatement psWorking = null;
		PreparedStatement psProgetto = null;

		try {
			psWorking = conn.prepareStatement(sqlWorking);
			psWorking.setString(1, nomeProgetto);
			num += psWorking.executeUpdate();
			psWorking.close();

			psProgetto = conn.prepareStatement(sqlProgetto);
			psProgetto.setString(1, nomeProgetto);
			num += psProgetto.executeUpdate();
			psProgetto.close();

			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			return num;
		}
		
	}
	
	public int removeDipendenteFromNomeProgettoByCodiceFiscale(String codiceFiscale, String nomeProgetto) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "DELETE FROM working "
				// Elimino quelli che hanno idDipendente nel, e vado a ricavarmi con le join una tabella
				+ "WHERE idDipendente IN ( "
				+ "    SELECT dipendente.id "
				+ "    FROM dipendente "
				+ "    INNER JOIN persona ON persona.id=dipendente.idPersona "
				+ "    INNER JOIN progetto ON progetto.id=working.idProgetto "
				+ "    WHERE persona.codiceFiscale=? AND progetto.nomeProgetto=?"
				+ ")";
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,codiceFiscale);
			ps.setString(2,nomeProgetto);
			
			num = ps.executeUpdate();
			ps.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public int addDipendenteToNomeProgettoByCodiceFiscale(String codiceFiscale, String nomeProgetto) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		
		String sql = "INSERT INTO working (idDipendente, idProgetto) "
				// Alias per accedere ai campi e darli a working
				+ "SELECT dipendente.id AS idDipendente, progetto.id AS idProgetto "
				+ "FROM dipendente "
				+ "INNER JOIN persona ON persona.id=dipendente.idPersona "
				// una join con progetto, prendendo il nome
				+ "INNER JOIN progetto ON progetto.nomeProgetto=? "
				+ "WHERE persona.codiceFiscale=?";
//				+ "INNER JOIN working ON dipendente.id=working.idDipendente "
//				+ "INNER JOIN progetto ON progetto.id=working.idProgetto "
//				+ "WHERE persona.codiceFiscale=? AND progetto.nomeProgetto=?"
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			ps.setString(2, codiceFiscale);
			
			num = ps.executeUpdate();
			ps.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	public int countProgettiDipendente(String codiceFiscale) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) "
				+ "FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN working ON dipendente.id=working.idDipendente "
				+ "INNER JOIN progetto ON progetto.id=working.idProgetto "
				+ "WHERE codiceFiscale=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, codiceFiscale);
			
			rs = ps.executeQuery();
			
			if (rs.next()) 
				num = rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public int countAllProgetti() {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM progetto";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) 
				num = rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public boolean verifyProgettoEsistente(String nomeProgetto) {
		boolean esiste = false;
		
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetto WHERE nomeProgetto=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			rs = ps.executeQuery();
			
			if (rs.next())
				esiste=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return esiste;
	}
	
	public boolean verifyWorkingEsistente(String codiceFiscale, String nomeProgetto) {
		boolean esiste = false;
		
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM working "
		           + "INNER JOIN dipendente ON dipendente.id = working.idDipendente "
		           + "INNER JOIN persona ON persona.id=dipendente.idPersona "
		           + "INNER JOIN progetto ON progetto.id = working.idProgetto "
		           + "WHERE codiceFiscale = ? AND nomeProgetto=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, codiceFiscale);
			ps.setString(2, nomeProgetto);
			rs = ps.executeQuery();
			
			if (rs.next())
				esiste=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return esiste;
	}
}
