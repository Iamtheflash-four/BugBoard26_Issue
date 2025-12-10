package entity;

import java.util.ArrayList;

public class Issue 
{
	private String progetto;
	private String priorita;
	private String titolo;
	private String descrizione;
	private String tokenUtente;
	private ArrayList<String> imageNames;
	
	public Issue(String progetto, String priorita, String titolo, String descrizione, String tokenUtente) {
		this(progetto, priorita, titolo, descrizione, tokenUtente, null);
	}

	public Issue(String progetto, String priorita, String titolo, String descrizione, String tokenUtente, ArrayList<String> imageNames) {
		super();
		this.progetto = progetto;
		this.priorita = priorita;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.tokenUtente = tokenUtente;
		this.imageNames = imageNames;
	}
	
	protected Issue() {}//Per usare un dto
	
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
	public String getTokenUtente() {
		return tokenUtente;
	}

	public ArrayList<String> getImageNames() {
		return imageNames;
	}

	public void setTokenUtente(String tokenUtente) {
		this.tokenUtente = tokenUtente;
	}

	public void setImageNames(ArrayList<String> imageNames) {
		this.imageNames = imageNames;
	}
}
