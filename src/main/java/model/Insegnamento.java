package model;

public class Insegnamento {
	private int codice;
	private String nome;
	private String codiceProfessore;
	
	public Insegnamento() {
		
	}
	
	public Insegnamento(String nome,String codiceProfessore) {
		super();
		this.nome=nome;
		this.codiceProfessore=codiceProfessore;
		
	}
	
	public String getNomeInsegnamento() {
		return this.nome;
	}

	public String getCodiceProfessore() {
		return this.codiceProfessore;
	}
	public int getCodiceInsegnamento() {
		return this.codice;
	}
	
	public void setNomeInsegnamento(String nome) {
		this.nome=nome;
	}
	public void setCodiceProfessore(String codiceProfessore) {
		this.codiceProfessore=codiceProfessore;
	}
}
