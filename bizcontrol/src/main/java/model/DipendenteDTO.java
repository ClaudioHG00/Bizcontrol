package model;

import java.sql.Date;
import java.util.Objects;

public class DipendenteDTO {
	
    private String nome;
    private String cognome;
    private boolean sesso;
    private Date dataNascita;
    private String codiceFiscale;
    private String luogoNascita;
    private double stipendio;
    private String username;
    private String email;
    private String password;
    private int idPermesso;
    private String tipoPermesso;
    // Numero progetti assegnati (campo db inesistente)
    private int numProgetti;
    
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
	@Override
	public String toString() {
		return "DipendenteDTO [nome=" + nome + ", cognome=" + cognome + ", sesso=" + sesso + ", dataNascita="
				+ dataNascita + ", codiceFiscale=" + codiceFiscale + ", luogoNascita=" + luogoNascita + ", stipendio="
				+ stipendio + ", username=" + username + ", email=" + email + ", password=" + password + ", idPermesso="
				+ idPermesso + "]";
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public boolean isSesso() {
		return sesso;
	}
	public void setSesso(boolean sesso) {
		this.sesso = sesso;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public double getStipendio() {
		return stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
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
	public int getIdPermesso() {
		return idPermesso;
	}
	public void setIdPermesso(int idPermesso) {
		this.idPermesso = idPermesso;
	}
	public String getTipoPermesso() {
		return tipoPermesso;
	}
	public void setTipoPermesso(String tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, cognome, dataNascita, email, idPermesso, luogoNascita, nome, password, sesso,
				stipendio, tipoPermesso, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DipendenteDTO other = (DipendenteDTO) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(email, other.email)
				&& idPermesso == other.idPermesso && Objects.equals(luogoNascita, other.luogoNascita)
				&& Objects.equals(nome, other.nome) && Objects.equals(password, other.password) && sesso == other.sesso
				&& Double.doubleToLongBits(stipendio) == Double.doubleToLongBits(other.stipendio)
				&& Objects.equals(tipoPermesso, other.tipoPermesso) && Objects.equals(username, other.username);
	}
	public int getNumProgetti() {
		return numProgetti;
	}
	public void setNumProgetti(int numProgetti) {
		this.numProgetti = numProgetti;
	}
	
	
    
}
