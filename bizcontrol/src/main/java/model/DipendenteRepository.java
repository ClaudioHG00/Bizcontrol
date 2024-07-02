package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DipendenteRepository {

	public int insertDipendente(Dipendente d, Account a) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sqlPersona = "INSERT INTO persona(nome,cognome,sesso,dataNascita,codiceFiscale,luogoNascita)"
				+ " VALUES (?,?,?,?,?,?)";

		PreparedStatement psPersona = null;

		try {
			// È necessario specificare RETURN_GENERATED_KEYS per far utilizzo di
			// getGeneratedKeys()
			psPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);

			psPersona.setString(1, d.getNome());
			psPersona.setString(2, d.getCognome());
			psPersona.setBoolean(3, d.isSesso());
			psPersona.setDate(4, d.getDataNascita());
			psPersona.setString(5, d.getCodiceFiscale());
			psPersona.setString(6, d.getLuogoNascita());

			num = psPersona.executeUpdate();

			// Prendo la key auto-generata
			ResultSet rs = psPersona.getGeneratedKeys();

			if (rs.next()) {
				PreparedStatement psDipendente = null;
				PreparedStatement psAccount = null;
				// Avendo solo id di Persona auto-generato, prendiamo il primo e unico
				int idPersona = rs.getInt(1);

				String sqlDipendente = "INSERT INTO dipendente(stipendio, idPersona) VALUES (?, ?)";
				String sqlAccount = "INSERT INTO account(username,email,password,idPermesso,idPersona)"
						+ "VALUES(?,?,?,?,?)";
				psDipendente = conn.prepareStatement(sqlDipendente);
				psDipendente.setDouble(1, d.getStipendio());
				psDipendente.setInt(2, idPersona);
				num += psDipendente.executeUpdate();
				psDipendente.close();

				psAccount = conn.prepareStatement(sqlAccount);
				psAccount.setString(1, a.getUsername());
				psAccount.setString(2, a.getEmail());
				psAccount.setString(3, a.getPassword());
				psAccount.setInt(4, a.getIdPermesso());
				psAccount.setInt(5, idPersona);
				num += psAccount.executeUpdate();
				psAccount.close();
			}

			psPersona.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}

	public DipendenteDTO findDipendenteByCodiceFiscale(String codiceFiscale) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM dipendente " + "INNER JOIN persona ON dipendente.idPersona=persona.id "
				+ "INNER JOIN account ON account.idPersona=persona.id " + "WHERE codiceFiscale=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Faccio utilizzo del DTO per comodita
		DipendenteDTO dto = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, codiceFiscale);
			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new DipendenteDTO();
				dto.setNome(rs.getString("nome"));
				dto.setCognome(rs.getString("cognome"));
				dto.setDataNascita(rs.getDate("dataNascita"));
				dto.setLuogoNascita(rs.getString("luogoNascita"));
				dto.setCodiceFiscale(rs.getString("codiceFiscale"));
				dto.setSesso(rs.getBoolean("sesso"));
				dto.setStipendio(rs.getDouble("stipendio"));
				dto.setUsername(rs.getString("username"));
				dto.setEmail(rs.getString("email"));
				dto.setPassword(rs.getString("password"));
				dto.setIdPermesso(rs.getInt("idPermesso"));
			}
			ps.close();
			rs.close();
			return dto;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet findDipendenteByUsernameEmail(String usernameEmail) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM dipendente " + "INNER JOIN persona ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON account.idPermesso=permesso.id " + "WHERE username=? OR email=?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, usernameEmail);
			ps.setString(2, usernameEmail);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet findAllDipendenti() {
		Connection conn = Connessione.getConnection();
		// faccio una join anche di permesso per accedere a permesso.tipo
		String sql = "SELECT * FROM dipendente INNER JOIN persona ON dipendente.idPersona=persona.id "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON account.idPermesso=permesso.id";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet findDipendentiFilteredWithPaginazione(int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso " + "LIMIT ? OFFSET ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, limit);
			ps.setInt(2, offset);

			rs = ps.executeQuery();
			return rs;

		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}

	public int countAllDipendenti() {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}

	public int countDipendentiFilterByCodiceFiscale(String cf) {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE codiceFiscale LIKE ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + cf + "%"); 
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}
	
	public int countDipendentiFilterByNomeCognome(String nome, String cognome) {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE nome LIKE ? AND cognome LIKE ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%"); 
			ps.setString(2, "%" + cognome + "%"); 
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}
	
	public int countDipendentiFilterByLuogoNascita(String luogoNascita) {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE luogoNascita LIKE ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + luogoNascita + "%"); 
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}
	
	public int countDipendentiFilterByMaxStipendio(int maxStipendio) {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE stipendio <= ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, maxStipendio); 
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}
	
	public int countDipendentiFilterTipoPermesso(String tipoPermesso) {
		int numDipendenti = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) FROM persona " + "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE tipo=?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, tipoPermesso); 
			rs = ps.executeQuery();
			if (rs.next()) {
				numDipendenti = rs.getInt(1);
			}
			ps.close();
			rs.close();
			return numDipendenti;
		} catch (SQLException e) {
			e.printStackTrace();
			return numDipendenti;
		}
	}
	
	public int countDipendentiWorkingByNomeProgetto(String nomeProgetto) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT COUNT(*) "
				+ "FROM working "
				+ "INNER JOIN progetto ON progetto.id=working.idProgetto "
				+ "WHERE nomeProgetto=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			
			rs = ps.executeQuery();
			
			if (rs.next()) 
				num = rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public boolean deleteDipendenteByCodiceFiscale(String codiceFiscale) {
		int num = 0;
		// Elimino uno alla volta, usando IN, a differenza di JOIN con le SELECT
		Connection conn = Connessione.getConnection();
		String sqlAccount = "DELETE FROM account WHERE idPersona IN "
				+ "(SELECT persona.id FROM persona WHERE codiceFiscale = ?)";
		String sqlWorking = "DELETE FROM working WHERE idDipendente IN "
				+ "(SELECT dipendente.id FROM dipendente "
				+ "INNER JOIN persona ON persona.id=dipendente.idPersona "
				+ "WHERE codiceFiscale = ?)";
		String sqlDipendente = "DELETE FROM dipendente WHERE idPersona IN "
				+ "(SELECT persona.id FROM persona WHERE codiceFiscale = ?)";
		String sqlPersona = "DELETE FROM persona WHERE persona.codiceFiscale = ?";
		PreparedStatement psAccount = null;
		PreparedStatement psWorking = null;
		PreparedStatement psDipendente = null;
		PreparedStatement psPersona = null;
		

		try {
			psAccount = conn.prepareStatement(sqlAccount);
			psAccount.setString(1, codiceFiscale);
			num += psAccount.executeUpdate();
			psAccount.close();
			
			psWorking = conn.prepareStatement(sqlWorking);
			psWorking.setString(1, codiceFiscale);
			num += psWorking.executeUpdate();
			psWorking.close();

			psDipendente = conn.prepareStatement(sqlDipendente);
			psDipendente.setString(1, codiceFiscale);
			num += psDipendente.executeUpdate();
			psDipendente.close();

			psPersona = conn.prepareStatement(sqlPersona);
			psPersona.setString(1, codiceFiscale);
			num += psPersona.executeUpdate();
			psPersona.close();

			return num > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int modifyDipendenteByCodiceFiscale(DipendenteDTO dto, String codiceFiscale) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sqlPersona = "UPDATE persona SET nome=?, cognome=?, sesso=?, dataNascita=?, luogoNascita=?, codiceFiscale=? WHERE codiceFiscale=?";
		String sqlAccount = "UPDATE account SET username=?, email=?, password=?, idPermesso=? WHERE idPersona IN (SELECT id FROM persona WHERE codiceFiscale=?)";
		String sqlDipendente = "UPDATE dipendente SET stipendio=? WHERE idPersona IN (SELECT id FROM persona WHERE codiceFiscale=?)";
		PreparedStatement psPersona = null;
		PreparedStatement psAccount = null;
		PreparedStatement psDipendente = null;

		try {
			psPersona = conn.prepareStatement(sqlPersona);
			psPersona.setString(1, dto.getNome());
			psPersona.setString(2, dto.getCognome());
			psPersona.setBoolean(3, dto.isSesso());
			psPersona.setDate(4, dto.getDataNascita());
			psPersona.setString(5, dto.getLuogoNascita());
			psPersona.setString(6, dto.getCodiceFiscale());
			psPersona.setString(7, codiceFiscale);
			num += psPersona.executeUpdate();
			psPersona.close();

			psAccount = conn.prepareStatement(sqlAccount);
			psAccount.setString(1, dto.getUsername());
			psAccount.setString(2, dto.getEmail());
			psAccount.setString(3, dto.getPassword());
			psAccount.setInt(4, dto.getIdPermesso());
			psAccount.setString(5, codiceFiscale);
			num += psAccount.executeUpdate();
			psAccount.close();

			psDipendente = conn.prepareStatement(sqlDipendente);
			psDipendente.setDouble(1, dto.getStipendio());
			psDipendente.setString(2, codiceFiscale);
			num += psDipendente.executeUpdate();
			psDipendente.close();

			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;

	}
	
	// A differenza di findDipendenteByCodiceFiscale, permette di cercare a caratteri
	public ResultSet filterByCodiceFiscaleWithPaginazione(String cfFilterStringa, int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE codiceFiscale LIKE ? "
				+ "LIMIT ? OFFSET ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			// Aggiungere i caratteri jolly direttamente qui, non nel sql
			ps.setString(1, "%" + cfFilterStringa + "%"); 
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			
			rs = ps.executeQuery();
			
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet filterByNomeCognomeWithPaginazione(String nome, String cognome, int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE nome LIKE ? AND cognome LIKE ? "
				+ "LIMIT ? OFFSET ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%"); 
			ps.setString(2, "%" + cognome + "%"); 
			ps.setInt(3, limit);
			ps.setInt(4, offset);
			
			rs = ps.executeQuery();
			
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet filterByLuogoNascitaWithPaginazione(String luogoNascita, int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE luogoNascita LIKE ? "
				+ "LIMIT ? OFFSET ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + luogoNascita + "%"); 
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			
			rs = ps.executeQuery();
			
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet filterByMaxStipendioWithPaginazione(int maxStipendio, int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE stipendio <= ? "
				+ "LIMIT ? OFFSET ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, maxStipendio); 
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			
			rs = ps.executeQuery();
			
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet filterByTipoPermessoWithPaginazione(String tipoPermesso, int limit, int offset) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "INNER JOIN permesso ON permesso.id=account.idPermesso "
				+ "WHERE tipo=? "
				+ "LIMIT ? OFFSET ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, tipoPermesso); 
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			
			rs = ps.executeQuery();
			
			return rs;
		} catch(SQLException e) {
			e.printStackTrace();
			return rs;
		}
	}
	
	public String findCodiceFiscaleByUsernameEmail(String usernameEmail) {
		String codiceFiscale = null;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT codiceFiscale FROM persona "
				+ "INNER JOIN account ON persona.id=account.idPersona "
				+ "WHERE username=? OR email=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, usernameEmail);
			ps.setString(2, usernameEmail);
			
			rs = ps.executeQuery();
			if(rs.next()) 
				 codiceFiscale = rs.getString(1);
			
			ps.close();
			rs.close();
			return codiceFiscale;
		} catch(SQLException e) {
			e.printStackTrace();
			return codiceFiscale;
		}
	}

	public ArrayList<DipendenteDTO> findAllDipendentiAssociatiByNomeProgetto(String nomeProgetto) {
		ArrayList<DipendenteDTO> arraydip = new ArrayList<DipendenteDTO>();
		Connection conn = Connessione.getConnection();
		String sql = "SELECT nome,cognome,codiceFiscale FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "INNER JOIN working ON dipendente.id=working.idDipendente "
				+ "INNER JOIN progetto ON working.idProgetto=progetto.id "
				+ "WHERE nomeProgetto=?;";
				// Non vi e' bisogno del group by, in quanto ce ne puo gia essere solo uno a progetto
//				+ "GROUP BY persona.id";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				DipendenteDTO dip = new DipendenteDTO();
				dip.setNome(rs.getString("nome"));
				dip.setCognome(rs.getString("cognome"));
				dip.setCodiceFiscale(rs.getString("codiceFiscale"));
				arraydip.add(dip);
			}
			ps.close();
			rs.close();
			
			return arraydip;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return arraydip;
		}
		
	}
	
	public ArrayList<DipendenteDTO> findAllDipendentiNonAssociatiByNomeProgetto(String nomeProgetto) {
		ArrayList<DipendenteDTO> arraydip = new ArrayList<DipendenteDTO>();
		Connection conn = Connessione.getConnection();
		// BUG 1: Prende anche lo stesso lavoratore ma in altri progetti
		// FIX 1: Risolto nella gestioneprogetto.jsp
		String sql = "SELECT nome,cognome,codiceFiscale,dipendente.id "
				+ "FROM persona "
				+ "INNER JOIN dipendente ON persona.id=dipendente.idPersona "
				+ "LEFT JOIN ("
				+ "	SELECT idDipendente "
				+ "    FROM working "
				+ "    INNER JOIN progetto ON progetto.id=working.idProgetto "
				+ "    WHERE progetto.nomeProgetto!=?"
				+ ") AS tabella_progetto ON dipendente.id=tabella_progetto.idDipendente "
				+ "GROUP BY dipendente.id;"; // GROUP BY per togliere i duplicati
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nomeProgetto);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				DipendenteDTO dip = new DipendenteDTO();
				dip.setNome(rs.getString("nome"));
				dip.setCognome(rs.getString("cognome"));
				dip.setCodiceFiscale(rs.getString("codiceFiscale"));
				arraydip.add(dip);
			}
			ps.close();
			rs.close();
			
			return arraydip;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return arraydip;
		}
		
	}
	
	public boolean verifyDipendenteEsistente(String codiceFiscale) {
		boolean esiste = false;
		
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM persona WHERE codiceFiscale=?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, codiceFiscale);
			rs = ps.executeQuery();
			
			if (rs.next())
				esiste=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return esiste;
	}
	
}
