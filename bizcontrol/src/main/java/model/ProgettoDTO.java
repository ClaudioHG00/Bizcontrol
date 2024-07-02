package model;

public class ProgettoDTO {

	private int id;
	private String nomeProgetto;
	private String descrizione;
	private String linkImg;
	private double costo;
	private int idWorking;
	private int idDipendente;
	private int idProgetto;
	// Per il conteggio di dipendenti working per progetto (campo db inesistente)
	private int numDipendenti;
	
	public int getIdDipendente() {
		return idDipendente;
	}
	public void setIdDipendente(int idDipendente) {
		this.idDipendente = idDipendente;
	}
	public int getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	public int getIdWorking() {
		return idWorking;
	}

	public void setIdWorking(int idWorking) {
		this.idWorking = idWorking;
	}
	
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getLinkImg() {
		return linkImg;
	}
	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ProgettoDTO [id=" + id + ", nomeProgetto=" + nomeProgetto + ", descrizione=" + descrizione
				+ ", linkImg=" + linkImg + ", costo=" + costo + ", idWorking=" + idWorking + ", idDipendente="
				+ idDipendente + ", idProgetto=" + idProgetto + "]";
	}
	public int getNumDipendenti() {
		return numDipendenti;
	}
	public void setNumDipendenti(int numDipendenti) {
		this.numDipendenti = numDipendenti;
	}
	
}
