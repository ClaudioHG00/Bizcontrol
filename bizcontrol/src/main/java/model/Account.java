package model;

public class Account extends Permesso{
	
	private int id;
	private String username;
	private String email;
	private String password;
	private int idPermesso;
	private int idPersona;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdPermesso() {
		return idPermesso;
	}
	public void setIdPermesso(int idPermesso) {
		this.idPermesso = idPermesso;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
