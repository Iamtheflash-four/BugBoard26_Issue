package entity;

public class Issue 
{
	private String progetto;
	private String tipo;
	private String priorita;
	private String titolo;
	private String descrizione;
	private String tokenUtente;
	

	public Issue(String progetto, String tipo, String priorita, String titolo, String descrizione, String tokenUtente) {
		super();
		this.progetto = progetto;
		this.tipo = tipo;
		this.priorita = priorita;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.tokenUtente = tokenUtente;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Issue() {}//Per usare un dto

	public String getProgetto() {
		return progetto;
	}

	public String getPriorita() {
		return priorita;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getTokenUtente() {
		return tokenUtente;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setTokenUtente(String tokenUtente) {
		this.tokenUtente = tokenUtente;
	}
}