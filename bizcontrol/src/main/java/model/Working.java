package model;

public class Working extends Progetto{
	
	private int idWorking;
	private int idDipendente;
	private int idProgetto;
	
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
	

}
