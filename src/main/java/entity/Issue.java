package entity;

import java.util.Date;

public class Issue 
{
	private long idProgetto;
	private String progetto;
	private String tipo;
	private String priorita;
	private String titolo;
	private String descrizione;
	private Date data;
	

	public Issue(long idProgetto, String progetto, String tipo, String priorita, String titolo, String descrizione, Date data) {
		super();
		this.idProgetto = idProgetto;
		this.progetto = progetto;
		this.tipo = tipo;
		this.priorita = priorita;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data = data;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}